package rest;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class RestTests {

    @Test
    void canFetchAllPosts() {
        // https://jsonplaceholder.typicode.com/posts
        get("https://jsonplaceholder.typicode.com/posts/").then().body("", hasSize(100));
    }

    @Test
    void canFetchAllPostsForOneUser() {
        // https://jsonplaceholder.typicode.com/posts?userId=2
        given().
                param("userId", 2).
        when().
                get("https://jsonplaceholder.typicode.com/posts").
        then().
                statusCode(200).
                body("", hasSize(10));
    }

    @Test
    void apiReturnsValidUserGeoData() {
        // https://jsonplaceholder.typicode.com/users/3
        Response response = given().pathParams("userId", 3).when().get("https://jsonplaceholder.typicode.com/users/{userId}").then().statusCode(200).extract().response();
        Assertions.assertEquals("-68.6102", response.path("address.geo.lat"));
        Assertions.assertEquals("-47.0653", response.path("address.geo.lng"));
    }

}
