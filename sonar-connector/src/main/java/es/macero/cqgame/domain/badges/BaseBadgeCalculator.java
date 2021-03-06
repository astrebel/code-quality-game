package es.macero.cqgame.domain.badges;

import java.util.List;

import es.macero.cqgame.resultbeans.Issue;
import es.macero.cqgame.util.Utils;

public abstract class BaseBadgeCalculator implements BadgeCalculator {
	protected abstract SonarBadge badgeFromIssue(List<Issue> issues, Issue currentIssue);
	
	public SonarBadge badgeFromIssueList(List<Issue> issues) {
		SonarBadge result = null;
		for(Issue issue : issues) {
			result = badgeFromIssue(issues, issue);

			if (result != null) {
				return result;
			}
		}
		
		return null;
	}

	protected boolean isVisible(Issue issue) {
		return Utils.withinThisWeek(issue.getCloseDate());
	}
}
