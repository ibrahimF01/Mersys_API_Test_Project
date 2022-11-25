package Campus.Model;

import java.util.Arrays;

public class HumanPosClass {
    private String id;
    private String name;
    private String shortName;
    private String [] translateName = {};
    private String tenantId="5fe0786230cc4d59295712cf";
    private boolean active= true;


    public HumanPosClass() {
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

    public String[] getTranslateName() {
        return translateName;
    }

    public void setTranslateName(String[] translateName) {
        this.translateName = translateName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "HumanPosClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", translateName=" + Arrays.toString(translateName) +
                ", tenantId='" + tenantId + '\'' +
                ", active=" + active +
                '}';
    }
}
