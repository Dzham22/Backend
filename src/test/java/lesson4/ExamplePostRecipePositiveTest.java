package lesson4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;



public class ExamplePostRecipePositiveTest {

    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;
    private final String apiKey = "050cee2795cc4fdeb283169ce4fce3fe";

    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectStatusLine(containsString("OK"))
                .expectResponseTime(Matchers.lessThan(3000L))
                .build();

        RestAssured.responseSpecification = responseSpecification;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("title", "Pork roast with green beans")
                .addQueryParam("ingredientList", "3 oz pork shoulder")
                .addQueryParam("language", "de")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }


    @Test
    void postRecipeCuisineWithAuthenticationPositiveTest() {
        given()
                .spec(requestSpecification)
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .spec(responseSpecification);


    }
    @Test
    void postRecipeWithTitleQueryParametersPositiveTest() {
        given()
                .spec(requestSpecification)
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine?title=Pork roast with green beans")
                .then()
                .body(stringContainsInOrder("Italian"))
                .spec(responseSpecification);
    }

    @Test
    void postIngredientListPositiveTest() {
        given()
                .spec(requestSpecification)
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine?ingredientList=3 oz pork shoulder")
                .prettyPeek()
                .then()
                .body(stringContainsInOrder("Italian"))
                .spec(responseSpecification);
    }

    @Test
    void postLanguagePositiveTest() {
        given()
                .spec(requestSpecification)
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine?language=de")
                .prettyPeek()
                .then()
                .body(stringContainsInOrder("Italian"))
                .spec(responseSpecification);
    }



}