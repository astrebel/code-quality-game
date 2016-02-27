package es.macero.cqgame.domain.badges;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.macero.cqgame.resultbeans.Issue;

@Component
public class BadgeEarlyBird extends BaseBadgeCalculator {
    private static final int EXTRA_POINTS = 100;

    @Value("${earlyBirdDate}")
    private String earlyBirdDate;

	@Override
	protected SonarBadge badgeFromIssue(List<Issue> issues, Issue currentIssue) {
		return new SonarBadge("Early Bird", "Resolve any issue before " + earlyBirdDate, EXTRA_POINTS);
	}
}
