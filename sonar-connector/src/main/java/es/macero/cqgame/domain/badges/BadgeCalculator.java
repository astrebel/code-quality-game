package es.macero.cqgame.domain.badges;

import java.util.List;

import es.macero.cqgame.resultbeans.Issue;

public interface BadgeCalculator {

//    Optional<SonarBadge> badgeFromIssueList(List<Issue> issues);
	SonarBadge badgeFromIssueList(List<Issue> issues);
}
