package es.macero.cqgame.domain.stats;

import es.macero.cqgame.domain.badges.SonarBadge;

import java.util.List;

public class SonarStats {
    public enum SeverityType {
        BLOCKER, CRITICAL, MAJOR, MINOR, INFO
    }

    private int totalPaidDebt;
    private int blocker;
    private int critical;
    private int major;
    private int minor;
    private int info;
    private int totalPoints;

    private List<SonarBadge> badges;

    public SonarStats(int totalPaidDebt, int blocker, int critical, int major, int minor, int info, List<SonarBadge> badges) {
        super();
        this.totalPaidDebt = totalPaidDebt;
        this.blocker = blocker;
        this.critical = critical;
        this.major = major;
        this.minor = minor;
        this.info = info;
        this.badges = badges;
        calculatePoints();
    }

    private void calculatePoints() {
    	int badgesExtraPoints = 0;
    	for(SonarBadge badge : badges) {
    		badgesExtraPoints += badge.getExtraPoints();
    	}
    	
    	totalPoints = totalPaidDebt + (blocker * 10) + (critical * 5) + (major * 3)  + minor + badgesExtraPoints;
    }

    public int getTotalPaidDebt() {
        return totalPaidDebt;
    }

    public int getBlocker() {
        return blocker;
    }

    public int getCritical() {
        return critical;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getInfo() {
        return info;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public List<SonarBadge> getBadges() {
        return badges;
    }

    @Override
    public String toString() {
        return "SonarStats [totalPaidDebt=" + totalPaidDebt + ", blocker=" + blocker + ", critical=" + critical + ", major=" + major + ", minor=" + minor + ", info=" + info + ", totalPoints="
                + totalPoints + ", badges=" + badges + "]";
    }

}
