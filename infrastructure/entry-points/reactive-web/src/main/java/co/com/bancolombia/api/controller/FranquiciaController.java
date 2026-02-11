package co.com.bancolombia.api.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.bancolombia.api.dto.SaveFranquiciaDTO;
import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.usecase.franquicia.GetFranquiciaDetailedUseCase;
import co.com.bancolombia.usecase.franquicia.GetFranquiciaUseCase;
import co.com.bancolombia.usecase.franquicia.GetFranquiciasUseCase;
import co.com.bancolombia.usecase.franquicia.SaveFranquiciaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class FranquiciaController {

    private final GetFranquiciaUseCase getFranquiciaUseCase;
    private final GetFranquiciasUseCase getFranquiciasUseCase;
    private final SaveFranquiciaUseCase saveFranquiciaUseCase;
    private final GetFranquiciaDetailedUseCase getFranquiciaDetailedUseCase;

    public Mono<ServerResponse> getAllFranquicias(ServerRequest request) {
        Flux<Franquicia> flujo = getFranquiciasUseCase.execute()
                .doOnSubscribe(s -> log.info("GET /franquicias"))
                .doOnNext(f -> log.info("Fetched franquicia: {}", f.getId()))
                .doOnError(e -> log.error("Error fetching franquicias", e))
                .doOnComplete(() -> log.info("Successfully fetched all franquicias"));
                // .switchIfEmpty(
                //         Flux.error(new RuntimeException("No franquicias found")));

        return ServerResponse.ok()
                .body(flujo, Franquicia.class);
    }

    public Mono<ServerResponse> getFranquiciaById(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Franquicia> mono = getFranquiciaUseCase.execute(Long.parseLong(id))
                .doOnSubscribe(s -> log.info("GET /franquicias/{}", id))
                .doOnNext(f -> log.info("Fetched franquicia: {}", f.getId()))
                .doOnError(e -> log.error("Error fetching franquicia with id " + id, e));
        
        return ServerResponse.ok()
                .body(mono, Franquicia.class);

        
    }

    public Mono<ServerResponse> createFranquicia(ServerRequest request) {
        Mono<Franquicia> mono = request.bodyToMono(SaveFranquiciaDTO.class)
                .map(req -> new Franquicia(null, req.getNombre()))
                .flatMap(saveFranquiciaUseCase::execute)
                .doOnSubscribe(s -> log.info("POST /franquicias"))
                .doOnNext(f -> log.info("Created franquicia: {}", f.getId()))
                .doOnError(e -> log.error("Error creating franquicia", e));
        return ServerResponse.ok()
                .body(mono, Franquicia.class);
    }

    public Mono<ServerResponse> getFranquiciaDetailedById(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<?> mono = getFranquiciaDetailedUseCase.execute(Long.parseLong(id))
                .doOnSubscribe(s -> log.info("GET /franquicias/detailed/{}", id))
                .doOnNext(f -> log.info("Fetched detailed franquicia: {}", f.getFranquicia().getId()))
                .doOnError(e -> log.error("Error fetching detailed franquicia with id " + id, e));
        
        return ServerResponse.ok()
                .body(mono, Object.class);

        
    }   
}
