import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

public class PintuApiTest {

    @Test
    public void getPintu() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts");
        response.getBody().prettyPrint();

        Assert.assertThat("Response Is Not Match With Given Schema",
                response.getBody().asString(),
                matchesJsonSchemaInClasspath(
                        "schemas/get-pintu-response.json"));
    }

    @Test
    public void postPintu() {

        JSONObject request  = new JSONObject();
        request.put("title", "recommendation");
        request.put("body", "motorcycle");
        request.put("userId", 12);

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .body(request.toJSONString()).when().post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("recommendation"),
                        "body", equalTo("motorcycle"),
                        "userId", equalTo(12));
    }
}