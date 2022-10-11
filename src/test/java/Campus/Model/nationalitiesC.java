package Campus.Model;

public class nationalitiesC {

    private String id = null;
    private String name;
    private String[] nationalityName = {};

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

    public String[] getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String[] nationalityName) {
        this.nationalityName = nationalityName;
    }
}
