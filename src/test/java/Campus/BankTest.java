package Campus;

import Campus.Model.BankClass;
import Campus.Model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BankTest {


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

    String bankID;

    String BankName;

    String interegationCode;

    String iban;

    @Test
    public void createBank() {
        BankName = getRandomName();
        interegationCode = getRandomCode();
        iban = getRandomCode();

        BankClass b=new BankClass();
        b.setName(BankName);
        b.setIban("TR4405"+iban);
        b.setIntegrationCode(interegationCode);

        bankID=
        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(b)


                .when()
                .post("school-service/api/bank-accounts")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")


        ;


    }
    @Test(dependsOnMethods ="createBank")
    public void EditBank() {
        BankName = getRandomName();
        interegationCode = getRandomCode();
        iban = getRandomCode();

        BankClass b=new BankClass();
        b.setName(BankName);
        b.setIban("TR455"+iban);
        b.setIntegrationCode(interegationCode);
        b.setId(bankID);


        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(b)


                .when()
                .put("school-service/api/bank-accounts")
                .then()
                .log().body()
                .statusCode(200)



        ;


    }    @Test(dependsOnMethods ="EditBank")
    public void deleteBank() {



        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("bankID",bankID)


                .when()
                .delete("school-service/api/bank-accounts/{bankID}")
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
