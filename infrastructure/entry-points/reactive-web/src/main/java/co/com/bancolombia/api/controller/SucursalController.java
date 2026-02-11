package co.com.bancolombia.api.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.bancolombia.api.dto.SaveSucursalDTO;
import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.usecase.sucursal.GetSucursalFranquiciaUseCase;
import co.com.bancolombia.usecase.sucursal.GetSucursalUseCase;
import co.com.bancolombia.usecase.sucursal.GetSucursalesUseCase;
import co.com.bancolombia.usecase.sucursal.SaveSucursalUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class SucursalController {

    private final GetSucursalUseCase getSucursalUseCase;
    private final GetSucursalesUseCase getSucursalesUseCase;
    private final SaveSucursalUseCase saveSucursalUseCase;
    private final GetSucursalFranquiciaUseCase getSucursalFranquiciaUseCase;

    public Mono<ServerResponse> getAllSucursales(ServerRequest request) {
        Flux<Sucursal> flujo = getSucursalesUseCase.execute()
                .doOnSubscribe(s -> log.info("GET /sucursales"))
                .doOnNext(s -> log.info("Fetched sucursal: {}", s.getId()))
                .doOnError(e -> log.error("Error fetching sucursales", e))
                .doOnComplete(() -> log.info("Successfully fetched all sucursales"))
                .switchIfEmpty(
                        Flux.error(new RuntimeException("No sucursales found")));
        return ServerResponse.ok()
                .body(flujo, Sucursal.class);
    }

    public Mono<ServerResponse> getSucursalById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Sucursal> mono = getSucursalUseCase.execute(Long.parseLong(id))
                .doOnSubscribe(s -> log.info("GET /sucursales/{}", id))
                .doOnNext(s -> log.info("Fetched sucursal: {}", s.getId()))
                .doOnError(e -> log.error("Error fetching sucursal with id " + id, e));
        return ServerResponse.ok()
                .body(mono, Sucursal.class);
    }

    public Mono<ServerResponse> createSucursal(ServerRequest request) {
        Mono<Sucursal> mono = request.bodyToMono(SaveSucursalDTO.class)
                .map(req -> new Sucursal(null, req.getNombre(), req.getFranquiciaId()))
                .flatMap(saveSucursalUseCase::execute)
                .doOnSubscribe(s -> log.info("POST /sucursales - Payload: {}", s))
                .doOnNext(s -> log.info("Created sucursal: {}", s.getId()))
                .doOnError(e -> log.error("Error creating sucursal", e));
        return ServerResponse.ok()
                .body(mono, Sucursal.class);
    }

    public Mono<ServerResponse> getSucursalByFranquiciaId(ServerRequest request) {
        String franquiciaId = request.pathVariable("franquiciaId");
        Flux<Sucursal> flujo = getSucursalFranquiciaUseCase.execute(Long.parseLong(franquiciaId))
                .doOnSubscribe(s -> log.info("GET /sucursales/franquicia/{}", franquiciaId))
                .doOnNext(s -> log.info("Fetched sucursal: {}", s.getId()))
                .doOnError(e -> log.error("Error fetching sucursales for franquicia id " + franquiciaId, e));
        return ServerResponse.ok()
                .body(flujo, Sucursal.class);
    }
}
