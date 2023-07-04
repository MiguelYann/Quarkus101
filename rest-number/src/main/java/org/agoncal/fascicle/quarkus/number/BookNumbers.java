package org.agoncal.fascicle.quarkus.number;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Instant;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

@Schema(description = "Several formats of book numbers")
public class BookNumbers {

  @Schema(required = true)
  @JsonbProperty("isbn_10")
  public String isbn10;

  @Schema(required = true)
  @JsonbProperty("isbn_13")
  public String isbn13;
  public String asin;

  @JsonbProperty("ean_8")
  public String ean8;

  @JsonbProperty("ean_13")
  public String ean13;

  @JsonbTransient
  public Instant generationDate;
}
