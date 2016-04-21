package es.macero.cqgame.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.macero.cqgame.dao.SonarUserRepository;
import es.macero.cqgame.domain.badges.BadgeCalculator;
import es.macero.cqgame.domain.badges.SonarBadge;
import es.macero.cqgame.domain.stats.SonarStats;
import es.macero.cqgame.domain.stats.SonarStatsRow;
import es.macero.cqgame.domain.users.SonarUser;
import es.macero.cqgame.domain.util.IssueDateParser;
import es.macero.cqgame.resultbeans.Issue;
import es.macero.cqgame.util.Utils;

@Service
public class SonarStatsService {

    private static final Log log = LogFactory.getLog(SonarStatsService.class);

    @Value("${legacyDate}")
    private String legacyDateString;

    @Value("${coverageDate}")
    private String coverageDateString;

    private Map<String, SonarUser> idsAndUsers;
    private Map<String, SonarStats> statsPerId;
    private SonarUserRepository sonarDao;
    private List<BadgeCalculator> badgeCalculators = new ArrayList<>();

    private LocalDate legacyDate;
    private LocalDate coverageDate;

    @Autowired
    public void setSonarDao(SonarUserRepository sonarDao) {
        this.sonarDao = sonarDao;
    }

    @Autowired(required = false)
    public void setBadgeCalculators(List<BadgeCalculator> badgeCalculators) {
        this.badgeCalculators = badgeCalculators;
    }

    @PostConstruct
    public void init() {
    	idsAndUsers = new HashMap<String, SonarUser>();
    	for(SonarUser user : sonarDao.findAll()) {
    		idsAndUsers.put(user.getId(), user);
    	}
    	
        statsPerId = new ConcurrentHashMap<>();
        legacyDate = LocalDate.parse(legacyDateString);
        coverageDate = LocalDate.parse(coverageDateString);
        log.info("Legacy date is " + legacyDate);
        log.info("Coverage date is " + coverageDate);
    }

    public Set<String> getIds() {
        return idsAndUsers.keySet();
    }

    public Collection<SonarUser> getUsers() {
        return idsAndUsers.values();
    }

    public void updateStats(String id, List<Issue> issues) {
        SonarStats stats = fromIssueList(issues);
        statsPerId.put(id, stats);
        log.info("Processing " + id + "; Stats: " + stats);
    }

	public Collection<SonarStatsRow> getSortedStatsPerUser() {
        List<SonarStatsRow> rows = new ArrayList<>();
        for (Entry<String, SonarStats> entry : statsPerId.entrySet()) {
            SonarUser user = idsAndUsers.get(entry.getKey());
            SonarStats stats = entry.getValue();
            rows.add(new SonarStatsRow(user.getAlias(), user.getTeam(), stats.getTotalPoints(),
                    stats.getTotalPaidDebt(), stats.getBlocker(),
                    stats.getCritical(), stats.getMajor(), stats.getMinor(),
                    stats.getInfo(), stats.getBadges()));
        }
        
        Collections.sort(rows, new Comparator<SonarStatsRow>() {
            @Override
            public int compare(SonarStatsRow lhs, SonarStatsRow rhs) {
                return lhs.getTotalPoints() > rhs.getTotalPoints() ? -1 : (lhs.getTotalPoints() > rhs.getTotalPoints() ) ? 1 : 0;
            }
        });
        
        return rows;
    }

    public Collection<SonarStatsRow> getSortedStatsPerTeam() {
    	Map<String, SonarStatsRow> rowsTeamMap = new HashMap<>();
    	for(SonarStatsRow row : getSortedStatsPerUser()) {
    		SonarStatsRow teamRow = rowsTeamMap.get(row.getUserTeam());
    		if(teamRow == null) {
    			rowsTeamMap.put(row.getUserTeam(), row);
    		} else {
    			rowsTeamMap.put(row.getUserTeam(), combine(row, teamRow));
    		}
    	}
    	
    	List<SonarStatsRow> rows = new ArrayList<>(rowsTeamMap.values());
    	Collections.sort(rows, new Comparator<SonarStatsRow>() {
            @Override
            public int compare(SonarStatsRow lhs, SonarStatsRow rhs) {
                return lhs.getTotalPoints() > rhs.getTotalPoints() ? -1 : (lhs.getTotalPoints() > rhs.getTotalPoints() ) ? 1 : 0;
            }
        });
    	
    	return rows;
    }

    private static SonarStatsRow combine(SonarStatsRow r1, SonarStatsRow r2) {
        Set<SonarBadge> allBadges = new HashSet<SonarBadge>();
        allBadges.addAll(r1.getBadges());
        allBadges.addAll(r2.getBadges());
        return new SonarStatsRow(r1.getUserAlias(), r1.getUserTeam(), r1.getTotalPoints() + r2.getTotalPoints(),
                r1.getTotalPaidDebt() + r2.getTotalPaidDebt(), r1.getBlocker() + r2.getBlocker(),
                r1.getCritical() + r2.getCritical(), r1.getMajor() + r2.getMajor(),
                r1.getMinor() + r2.getMinor(), r1.getInfo() + r2.getInfo(), allBadges);
    }

    private SonarStats fromIssueList(List<Issue> issues) {    	
    	List<Issue> issuesFilteredByClosedDate = new ArrayList<>();
    	Map<String, Long> typeCount = new HashMap<>();
    	int debtSum = 0;
    	for(Issue issue : issues) {
    		if(IssueDateParser.parse(issue.getCloseDate()).isAfter(new LocalDate().minusDays(30))) {
    			
    			if(issue.getDebt() != null) {
    				debtSum += (int) Utils.durationTranslator(issue.getDebt()).toStandardMinutes().getMinutes();
    			}
    			
    			Long count = typeCount.get(issue.getSeverity());
    			if(count == null) {
    				typeCount.put(issue.getSeverity(), new Long(1));
    			} else {
    				typeCount.put(issue.getSeverity(), ++count);
    			}
    			
    			issuesFilteredByClosedDate.add(issue);
    		}
    	}
        
        int blocker = getTotalIssuesForType(SonarStats.SeverityType.BLOCKER, typeCount);
        int critical = getTotalIssuesForType(SonarStats.SeverityType.CRITICAL, typeCount);
        int major = getTotalIssuesForType(SonarStats.SeverityType.MAJOR, typeCount);
        int minor = getTotalIssuesForType(SonarStats.SeverityType.MINOR, typeCount);
        int info = getTotalIssuesForType(SonarStats.SeverityType.INFO, typeCount);
        
        List<SonarBadge> badges = new ArrayList<>();
        for(BadgeCalculator calc : badgeCalculators) {
        	SonarBadge badge = calc.badgeFromIssueList(issuesFilteredByClosedDate);
        	if(badge != null) {
        		badges.add(badge);
        	}
        }

        return new SonarStats(debtSum, blocker, critical, major, minor, info, badges);
    }

    private static int getTotalIssuesForType(SonarStats.SeverityType type, Map<String, Long> typeCount) {
    	if(typeCount.containsKey(type.toString())) {
    		return typeCount.get(type.toString()).intValue();
    	}
    	
    	return new Long(0L).intValue();
    }
}
