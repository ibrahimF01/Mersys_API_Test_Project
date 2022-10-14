package Campus.Model;

public class DepartmentClass {


    private String id=null;
    private String name;
    private String code;
    private School school;
    private String[] sections={};
    private String[] constants={};


    public String[] getSections() {
        return sections;
    }

    public void setSections(String[] sections) {
        this.sections = sections;
    }

    public String[] getConstants() {
        return constants;
    }

    public void setConstants(String[] constants) {
        this.constants = constants;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

}
