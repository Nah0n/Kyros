package com.agenda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SessionResourceTest {

  static String token;
  static Long sessionId;

  @BeforeAll
  static void setup() {
    token =
        given()
            .contentType(ContentType.JSON)
            .body("{\"login\": \"sessionuser\", \"password\": \"test123\"}")
            .when()
            .post("/auth/register")
            .then()
            .statusCode(201)
            .extract()
            .path("token");
  }

  @Test
  @Order(1)
  public void createSession_success() {
    Response response =
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(
                "{\"title\": \"Révision maths\", \"plannedAt\": \"2026-05-07T10:00:00\", \"duration\": 60, \"method\": \"POMODORO\"}")
            .when()
            .post("/sessions")
            .then()
            .statusCode(201)
            .body("title", equalTo("Révision maths"))
            .body("status", equalTo("PLANNED"))
            .body("method", equalTo("POMODORO"))
            .body("duration", equalTo(60))
            .extract()
            .response();

    sessionId = ((Integer) response.path("id")).longValue();
  }

  @Test
  @Order(2)
  public void getAll_success() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/sessions")
        .then()
        .statusCode(200)
        .body("size()", greaterThan(0));
  }

  @Test
  @Order(3)
  public void getById_success() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/sessions/" + sessionId)
        .then()
        .statusCode(200)
        .body("id", equalTo(sessionId.intValue()))
        .body("title", equalTo("Révision maths"));
  }

  @Test
  @Order(4)
  public void getById_notFound() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/sessions/99999")
        .then()
        .statusCode(500);
  }

  @Test
  @Order(5)
  public void updateSession_success() {
    given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .body("{\"title\": \"Révision physique\", \"duration\": 90}")
        .when()
        .put("/sessions/" + sessionId)
        .then()
        .statusCode(200)
        .body("title", equalTo("Révision physique"))
        .body("duration", equalTo(90));
  }

  @Test
  @Order(6)
  public void startSession_success() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/sessions/" + sessionId + "/start")
        .then()
        .statusCode(200)
        .body("status", equalTo("IN_PROGRESS"));
  }

  @Test
  @Order(7)
  public void startSession_alreadyStarted() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/sessions/" + sessionId + "/start")
        .then()
        .statusCode(500);
  }

  @Test
  @Order(8)
  public void finishSession_success() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/sessions/" + sessionId + "/finish")
        .then()
        .statusCode(200)
        .body("status", equalTo("DONE"));
  }

  @Test
  @Order(9)
  public void finishSession_alreadyDone() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/sessions/" + sessionId + "/finish")
        .then()
        .statusCode(500);
  }

  @Test
  @Order(10)
  public void deleteSession_success() {
    Long newSessionId =
        ((Integer)
                given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .body(
                        "{\"title\": \"À supprimer\", \"plannedAt\": \"2026-05-07T10:00:00\", \"duration\": 30, \"method\": \"FREE\"}")
                    .when()
                    .post("/sessions")
                    .then()
                    .statusCode(201)
                    .extract()
                    .path("id"))
            .longValue();

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .delete("/sessions/" + newSessionId)
        .then()
        .statusCode(204);
  }
}
