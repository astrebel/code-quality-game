package es.macero.cqgame.domain.badges;

import java.util.List;

import es.macero.cqgame.resultbeans.Issue;
import es.macero.cqgame.util.Utils;

public abstract class BaseBadgeCalculator implements BadgeCalculator {
	protected abstract SonarBadge badgeFromIssue(List<Issue> issues, Issue currentIssue);
	
	public SonarBadge badgeFromIssueList(List<Issue> issues) {
		for(Issue issue : issues) {
    		if(Utils.withinThisWeek(issue.getCloseDate())) {
    			return badgeFromIssue(issues, issue);
    		}
    	}
		
		return null;
	}

	
}
