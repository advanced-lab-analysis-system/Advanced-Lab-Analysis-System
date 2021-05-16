package org.alas.backend.services;

import org.alas.backend.dataobjects.exam.submission.coding.judge0.CreationResponse;
import org.alas.backend.dataobjects.exam.submission.coding.judge0.GetSubmissionResponse;
import org.alas.backend.dataobjects.exam.submission.coding.judge0.JudgeRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class JudgeService {

    private final WebClient webClient;

    public JudgeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://judge0-ce.p.rapidapi.com/submissions").build();
    }

    public CreationResponse createSubmission(JudgeRequest judgeRequest) {

        return this.webClient.post().uri("?base64_encoded=true&fields=*")
                .header("content-type", "application/json")
                .header("x-rapidapi-key", "e9cfb11d99msh219ec5b449fe0fap1c421fjsn7cbad978f447")
                .header("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
                .body(BodyInserters.fromValue(judgeRequest))
                .retrieve().bodyToMono(CreationResponse.class).block();
    }

    public GetSubmissionResponse getSubmission(String token) {
        System.out.println(token);
        return this.webClient.get().uri("/" + token + "?base64_encoded=true&fields=*")
                .header("content-type", "application/json")
                .header("x-rapidapi-key", "e9cfb11d99msh219ec5b449fe0fap1c421fjsn7cbad978f447")
                .header("x-rapidapi-host", "judge0-ce.p.rapidapi.com")
                .retrieve().bodyToMono(GetSubmissionResponse.class).block();
    }
}

