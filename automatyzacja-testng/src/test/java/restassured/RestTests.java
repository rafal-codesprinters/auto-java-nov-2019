package restassured;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class RestTests {

    /*Przykładowe testy API restowego napisane z wykorzystaniem REST-assured.
    * Więcej informacji: http://rest-assured.io/*/

    @Test
    void canFetchAllPosts() {
        /*Test generuje następujące zapytanie: https://jsonplaceholder.typicode.com/posts*/
        get("https://jsonplaceholder.typicode.com/posts/").then().body("", hasSize(100));
    }

    @DataProvider
    public static Object[] getUserIds() {
        return new Object[][] {{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}};
    }
    @Test(dataProvider = "getUserIds")
    void canFetchAllPostsForOneUser(int userId) {
        /*Test generuje następujące zapytanie np. dla usera 2: https://jsonplaceholder.typicode.com/posts?userId=2*/
        given().
                param("userId", userId).
        when().
                get("https://jsonplaceholder.typicode.com/posts").
        then().
                statusCode(200).
                body("", hasSize(10));
    }

    @DataProvider
    public static Object[][] getUserGeoCoordinates() {
        return new Object[][] {
            {1, "-37.3159", "81.1496"},
            {2, "-43.9509", "-34.4618"},
            {3, "-68.6102", "-47.0653"},
            {4, "29.4572", "-164.2990"},
            {5, "-31.8129", "62.5342"}
        };
    }
    @Test(dataProvider = "getUserGeoCoordinates")
    void apiReturnsValidUserGeoData(int userId, String geoLat, String geoLng) {
        /*Test generuje następujące zapytanie np. dla usera 3: https://jsonplaceholder.typicode.com/users/3
        * Poniżej pokazano, jak można zapisać odpowiedź restową i na niej wykonywać wiele asercji.*/
        Response response = given().pathParams("userId", userId).when().get("https://jsonplaceholder.typicode.com/users/{userId}").then().statusCode(200).extract().response();
        Assert.assertEquals(response.path("address.geo.lat"), geoLat);
        Assert.assertEquals(response.path("address.geo.lng"), geoLng);
    }

}
