package Campus;

import Campus.Model.Country;
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

public class HumanPositonsTest {
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


    String humanId;
    String humanName;
    String humanShortName;
    @Test
    public void createHumanPositions(){
        humanName=getRandomName();
        humanShortName=getRandomShortName();

        HumanPosClass humpos=new HumanPosClass();
        humpos.setName(humanName);
        humpos.setShortName(humanShortName);

        humanId=
        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(humpos)


                .when()
                .post("school-service/api/employee-position")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")


        ;


    }
    @Test(dependsOnMethods = "createHumanPositions")
    public void updateHumanPositions(){
        humanName=getRandomName();
        humanShortName=getRandomShortName();

        HumanPosClass humpos=new HumanPosClass();
        humpos.setId(humanId);
        humpos.setName(humanName);
        humpos.setShortName(humanShortName);


                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(humpos)


                        .when()
                        .put("school-service/api/employee-position")
                        .then()
                        .log().body()
                        .statusCode(200)



        ;


    }
    @Test(dependsOnMethods = "updateHumanPositions")
    public void deleteHumanPositions(){

        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("humanId",humanId)



                .when()
                .delete("school-service/api/employee-position/{humanId}")
                .then()
                .log().body()
                .statusCode(204)



        ;


    }
    public  String getRandomName(){
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
    public  String getRandomShortName(){
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }
}
