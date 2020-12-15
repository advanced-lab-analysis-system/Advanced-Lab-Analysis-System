package org.alas.backend.routerhandler;

import org.alas.backend.model.Exam;
import org.alas.backend.repository.ExamsRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CandidateRouterHandler {

    ExamsRepository examsRepository;

    public CandidateRouterHandler(ExamsRepository examsRepository) {
        this.examsRepository = examsRepository;
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest){
//        return examsRepository.findAll()
//                .flatMap(exam ->
//                    Mono.just(exam)
//                        .flatMap(item->
//                                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(item,Exam.class))
//                );

        Mono<Exam> examMono = examsRepository.findById("1234");
        System.out.println(examMono.
                flatMap(item->
                    System.out.println(item.toString())
                ));
//        return examsRepository.findById("1234")
//                .flatMap(item->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(item));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(examMono,Exam.class);
    }
}
