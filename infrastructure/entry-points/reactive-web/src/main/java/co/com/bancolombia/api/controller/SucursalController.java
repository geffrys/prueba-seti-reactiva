package co.com.bancolombia.api.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.bancolombia.api.dto.SaveSucursalDTO;
import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.usecase.sucursal.GetSucursalUseCase;
import co.com.bancolombia.usecase.sucursal.GetSucursalesUseCase;
import co.com.bancolombia.usecase.sucursal.SaveSucursalUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class SucursalController {

    private final GetSucursalUseCase getSucursalUseCase;
    private final GetSucursalesUseCase getSucursalesUseCase;
    private final SaveSucursalUseCase saveSucursalUseCase;

    public Mono<ServerResponse> getAllSucursales(ServerRequest request) {
        return ServerResponse.ok()
            .body(
                getSucursalesUseCase.execute(), Sucursal.class
            );
    }

    public Mono<ServerResponse> getSucursalById(ServerRequest request) {
        String id = request.pathVariable("id");
        return getSucursalUseCase.execute(Long.parseLong(id))
            .flatMap(sucursal -> ServerResponse.ok().bodyValue(sucursal))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createSucursal(ServerRequest request) {
        return request.bodyToMono(SaveSucursalDTO.class)
            .map(req -> new Sucursal(null, req.getNombre(), req.getFranquiciaId()))
            .flatMap(saveSucursalUseCase::execute)
            .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
    }
}
