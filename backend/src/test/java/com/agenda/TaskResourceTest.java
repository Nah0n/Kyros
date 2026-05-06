package com.agenda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskResourceTest {

  static String token;
  static Long sessionId;
  static Long taskId;

  @BeforeAll
  static void setup() {
    token =
        given()
            .contentType(ContentType.JSON)
            .body("{\"login\": \"taskuser\", \"password\": \"test123\"}")
            .when()
            .post("/auth/register")
            .then()
            .statusCode(201)
            .extract()
            .path("token");

    sessionId =
        ((Integer)
                given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .body(
                        "{\"title\": \"Session test\", \"plannedAt\": \"2026-05-07T10:00:00\", \"duration\": 60, \"method\": \"POMODORO\"}")
                    .when()
                    .post("/sessions")
                    .then()
                    .statusCode(201)
                    .extract()
                    .path("id"))
            .longValue();
  }

  @Test
  @Order(1)
  public void createTask_success() {
    taskId =
        ((Integer)
                given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .body("{\"title\": \"Faire les exercices\"}")
                    .when()
                    .post("/sessions/" + sessionId + "/tasks")
                    .then()
                    .statusCode(201)
                    .body("title", equalTo("Faire les exercices"))
                    .body("done", equalTo(false))
                    .extract()
                    .path("id"))
            .longValue();
  }

  @Test
  @Order(2)
  public void createTask_sessionNotFound() {
    given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .body("{\"title\": \"Faire les exercices\"}")
        .when()
        .post("/sessions/99999/tasks")
        .then()
        .statusCode(500);
  }

  @Test
  @Order(3)
  public void updateTask_success() {
    given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .body("{\"title\": \"Nouveau titre\"}")
        .when()
        .put("/sessions/" + sessionId + "/tasks/" + taskId)
        .then()
        .statusCode(200)
        .body("title", equalTo("Nouveau titre"));
  }

  @Test
  @Order(4)
  public void toggleDone_toTrue() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/sessions/" + sessionId + "/tasks/" + taskId + "/toggle")
        .then()
        .statusCode(200)
        .body("done", equalTo(true));
  }

  @Test
  @Order(5)
  public void toggleDone_toFalse() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .put("/sessions/" + sessionId + "/tasks/" + taskId + "/toggle")
        .then()
        .statusCode(200)
        .body("done", equalTo(false));
  }

  @Test
  @Order(6)
  public void deleteTask_success() {
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .delete("/sessions/" + sessionId + "/tasks/" + taskId)
        .then()
        .statusCode(204);
  }
}
