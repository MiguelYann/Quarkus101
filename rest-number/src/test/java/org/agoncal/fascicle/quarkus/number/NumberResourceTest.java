package org.agoncal.fascicle.quarkus.number;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.jboss.resteasy.util.HttpHeaderNames.ACCEPT;

@QuarkusTest
public class NumberResourceTest {

  @Test
  public void testApiEndpointWithTextPlain() {
    given()
      .header(ACCEPT, MediaType.TEXT_PLAIN)
      .when().get("/api/numbers")
      .then()
      .statusCode(200)
      .body(is("Hello RESTEasy"));
  }

  @Test
  void shouldGenerateBookNumber() {
    given()
      .header(ACCEPT, MediaType.APPLICATION_JSON).
      when()
      .get("/api/numbers").
      then()
      .statusCode(OK.getStatusCode())
      .body("$", hasKey("isbn_10"))
      .body("$", hasKey("isbn_13"))
      .body("$", hasKey("asin"))
      .body("$", hasKey("ean_8"))
      .body("$", hasKey("ean_13"))
      .body("$", not(hasKey("generationDate")));
  }

}
