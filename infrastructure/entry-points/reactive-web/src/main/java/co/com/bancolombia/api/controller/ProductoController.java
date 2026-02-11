package co.com.bancolombia.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import co.com.bancolombia.api.dto.ModifyStockDTO;
import co.com.bancolombia.api.dto.SaveProductoDTO;
import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.usecase.producto.GetProductoUseCase;
import co.com.bancolombia.usecase.producto.GetProductosSucursalUseCase;
import co.com.bancolombia.usecase.producto.GetProductosUseCase;
import co.com.bancolombia.usecase.producto.ModifyStockUseCase;
import co.com.bancolombia.usecase.producto.SaveProductoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class ProductoController {

        private final GetProductoUseCase getProductoUseCase;
        private final GetProductosUseCase getProductosUseCase;
        private final SaveProductoUseCase saveProductoUseCase;
        private final GetProductosSucursalUseCase getProductosSucursalUseCase;
        private final ModifyStockUseCase modifyStockUseCase;

        public Mono<ServerResponse> getAllProductos(ServerRequest request) {
                Flux<Producto> flujo = getProductosUseCase.execute()
                                .doOnSubscribe(s -> log.info("GET /productos"))
                                .doOnNext(p -> log.info("Fetched producto: {}", p.getId()))
                                .doOnError(e -> log.error("Error fetching productos", e))
                                .doOnComplete(() -> log.info("Successfully fetched all productos"))
                                .switchIfEmpty(
                                                Flux.error(new RuntimeException("No productos found")));
                return ServerResponse.ok()
                                .body(flujo, Producto.class);

        }

        public Mono<ServerResponse> getProductoById(ServerRequest request) {
                String id = request.pathVariable("id");
                Mono<Producto> mono = getProductoUseCase.execute(Long.parseLong(id))
                                .doOnSubscribe(s -> log.info("GET /productos/{}", id))
                                .doOnNext(p -> log.info("Fetched producto: {}", p.getId()))
                                .doOnError(e -> log.error("Error fetching producto with id " + id, e));
                return ServerResponse.ok()
                                .body(mono, Producto.class);
        }

        public Mono<ServerResponse> createProducto(ServerRequest request) {
                Mono<Producto> mono = request.bodyToMono(SaveProductoDTO.class)
                                .map(req -> new Producto(null, req.getNombre(), req.getStock(), req.getSucursalId()))
                                .flatMap(saveProductoUseCase::execute)
                                .doOnSubscribe(s -> log.info("POST /productos - Payload: {}", s))
                                .doOnNext(p -> log.info("Created producto: {}", p.getId()))
                                .doOnError(e -> log.error("Error creating producto", e));
                return ServerResponse.ok()
                                .body(mono, Producto.class);
        }

        public Mono<ServerResponse> getProductoBySucursalId(ServerRequest request) {
                String sucursalId = request.pathVariable("sucursalId");
                Flux<Producto> flujo = getProductosSucursalUseCase.execute(Long.parseLong(sucursalId))
                                .doOnSubscribe(s -> log.info("GET /productos/sucursal/{}", sucursalId))
                                .doOnNext(p -> log.info("Fetched producto: {}", p.getId()))
                                .doOnError(e -> log.error("Error fetching productos for sucursal id " + sucursalId, e));
                return ServerResponse.ok()
                                .body(flujo, Producto.class);
        }

        public Mono<ServerResponse> modifyStock(ServerRequest request) {
                Long productId = Long.valueOf(request.pathVariable("id"));

                Mono<Producto> mono = request.bodyToMono(ModifyStockDTO.class)
                                .flatMap(dto -> {
                                        log.info("PUT /productos/{}/modify-stock - Stock: {}", productId,
                                                        dto.getCantidad());
                                        return modifyStockUseCase.execute(productId, dto.getCantidad());
                                })
                                .doOnNext(p -> log.info("Modified stock for producto: {}", p.getId()))
                                .doOnError(e -> log.error("Error modifying stock for producto with id {}", productId,
                                                e))
                                .switchIfEmpty(Mono.error(
                                                new ResponseStatusException(
                                                                HttpStatus.NOT_FOUND,
                                                                "Producto not found")));

                return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(mono, Producto.class);
        }
}
