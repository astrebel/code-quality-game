package es.macero.cqgame.domain.badges;

import java.util.List;

import org.springframework.stereotype.Component;

import es.macero.cqgame.domain.stats.SonarStats;
import es.macero.cqgame.resultbeans.Issue;

@Component
public class BadgeBlockerKiller extends BaseBadgeCalculator {
    private static final int EXTRA_POINTS = 100;

	@Override
	protected SonarBadge badgeFromIssue(List<Issue> issues, Issue currentIssue) {
		if(SonarStats.SeverityType.BLOCKER.toString().equals(currentIssue.getSeverity())) {
			return new SonarBadge("Blocker Killer", "Resolved at least one blocker issue", "ribbon41.png", EXTRA_POINTS);
		}
		
		return null;
	}
}
