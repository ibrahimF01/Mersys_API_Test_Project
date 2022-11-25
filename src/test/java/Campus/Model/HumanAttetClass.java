package Campus.Model;

import java.util.Arrays;

public class HumanAttetClass {
    private String id;
    private String name;
    private String [] translateName = {};

    public HumanAttetClass() {
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

    public String[] getTranslateName() {
        return translateName;
    }

    public void setTranslateName(String[] translateName) {
        this.translateName = translateName;
    }

    @Override
    public String toString() {
        return "HumanAttetClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", translateName=" + Arrays.toString(translateName) +
                '}';
    }
}
