package Campus;

import Campus.Model.LocationClass;
import Campus.Model.School;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class School_Location {

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
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }

    String locationID;
    String locationName;
    String locationShortName;
    String locationCapacity;

    @Test
    public void createLocation()
    {

        locationName=getRandomName();
        locationShortName=getRandomShortName();
        locationCapacity=getRandomCapacity();

        LocationClass location=new LocationClass();
        location.setName(locationName);
        location.setShortName(locationShortName);
        location.setCapacity(locationCapacity);
        location.setType("CLASS");
        location.setSchool(new School("5fe07e4fb064ca29931236a5"));

        locationID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(location)

                        .when()
                        .post("school-service/api/location")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;

    }

    @Test(dependsOnMethods ="createLocation")
    public void editLocation()
    {

        locationName=getRandomName();
        locationShortName=getRandomShortName();
        locationCapacity=getRandomCapacity();

        LocationClass location=new LocationClass();
        location.setName(locationName);
        location.setShortName(locationShortName);
        location.setId(locationID);
        location.setType("CLASS");
        location.setCapacity(locationCapacity);

        location.setSchool(new School("5fe07e4fb064ca29931236a5"));

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(location)

                        .when()
                        .put("school-service/api/location")

                        .then()
                        .log().body()
                        .statusCode(200)

        ;

    }

    @Test(dependsOnMethods ="editLocation")
    public void deleteLocation()
    {

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .pathParam("locationID",locationID)

                        .when()
                        .delete("school-service/api/location/{locationID}")

                        .then()
                        .log().body()
                        .statusCode(200)
        ;

    }

    @Test(dependsOnMethods ="deleteLocation")
    public void deleteLocationNegative()
    {

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("locationID",locationID)

                .when()
                .delete("school-service/api/location/{locationID}")

                .then()
                .log().body()
                .statusCode(400)
        ;

    }

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(4).toLowerCase();
    }
    public String getRandomShortName() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }
    public String getRandomCapacity() {
        return RandomStringUtils.randomNumeric(3);
    }

}
