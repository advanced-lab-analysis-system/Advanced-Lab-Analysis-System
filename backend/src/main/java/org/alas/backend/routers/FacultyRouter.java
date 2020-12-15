package org.alas.backend.routers;

import org.alas.backend.routerhandler.FacultyRouterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class FacultyRouter {

    @Bean
    RouterFunction<?> facultyRouterFunction(FacultyRouterHandler facultyRouterHandler){

        return RouterFunctions.route().POST("/test",accept(MediaType.APPLICATION_JSON),facultyRouterHandler::save).build();
    }
}
