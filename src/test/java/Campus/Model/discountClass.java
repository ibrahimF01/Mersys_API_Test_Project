package Campus.Model;

public class discountClass {


    private String id = null;
    private String description;
    private String code;
    private boolean active = true;
    private String[] translateDescription = {};
    private String priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String[] getTranslateDescription() {
        return translateDescription;
    }

    public void setTranslateDescription(String[] translateDescription) {
        this.translateDescription = translateDescription;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
