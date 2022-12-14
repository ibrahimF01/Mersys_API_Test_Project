package Campus;

import Campus.Model.discountClass;
import Campus.Model.nationalitiesC;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class nationalitiesTest {
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
                        //.log().cookies()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }

    String nationalityID;
    String nationalityName;


    @Test
    public void createNationality()
    {
        nationalityName=getRandomName();

        nationalitiesC nationality=new nationalitiesC();
        nationality.setName(nationalityName); // generateCountrName


        nationalityID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(nationality)

                        .when()
                        .post("school-service/api/nationality")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;

    }
    @Test(dependsOnMethods ="createNationality")
    public void editNationality()
    {

        nationalityName=getRandomName();

        nationalitiesC nationality=new nationalitiesC();
        nationality.setName(nationalityName);
        nationality.setId(nationalityID);


                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(nationality)

                        .when()
                        .put("school-service/api/nationality")

                        .then()
                        .log().body()
                        .statusCode(200)

        ;

    }
    @Test(dependsOnMethods ="editNationality")
    public void deleteNationality()
    {




                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .pathParam("nationalityID",nationalityID)

                        .when()
                        .delete("school-service/api/nationality/{nationalityID}")

                        .then()
                        .log().body()
                        .statusCode(200)

        ;

    }


    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }



}
