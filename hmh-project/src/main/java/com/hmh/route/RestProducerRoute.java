package com.hmh.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.hmh.service.TutorialService;

@Component
public class RestProducerRoute  extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
            .contextPath("/hmh-camel-api").apiContextPath("/api-doc")
                .apiProperty("api.title", "HMH REST API")
                .apiProperty("api.version", "1.0")
                .apiProperty("cors", "true")
                .apiContextRouteId("hmh-api")
                .port("8080")
            .bindingMode(RestBindingMode.json);

        rest("/tutorials").description("Tutorials REST service")
            .get("/").description("The list of all tutorials")
                .route().routeId("tutorials-api")
                .bean(TutorialService.class, "getAllTutorials")
                .endRest()
            .get("/{id}").description("Details of a Tutorial by id")
                .route().routeId("tutorial-api")
                .bean(TutorialService.class, "getTutorialById(${header.id})");
    }
}