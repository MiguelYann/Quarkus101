package org.agoncal.fascicle.quarkus.book.client;

import javax.json.bind.annotation.JsonbProperty;

public class IsbnNumbers {
  @JsonbProperty("isbn_10")
  public String isbn10;

  @JsonbProperty("isbn_13")
  public String isbn13;
}
