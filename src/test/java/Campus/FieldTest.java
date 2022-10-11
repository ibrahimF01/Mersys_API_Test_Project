package Campus;

import Campus.Model.FieldIn;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FieldTest {
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
                        //.log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()


        ;

    }

    String fieldID;
    String fieldName;
    String fieldCode;

    @Test
    public void createField() {
        fieldName = getRandomName();
        fieldCode = getRandomCode();

        FieldIn fieldIn = new FieldIn();
        fieldIn.setType("STRING");
        fieldIn.setSchoolId("5fe07e4fb064ca29931236a5");
        fieldIn.setName(fieldName);
        fieldIn.setCode(fieldCode);
        fieldID=
                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(fieldIn)


                        .when()
                        .post("school-service/api/entity-field")
                        .then()
                        //.log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;


    }


    @Test(dependsOnMethods = "createField")
    public void updateField() {
        fieldName = getRandomName();
        fieldCode = getRandomCode();

        FieldIn fieldIn = new FieldIn();
        fieldIn.setType("STRING");
        fieldIn.setSchoolId("5fe07e4fb064ca29931236a5");
        fieldIn.setId(fieldID);
        fieldIn.setName(fieldName);
        fieldIn.setCode(fieldCode);

        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(fieldIn)


                .when()
                .put("school-service/api/entity-field")
                .then()
               // .log().body()
                .statusCode(200)
                .body("name",equalTo(fieldName))
        ;


    }

    @Test(dependsOnMethods = "updateField")
    public void deleteField() {

        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("fieldID",fieldID)
              //  .log().uri()
                .when()
                .delete("school-service/api/entity-field/{fieldID}")
                .then()
               // .log().body()
                .statusCode(204)

        ;


    }


    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }
}



