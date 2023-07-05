package org.agoncal.fascicle.quarkus.number;

import com.github.javafaker.Faker;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.eclipse.microprofile.faulttolerance.Timeout;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Path("/api/numbers")
public class NumberResource {

  @Inject
  Logger LOGGER;

  @ConfigProperty(name = "number.separator", defaultValue = "false")
  boolean separator;

  @ConfigProperty(name = "seconds.sleep", defaultValue = "0")
  int secondsToSleep = 0;

  @GET
  @Timeout(250)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Generates book numbers", description = "These book numbers have several formats: ISBN, ASIN and EAN")
  @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType
    .APPLICATION_JSON, schema = @Schema(implementation = BookNumbers.class)))
  public Response generateBookNumbers() throws InterruptedException {

    LOGGER.info("Waiting for " + secondsToSleep + " seconds");
    TimeUnit.SECONDS.sleep(secondsToSleep);

    LOGGER.info("generating book numbers");
    final var faker = new Faker();
    BookNumbers bookNumbers = new BookNumbers();
    bookNumbers.isbn10 = faker.code().isbn10(separator);
    bookNumbers.isbn13 = faker.code().isbn13(separator);
    bookNumbers.asin = faker.code().asin();
    bookNumbers.ean8 = faker.code().ean8();
    bookNumbers.ean13 = faker.code().ean13();
    bookNumbers.generationDate = Instant.now();

    return Response.ok(bookNumbers).build();
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "Hello RESTEasy";
  }
}
