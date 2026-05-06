package com.agenda;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceTest {

  @Test
  @Order(1)
  public void register_success() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"login\": \"testuser\", \"password\": \"test123\"}")
        .when()
        .post("/auth/register")
        .then()
        .statusCode(201)
        .body("token", notNullValue())
        .body("user.login", equalTo("testuser"))
        .body("user.password", nullValue());
  }

  @Test
  @Order(2)
  public void register_loginAlreadyTaken() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"login\": \"testuser\", \"password\": \"test123\"}")
        .when()
        .post("/auth/register")
        .then()
        .statusCode(500);
  }

  @Test
  @Order(3)
  public void login_success() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"login\": \"testuser\", \"password\": \"test123\"}")
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .body("token", notNullValue())
        .body("user.login", equalTo("testuser"));
  }

  @Test
  @Order(4)
  public void login_wrongPassword() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"login\": \"testuser\", \"password\": \"wrongpassword\"}")
        .when()
        .post("/auth/login")
        .then()
        .statusCode(500);
  }

  @Test
  @Order(5)
  public void login_userNotFound() {
    given()
        .contentType(ContentType.JSON)
        .body("{\"login\": \"unknown\", \"password\": \"test123\"}")
        .when()
        .post("/auth/login")
        .then()
        .statusCode(500);
  }
}
