package org.alas.backend.handlers;

import org.alas.backend.documents.Ping;
import org.alas.backend.repositories.PingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PingHandler {

    @Autowired
    private PingRepository pingRepository;

    public Flux<Ping> getPings(){
        return pingRepository.findAll();
    }

    public void savePing(Ping ping){
        pingRepository.save(ping).block();
    }
}
