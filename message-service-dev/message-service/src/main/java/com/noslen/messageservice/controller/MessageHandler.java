package com.noslen.messageservice.controller;

import com.noslen.messageservice.model.Msg;
import com.noslen.messageservice.service.MsgService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
class MessageHandler {


    private final MsgService messageService;

    MessageHandler(MsgService messageService) {
        this.messageService = messageService;
    }


    Mono<ServerResponse> getById(ServerRequest r) {
        return defaultReadResponse(this.messageService.get(id(r)));
    }

    Mono<ServerResponse> all(ServerRequest r) {
        System.out.println(defaultReadResponse(this.messageService.all()));
        return defaultReadResponse(this.messageService.all());
    }

    Mono<ServerResponse> deleteById(ServerRequest r) {
        return defaultReadResponse(this.messageService.delete(id(r)));
    }

    Mono<ServerResponse> create(ServerRequest request) {
        Flux<Msg> flux = request
                .bodyToFlux(Msg.class)
                .flatMap(toWrite -> this.messageService.create(toWrite.getSender(),toWrite.getMsg()));
        return defaultWriteResponse(flux);
    }


    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Msg> profiles) {
        return Mono
                .from(profiles)
                .flatMap(p -> ServerResponse
                        .created(URI.create("/profiles/" + p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build()
                );
    }


    private static Mono<ServerResponse> defaultReadResponse(Publisher<Msg> profiles) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profiles, Msg.class);
    }

    private static String id(ServerRequest r) {
        return r.pathVariable("id");
    }
}
