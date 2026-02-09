package co.com.bancolombia.api.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.bancolombia.api.dto.SaveProductoDTO;
import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.usecase.producto.GetProductoUseCase;
import co.com.bancolombia.usecase.producto.GetProductosUseCase;
import co.com.bancolombia.usecase.producto.SaveProductoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class ProductoController {

    private final GetProductoUseCase getProductoUseCase;
    private final GetProductosUseCase getProductosUseCase;
    private final SaveProductoUseCase saveProductoUseCase;

    public Mono<ServerResponse> getAllProductos(ServerRequest request) {
        return ServerResponse.ok()
            .body(
                getProductosUseCase.execute(), Producto.class
            );
    }

    public Mono<ServerResponse> getProductoById(ServerRequest request) {
        String id = request.pathVariable("id");
        return getProductoUseCase.execute(Long.parseLong(id))
            .flatMap(producto -> ServerResponse.ok().bodyValue(producto))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createProducto(ServerRequest request) {
        return request.bodyToMono(SaveProductoDTO.class)
            .map(req -> {
                Long stock = req.getStock() == null ? null : req.getStock().longValue();
                return new Producto(null, req.getNombre(), stock, req.getSucursalId());
            })
            .flatMap(saveProductoUseCase::execute)
            .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
    }
}
