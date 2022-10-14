package Campus;

import Campus.Model.PositionClass;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PositionTest {

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


    String positionsID;
    String positionsName;
    String positionsShortName;
    @Test
    public void createPositions(){
        positionsName=getRandomName();
        positionsShortName=getRandomShortName();

        PositionClass position=new PositionClass();
        position.setName(positionsName);
        position.setShortName(positionsShortName);

        positionsID =
        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(position)


                .when()
                .post("school-service/api/employee-position")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")


        ;


    }
    @Test (dependsOnMethods = "createPositions")
    public void editPositions(){
        positionsName=getRandomName();
        positionsShortName=getRandomShortName();

        PositionClass position=new PositionClass();
        position.setName(positionsName);
        position.setShortName(positionsShortName);
        position.setId(positionsID);

                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(position)


                        .when()
                        .put("school-service/api/employee-position")
                        .then()
                        .log().body()
                        .statusCode(200)



        ;


    }
    @Test (dependsOnMethods = "editPositions")
    public void deletePositions(){

        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("positionsID",positionsID)


                .when()
                .delete("school-service/api/employee-position/{positionsID}")
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
