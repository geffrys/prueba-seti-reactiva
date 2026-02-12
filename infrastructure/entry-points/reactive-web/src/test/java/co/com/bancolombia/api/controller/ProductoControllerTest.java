package co.com.bancolombia.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.bancolombia.api.dto.SaveProductoDTO;
import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.usecase.producto.GetProductoUseCase;
import co.com.bancolombia.usecase.producto.GetProductosSucursalUseCase;
import co.com.bancolombia.usecase.producto.GetProductosUseCase;
import co.com.bancolombia.usecase.producto.SaveProductoUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@DisplayName("Producto Controller Tests")
class ProductoControllerTest {

    @Mock
    private GetProductoUseCase getProductoUseCase;

    @Mock
    private GetProductosUseCase getProductosUseCase;

    @Mock
    private SaveProductoUseCase saveProductoUseCase;

    @Mock
    private GetProductosSucursalUseCase getProductosSucursalUseCase;

    @InjectMocks
    private ProductoController productoController;

    private WebTestClient webTestClient;

    private Producto testProducto;
    private SaveProductoDTO testSaveDTO;

    @BeforeEach
    void setUp() {
        testProducto = Producto.builder()
                .id(1L)
                .nombre("Producto Test")
                .stock(100)
                .sucursalId(1L)
                .build();

        testSaveDTO = new SaveProductoDTO();
        testSaveDTO.setNombre("Producto Test");
        testSaveDTO.setStock(100);
        testSaveDTO.setSucursalId(1L);
    }

    @Test
    @DisplayName("Should get all productos successfully")
    void testGetAllProductos() {
        when(getProductosUseCase.execute())
                .thenReturn(Flux.just(testProducto));

        webTestClient = WebTestClient.bindToRouterFunction(
                getRouterFunction(productoController)
        ).build();

        webTestClient.get()
                .uri("/api/productos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].nombre").isEqualTo("Producto Test")
                .jsonPath("$[0].stock").isEqualTo(100)
                .jsonPath("$[0].sucursalId").isEqualTo(1);
    }

    @Test
    @DisplayName("Should return empty flux when no productos found")
    void testGetAllProductosEmpty() {
        when(getProductosUseCase.execute())
                .thenReturn(Flux.empty());

        webTestClient = WebTestClient.bindToRouterFunction(
                getRouterFunction(productoController)
        ).build();

        webTestClient.get()
                .uri("/api/productos")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Should get producto by id successfully")
    void testGetProductoById() {
        when(getProductoUseCase.execute(1L))
                .thenReturn(Mono.just(testProducto));

        webTestClient = WebTestClient.bindToRouterFunction(
                getRouterFunction(productoController)
        ).build();

        webTestClient.get()
                .uri("/api/productos/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("Producto Test")
                .jsonPath("$.stock").isEqualTo(100)
                .jsonPath("$.sucursalId").isEqualTo(1);
    }

    @Test
    @DisplayName("Should create producto successfully")
    void testCreateProducto() {
        when(saveProductoUseCase.execute(any(Producto.class)))
                .thenReturn(Mono.just(testProducto));

        webTestClient = WebTestClient.bindToRouterFunction(
                getRouterFunction(productoController)
        ).build();

        webTestClient.post()
                .uri("/api/productos")
                .bodyValue(testSaveDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.nombre").isEqualTo("Producto Test")
                .jsonPath("$.stock").isEqualTo(100)
                .jsonPath("$.sucursalId").isEqualTo(1);
    }

    @Test
    @DisplayName("Should get productos by sucursal id successfully")
    void testGetProductoBySucursalId() {
        when(getProductosSucursalUseCase.execute(1L))
                .thenReturn(Flux.just(testProducto));

        webTestClient = WebTestClient.bindToRouterFunction(
                getRouterFunction(productoController)
        ).build();

        webTestClient.get()
                .uri("/api/productos/sucursal/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].nombre").isEqualTo("Producto Test")
                .jsonPath("$[0].stock").isEqualTo(100)
                .jsonPath("$[0].sucursalId").isEqualTo(1);
    }

    private RouterFunction<ServerResponse> getRouterFunction(ProductoController productoController) {
        return org.springframework.web.reactive.function.server.RouterFunctions.route(
                org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/productos"),
                productoController::getAllProductos
        ).andRoute(
                org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/productos/{id}"),
                productoController::getProductoById
        ).andRoute(
                org.springframework.web.reactive.function.server.RequestPredicates.POST("/api/productos"),
                productoController::createProducto
        ).andRoute(
                org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/productos/sucursal/{sucursalId}"),
                productoController::getProductoBySucursalId
        );
    }
}
