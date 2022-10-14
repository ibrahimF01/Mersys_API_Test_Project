package Campus.Model;

public class GradeClass {
    private String id = null;
    private String name;
    private String shortName;
    private String nextGradeLevel = null;
    private String order = "45";
    private String[] translateName = {};
    private String[] translateShortName = {};
    private boolean active = true;

    public GradeClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNextGradeLevel() {
        return nextGradeLevel;
    }

    public void setNextGradeLevel(String nextGradeLevel) {
        this.nextGradeLevel = nextGradeLevel;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String[] getTranslateName() {
        return translateName;
    }

    public void setTranslateName(String[] translateName) {
        this.translateName = translateName;
    }

    public String[] getTranslateShortName() {
        return translateShortName;
    }

    public void setTranslateShortName(String[] translateShortName) {
        this.translateShortName = translateShortName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
