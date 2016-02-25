package es.macero.cqgame.domain.badges;

import es.macero.cqgame.domain.util.IssueDateFormatter;
import es.macero.cqgame.resultbeans.Issue;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BadgeEarlyBird implements BadgeCalculator {
    private static final int EXTRA_POINTS = 100;

    @Value("${earlyBirdDate}")
    private String earlyBirdDate;

    @Override
    public SonarBadge badgeFromIssueList(List<Issue> issues) {
    	for(Issue issue : issues) {
    		if(issue.getCloseDate() != null && IssueDateFormatter.format(issue.getCloseDate()).isBefore(LocalDate.parse(earlyBirdDate))) {
    			return new SonarBadge("Early Bird", "Resolve any issue before " + earlyBirdDate, EXTRA_POINTS);
    		}
    	}
    	
    	return null;
    }
}
