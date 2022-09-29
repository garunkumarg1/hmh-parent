package com.hmh.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestConsumerRoute extends RouteBuilder{
	
	@Override
    public void configure() throws Exception {
        // call the embedded rest service from the TutorialController
        restConfiguration().host("localhost").component("servlet").port(8080);
        
        from("timer:hello?period={{timer.period}}")
            .setHeader("id", simple("${random(1,5)}"))
            .to("rest:get:hmh-rest-api/tutorials/{id}")
            .log("${body}");
    }

}
