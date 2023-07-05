package org.agoncal.fascicle.quarkus.book.heath;

import org.agoncal.fascicle.quarkus.book.BookResource;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.inject.Inject;

public class PingBookResourceHealthCheck implements HealthCheck {

  @Inject
  BookResource bookResource;

  @Override
  public HealthCheckResponse call() {
    bookResource.hello();
    return HealthCheckResponse.named("Ping Number REST Endpoint").up().build();
  }
}
