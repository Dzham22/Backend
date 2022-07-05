package lesson3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.number.OrderingComparison.lessThan;

public class GetRecipeTest {



    private final String apiKey = "050cee2795cc4fdeb283169ce4fce3fe";

    @BeforeEach
    void beforeTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @Test
    void getRecipeWithAuthenticationPositiveTest() {
        given()
                .queryParam("apiKey", apiKey)
                .response()
                .when()
                .get("https://spoonacular.com/food-api/docs#Authentication")
                .prettyPeek()
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .statusLine(containsString("OK"))
                .time(lessThan(2000L));


    }

    @Test
    void getQueryPositiveTest(){
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("query", "burger")
                .response()
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?query=burger")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(3000L))
                .body(stringContainsInOrder("Falafel Burger"));
    }


    @Test
    void getCuisinePositiveTest(){
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("cuisine", "italian")
                .response()
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?cuisine=italian")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(2000L))
                .body(stringContainsInOrder("results"));
    }

    @Test
    void getUnauthorizedNegativeTest(){
        given()
                .queryParam("diet", "vegetarian")
                .response()
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?diet=vegetarian")
                .then()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized")
                .time(lessThan(2000L))
                .body(stringContainsInOrder("failure"));
    }


    @Test
    void getNegativeTest(){
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("nunber", "A")
                .response()
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch?number=A")
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .time(lessThan(2000L))
                .body(stringContainsInOrder("html"));
    }


}
