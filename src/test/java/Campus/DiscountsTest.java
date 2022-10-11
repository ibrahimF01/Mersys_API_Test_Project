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

public class DiscountsTest {
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

    String discountID;
    String discription;
    String code;
    String priorty;


    @Test
    public void createDiscount()
    {
        discription=getRandomDiscription();
        code=getRandomCode();
        priorty=getRandomPriorty();

        discountClass discount=new discountClass();
        discount.setDescription(discription);
        discount.setCode(code);
        discount.setPriority(priorty);


        discountID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(discount)

                        .when()
                        .post("school-service/api/discounts")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;

    }
    @Test(dependsOnMethods ="createDiscount")
    public void editDiscount()
    {
        discription=getRandomDiscription();
        code=getRandomCode();
        priorty=getRandomPriorty();

        discountClass discount=new discountClass();
        discount.setId(discountID);
        discount.setDescription(discription);
        discount.setCode(code);
        discount.setPriority(priorty);



                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(discount)

                        .when()
                        .put("school-service/api/discounts")

                        .then()
                        .log().body()
                        .statusCode(200)

        ;

    }
    @Test(dependsOnMethods ="editDiscount")
    public void deleteDiscount()
    {


                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .pathParam("discountID",discountID)

                        .when()
                        .delete("school-service/api/discounts/{discountID}")

                        .then()
                        .log().body()
                        .statusCode(200)

        ;

    }


    public String getRandomDiscription() {return RandomStringUtils.randomAlphabetic(8).toLowerCase();}
    public String getRandomCode() {return RandomStringUtils.randomNumeric(4).toLowerCase();}
    public String getRandomPriorty() {return RandomStringUtils.randomNumeric(3).toLowerCase();}




}
