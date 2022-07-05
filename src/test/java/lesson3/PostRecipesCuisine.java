package lesson3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.hamcrest.number.OrderingComparison.lessThan;


class PostRecipesCuisine {

    private final String apiKey = "d327a923dc3e4d72962b22f4b095db96";

     @BeforeAll
     static void setUp(){
         RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
     }


     @Test
    void postRecipeCuisineWithAuthenticationPositiveTest() {
         given()
                 .queryParam("apiKey", apiKey)
                 .response()
                 .when()
                 .post("https://api.spoonacular.com/recipes/cuisine")
                 .prettyPeek()
                 .then()
                 .statusCode(200)
                 .statusLine("HTTP/1.1 200 OK")
                 .statusLine(containsString("OK"))
                 .time(lessThan(2000L));


     }
    @Test
    void postRecipeWithTitleQueryParametersPositiveTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("title", "Pork roast with green beans")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine?title=Pork roast with green beans")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(lessThan(3000L))
                .body(stringContainsInOrder("Italian"));
    }


    @Test
    void postRecipeCuisineUnauthorizedNegativeTest() {
        given()
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized")
                .time(lessThan(3000L))
                .body(stringContainsInOrder("You are not authorized. Please read https://spoonacular.com/food-api/docs#Authentication"));
    }

    @Test
    void postRecipeCuisineInternalServerErrorNegativeTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("title", "Pork roast with green beans")
                .queryParam("ingredientList", "3 oz pork shoulder")
                .queryParam("language", "")
                .when()
                .post("https://api.spoonacular.com/recipes/cuisine?title=Pork roast with green beans&ingredientList=3 oz pork shoulder&language=")
                .then()
                .statusCode(500)
                .statusLine("HTTP/1.1 500 Internal Server Error")
                .time(lessThan(3000L))
                .body(stringContainsInOrder("head"));
    }

}






