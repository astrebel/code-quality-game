package es.macero.cqgame.domain.badges;

public class SonarBadge {
    private String name;
    private String description;
    private String image;
    private int extraPoints;
    private boolean visible;
    
    public SonarBadge(String name, String description, int extraPoints, boolean visible) {
    	this(name, description, "star2.png", extraPoints, visible);
    }

    public SonarBadge(String name, String description, String image, int extraPoints, boolean visible) {
        super();
        this.name = name;
        this.description = description;
        this.extraPoints = extraPoints;
        this.image = image;
        this.visible = visible;
    }
    
    public String getImage() {
		return image;
	}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getExtraPoints() {
        return extraPoints;
    }
    
    public boolean isVisible() {
    	return visible;
    }

    @Override
    public String toString() {
        return "SonarBadge [name=" + name + ", description=" + description + "]" + ", image=" + image;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SonarBadge other = (SonarBadge) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


}
