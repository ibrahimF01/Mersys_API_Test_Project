package Campus.Model;

import Campus.SubjCategoriesTest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SubjectCategoriesClass {
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
                        //.log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()


        ;

    }


    String sjCateID;
    String sjCateName;
    String sjCateCode;
    @Test
    public void createSjCate(){
        sjCateName=getRandomName();
        sjCateCode=getRandomCode();

        SubjCategoriesTest sjCate=new SubjCategoriesTest();

        sjCate.setName(sjCateName); //generateCountryName
        sjCate.setCode(sjCateCode);// generateCountryCode


        sjCateID=
        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(sjCate)


                .when()
                .post("school-service/api/subject-categories")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")


        ;


    }
    @Test (dependsOnMethods = "createSjCate")
    public void updateSjCate(){
        sjCateName=getRandomName();
        sjCateCode=getRandomCode();

        SubjCategoriesTest sjCate=new SubjCategoriesTest();
        sjCate.setId(sjCateID);

        sjCate.setName(sjCateName); //generateCountryName
        sjCate.setCode(sjCateCode);// generateCountryCode


        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(sjCate)


                .when()
                .put("school-service/api/subject-categories")
                .then()
                .log().body()
                .statusCode(200)


        ;


    }
    @Test (dependsOnMethods = "updateSjCate")
    public void deleteSjCate(){

        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("sjCateID",sjCateID)


                .when()
                .delete("school-service/api/subject-categories/{sjCateID}")
                .then()
                .log().body()
                .statusCode(200)


        ;


    }
    public  String getRandomName(){
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
    public  String getRandomCode(){
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }

}