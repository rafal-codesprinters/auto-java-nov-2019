package restassured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    /*Ten test korzysta z parametrów, dzięki czemu można go wykonać dla całego zestawu danych. W tym przypadku jest to
    * ten sam test, który sprawdza, że dla kolejnych ID użytkowników jakiegoś systemu zwracana jest zawsze taka sama
    * liczba opublikowanych postów, czyli 10.
    *
    * @Test zostało zastąpione przez @ParameterizedTest, a sama metoda testowa musi przyjmować parametry (tu jeden,
    * czyli userId). Do tego konieczne jest podanie danch testowych - używam najprostszego sposobu, czyli listy intów
    * w annocatji @ValueSource.
    *
    * więcej o parametryzacji: https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests*/
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
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

    /*Ten test również jest parametryzowany ale dla odmiany przekazuje dane w formie CSV.*/
    @ParameterizedTest
    @CsvSource({
        "1, -37.3159, 81.1496",
        "2, -43.9509, -34.4618",
        "3, -68.6102, -47.0653",
        "4, 29.4572, -164.2990",
        "5, -31.8129, 62.5342"
    })
    void apiReturnsValidUserGeoData(int userId, String geoLat, String geoLng) {
        /*Test generuje następujące zapytanie np. dla usera 3: https://jsonplaceholder.typicode.com/users/3
        * Poniżej pokazano, jak można zapisać odpowiedź restową i na niej wykonywać wiele asercji.*/
        Response response = given().pathParams("userId", userId).when().get("https://jsonplaceholder.typicode.com/users/{userId}").then().statusCode(200).extract().response();
        Assertions.assertEquals(geoLat, response.path("address.geo.lat"));
        Assertions.assertEquals(geoLng, response.path("address.geo.lng"));
    }

}
