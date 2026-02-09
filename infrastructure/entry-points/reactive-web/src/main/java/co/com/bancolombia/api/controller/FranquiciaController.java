package co.com.bancolombia.api.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.bancolombia.api.dto.SaveFranquiciaDTO;
import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.usecase.franquicia.GetFranquiciaUseCase;
import co.com.bancolombia.usecase.franquicia.GetFranquiciasUseCase;
import co.com.bancolombia.usecase.franquicia.SaveFranquiciaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class FranquiciaController {

    private final GetFranquiciaUseCase getFranquiciaUseCase;
    private final GetFranquiciasUseCase getFranquiciasUseCase;
    private final SaveFranquiciaUseCase saveFranquiciaUseCase;

    public Mono<ServerResponse> getAllFranquicias(ServerRequest request) {
        return ServerResponse.ok()
        .body(
            getFranquiciasUseCase.execute(), Franquicia.class
        );
    }

    public Mono<ServerResponse> getFranquiciaById(ServerRequest request) {
        String id = request.pathVariable("id");
        return getFranquiciaUseCase.execute(Long.parseLong(id))
                .flatMap(franquicia -> ServerResponse.ok().bodyValue(franquicia))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createFranquicia(ServerRequest request) {
        return request.bodyToMono(SaveFranquiciaDTO.class)
        .map(req -> {
            return new Franquicia(null, req.getNombre());
        })
        .flatMap(saveFranquiciaUseCase::execute)
        .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
    }
    
}
