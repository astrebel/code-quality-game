package es.macero.cqgame.domain.badges;

import java.util.List;

import org.springframework.stereotype.Component;

import es.macero.cqgame.resultbeans.Issue;

@Component
public class BadgeUnitTester implements BadgeCalculator {
    private static final int EXTRA_POINTS_PAPER = 100;
    private static final int EXTRA_POINTS_BRONZE = 250;
    private static final int EXTRA_POINTS_SILVER = 650;
    private static final int EXTRA_POINTS_GOLDEN = 1000;

    private static final String RULE_ID_LINE = "common-java:InsufficientLineCoverage";
    private static final String RULE_ID_BRANCH = "common-java:InsufficientBranchCoverage";

    @Override
    public SonarBadge badgeFromIssueList(List<Issue> issues) {
    	int count = 0;
    	for(Issue issue : issues) {
    		if(issue.getRule().equalsIgnoreCase(RULE_ID_LINE) || issue.getRule().equalsIgnoreCase(RULE_ID_BRANCH)) {
    			count++;
    		}
    	}
    	
//        long count = issues.stream().filter(i -> i.getRule().equalsIgnoreCase(RULE_ID_LINE) || i.getRule().equalsIgnoreCase(RULE_ID_BRANCH)).count();
        if (count >= 50) {
            return new SonarBadge("Golden Cover", "Fixing UT coverage for 50 legacy classes.", EXTRA_POINTS_GOLDEN);
        } else if (count >= 25) {
            return new SonarBadge("Silver Cover", "Fixing UT coverage for 25 legacy classes.", EXTRA_POINTS_SILVER);
        } else if (count >= 10) {
            return new SonarBadge("Bronze Cover", "Fixing UT coverage for 10 legacy classes.", EXTRA_POINTS_BRONZE);
        } else if (count >= 1) {
            return new SonarBadge("Paper Cover", "Fixing UT coverage for 1 legacy class.", EXTRA_POINTS_PAPER);
        } else {
            return null;
        }
    }

}
