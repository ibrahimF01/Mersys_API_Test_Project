package Campus;

import Campus.Model.DepartmentClass;
import Campus.Model.LocationClass;
import Campus.Model.School;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class School_Department {

    Cookies cookies;

    @BeforeClass
    public void loginCampus() {
        baseURI = "https://demo.mersys.io/";

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }

    String departmentID;
    String departmentName;
    String departmentCode;

    @Test
    public void createDepartment()
    {

        departmentName=getRandomName();
        departmentCode=getRandomCode();

        DepartmentClass department=new DepartmentClass();
        department.setName(departmentName);
        department.setCode(departmentCode);
        department.setSchool(new School("5fe07e4fb064ca29931236a5"));

        departmentID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(department)

                        .when()
                        .post("school-service/api/department")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;

    }

    @Test(dependsOnMethods ="createDepartment")
    public void editDepartment()
    {

        departmentName=getRandomName();
        departmentCode=getRandomCode();

        DepartmentClass department=new DepartmentClass();
        department.setName(departmentName);
        department.setCode(departmentCode);
        department.setId(departmentID);
        department.setSchool(new School("5fe07e4fb064ca29931236a5"));


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(department)

                .when()
                .put("school-service/api/department")

                .then()
                .log().body()
                .statusCode(200)

        ;

    }

    @Test(dependsOnMethods ="editDepartment")
    public void deleteDepartment()
    {

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("departmentID",departmentID)

                .when()
                .delete("school-service/api/department/{departmentID}")

                .then()
                .log().body()
                .statusCode(204)
        ;

    }

    @Test(dependsOnMethods ="deleteDepartment")
    public void deleteDepartmentNegative()
    {

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("departmentID",departmentID)

                .when()
                .delete("school-service/api/department/{departmentID}")

                .then()
                .log().body()
                .statusCode(204)
        ;

    }

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(4).toLowerCase();
    }
    public String getRandomCode() {
        return RandomStringUtils.randomNumeric(3);
    }

}
