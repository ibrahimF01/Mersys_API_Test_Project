package Campus;

import Campus.Model.HumanAttetClass;
import Campus.Model.HumanPosClass;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HumanAttetTest {
    Cookies cookies;
    @BeforeClass
    public void loginCampus(){
        baseURI="https://demo.mersys.io/";

        Map<String,String> credential =new HashMap<>();
        credential.put("username","richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies=
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


    String humanAttetId;
    String humanAttetName;

    @Test
    public void createHumanAttet(){
        humanAttetName=getRandomName();


        HumanAttetClass humattet=new HumanAttetClass();
        humattet.setName(humanAttetName);


        humanAttetId=
                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(humattet)


                        .when()
                        .post("school-service/api/attestation")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")


        ;


    }
    @Test (dependsOnMethods = "createHumanAttet")
    public void updateHumanAttet(){
        humanAttetName=getRandomName();


        HumanAttetClass humattet=new HumanAttetClass();
        humattet.setId(humanAttetId);
        humattet.setName(humanAttetName);




                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(humattet)


                        .when()
                        .put("school-service/api/attestation")
                        .then()
                        .log().body()
                        .statusCode(200)



        ;


    }
    @Test (dependsOnMethods = "updateHumanAttet")
    public void deleteHumanAttet(){


        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("humanAttetId",humanAttetId)



                .when()
                .delete("school-service/api/attestation/{humanAttetId}")
                .then()
                .log().body()
                .statusCode(204)



        ;


    }
    public  String getRandomName(){
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

}
