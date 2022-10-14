package Campus;

import Campus.Model.BankClass;
import Campus.Model.GradeClass;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class GradeLvlTest {

    Cookies cookies;

    @BeforeClass
    public void loginCampus() {
        baseURI = "https://demo.mersys.io/";

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)
                        .when()
                        .post("auth/login")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()


        ;

    }

    String gradeID;

    String Gradename;

    String shortName;


    @Test
    public void createGrade() {
        Gradename = getRandomName();
        shortName = getRandomShort();


        GradeClass g = new GradeClass();
        g.setName(Gradename);
        g.setShortName(shortName);


        gradeID =
                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(g)


                        .when()
                        .post("school-service/api/grade-levels")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")


        ;


    }

    @Test(dependsOnMethods = "createGrade")
    public void editGrade() {
        Gradename = getRandomName();
        shortName = getRandomShort();


        GradeClass g = new GradeClass();
        g.setName(Gradename);
        g.setShortName(shortName);
        g.setId(gradeID);



                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(g)


                        .when()
                        .put("school-service/api/grade-levels")
                        .then()
                        .log().body()
                        .statusCode(200)



        ;


    }


     @Test(dependsOnMethods = "editGrade")
    public void deleteGrade() {




                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .pathParam("gradeID",gradeID)



                        .when()
                        .delete("school-service/api/grade-levels/{gradeID}")
                        .then()
                        .log().body()
                        .statusCode(200)



        ;

    }










    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomShort() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }

}
