package org.alas.backend.routers;

import org.alas.backend.routerhandler.CandidateRouterHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;

@Configuration
public class CandidateRouter{

    @Bean
    RouterFunction<?> candidateRouterFunction(CandidateRouterHandler candidateRouterHandler){

        return RouterFunctions.route().GET("/test",candidateRouterHandler::getAll).build();
    }
}
