package org.alas.backend.routerhandler;
import org.alas.backend.model.Exam;
import org.alas.backend.repository.ExamsRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class FacultyRouterHandler {

    ExamsRepository examsRepository;

    public FacultyRouterHandler(ExamsRepository examsRepository) {
        this.examsRepository = examsRepository;
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest){

        return serverRequest.bodyToMono(Exam.class)
                .flatMap( exam ->
                        Mono.just( exam )
                        .flatMap(item-> {
                                    examsRepository.save(item);
                                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(item);
                                }
                        )
                );
    }
}
