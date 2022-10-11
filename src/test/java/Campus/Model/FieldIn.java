package Campus.Model;

public class FieldIn {

    private String id = null;
    private String name;
    private String[] translateName = {};
    private String code;
    private String type;
    private String[] children = {};
    private boolean systemField = false;
    private String systemFieldName= null;
    private String schoolId;

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

    public String[] getTranslateName() {
        return translateName;
    }

    public void setTranslateName(String[] translateName) {
        this.translateName = translateName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }

    public boolean isSystemField() {
        return systemField;
    }

    public void setSystemField(boolean systemField) {
        this.systemField = systemField;
    }

    public String getSystemFieldName() {
        return systemFieldName;
    }

    public void setSystemFieldName(String systemFieldName) {
        this.systemFieldName = systemFieldName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
