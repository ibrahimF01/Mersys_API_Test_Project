package Campus;


import Campus.Model.DocumentTypeIn;
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

public class DocumentTypes {

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

    String documentID;
    String docName;
    String docDescription;

    @Test
    public void createDocumentType() {
        docName = getRandomName();
        docDescription = getRandomDescription() + " " + getRandomDescription();

        DocumentTypeIn documentTypeIn = new DocumentTypeIn();
        documentTypeIn.setAttachmentStages(new String[]{"STUDENT_REGISTRATION"});
        documentTypeIn.setSchoolId("5fe07e4fb064ca29931236a5");
        documentTypeIn.setName(docName);
        documentTypeIn.setDescription(docDescription);
        documentID=
        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(documentTypeIn)


                .when()
                .post("school-service/api/attachments")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")
        ;


    }


    @Test(dependsOnMethods = "createDocumentType")
    public void updateDocumentType() {
        docName = getRandomName();
        docDescription = getRandomDescription() + " " + getRandomDescription();

        DocumentTypeIn documentTypeIn = new DocumentTypeIn();
        documentTypeIn.setAttachmentStages(new String[]{"STUDENT_REGISTRATION"});
        documentTypeIn.setSchoolId("5fe07e4fb064ca29931236a5");
        documentTypeIn.setId(documentID);
        documentTypeIn.setName(docName);
        documentTypeIn.setDescription(docDescription);

                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(documentTypeIn)


                        .when()
                        .put("school-service/api/attachments")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("name",equalTo(docName))
        ;


    }

    @Test(dependsOnMethods = "updateDocumentType")
    public void deleteDocumentType() {

        given()

                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("documentID",documentID)
                .log().uri()
                .when()
                .delete("school-service/api/attachments/{documentID}")
                .then()
                .log().body()
                .statusCode(200)

        ;


    }


    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomDescription() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
}
