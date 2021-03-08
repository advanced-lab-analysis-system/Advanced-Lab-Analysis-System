package org.alas.backend.handlers;

import org.alas.backend.dto.CreationResponse;
import org.alas.backend.dto.GetSubmissionResponse;
import org.alas.backend.dto.JudgeRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class JudgeHandler {

    private final WebClient webClient;

    public JudgeHandler(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://judge0-ce.p.rapidapi.com/submissions").build();
    }

    public Mono<CreationResponse> createSubmission(JudgeRequestDTO judgeRequestDTO){

         return this.webClient.post().uri("?base64_encoded=true&fields=*")
                 .header("content-type", "application/json")
                 .header("x-rapidapi-key", "fe790da120msh372c971c0124cefp1a20f3jsnde5e399ad9ad")
                 .header("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
                 .body(BodyInserters.fromValue(judgeRequestDTO))
                 .retrieve().bodyToMono(CreationResponse.class);
    }

    public Mono<GetSubmissionResponse> getSubmission(String token){
        System.out.println(token);
        return this.webClient.get().uri("/" + token + "?base64_encoded=true&fields=*")
                .header("content-type", "application/json")
                .header("x-rapidapi-key", "fe790da120msh372c971c0124cefp1a20f3jsnde5e399ad9ad")
                .header("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
                .retrieve().bodyToMono(GetSubmissionResponse.class);
    }
}
