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



public class ExampleGetRecipePositiveTest {

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
                .addQueryParam("query", "burger")
                .addQueryParam("cuisine", "italian")
                .addQueryParam("diet", "vegetarian")
                .addQueryParam("nunber", "A")
                .addQueryParam("Ingredients", "eggs")
                .addQueryParam("addRecipeInformation", "true")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }



    @Test
    void getQueryPositiveTest() {
        given().spec(requestSpecification)
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?query=burger")
                .then()
                .body(stringContainsInOrder("results"))
                .spec(responseSpecification);

    }

    @Test
    void getCuisinePositiveTest() {
        given().spec(requestSpecification)
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?cuisine=italian")
                .then()
                .body(stringContainsInOrder("results"))
                .spec(responseSpecification);
    }

    @Test
    void getExcludeIngredientsPositiveTest() {
        given().spec(requestSpecification)
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?excludeIngredients=eggs")
                .then()
                .body(stringContainsInOrder("results"))
                .spec(responseSpecification);
    }

    @Test
    void getAddRecipeInformationPositiveTest() {
        given().spec(requestSpecification)
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?addRecipeInformation=true")
                .then()
                .body(stringContainsInOrder("results"))
                .spec(responseSpecification);
    }



}
