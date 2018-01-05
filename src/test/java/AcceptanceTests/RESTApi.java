package AcceptanceTests;

import Utils.BaseTest;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Created by borisgurtovyy on 11/9/17.
 */
public class RESTApi extends BaseTest{

    private String base_url = "https://moviemates-prod.herokuapp.com/api/v1/";

    @Test
    public void getMoviesRequest(){
        given()
                .header("Authorization", "Token " + prop.getProperty("TOKEN"))
                .get(base_url + "movies?days=1")
                .then().statusCode(200);
    }

    @Test
    public void getUserInfoRequest() {
        given()
                .header("Authorization", "Token " + prop.getProperty("TOKEN"))
                .get(base_url + "users/" +prop.getProperty("ID"))
                .then().statusCode(200).body(matchesJsonSchemaInClasspath("jsonUserSchema.json"));
    }
}
