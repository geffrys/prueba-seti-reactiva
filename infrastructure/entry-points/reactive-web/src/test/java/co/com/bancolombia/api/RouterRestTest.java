package co.com.bancolombia.api;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.com.bancolombia.api.controller.FranquiciaController;
import co.com.bancolombia.api.controller.HealthController;
import co.com.bancolombia.api.controller.ProductoController;
import co.com.bancolombia.api.controller.SucursalController;
import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.usecase.franquicia.GetFranquiciaUseCase;
import co.com.bancolombia.usecase.franquicia.GetFranquiciasUseCase;
import co.com.bancolombia.usecase.franquicia.SaveFranquiciaUseCase;
import co.com.bancolombia.usecase.producto.GetProductoUseCase;
import co.com.bancolombia.usecase.producto.GetProductosSucursalUseCase;
import co.com.bancolombia.usecase.producto.GetProductosUseCase;
import co.com.bancolombia.usecase.producto.SaveProductoUseCase;
import co.com.bancolombia.usecase.sucursal.GetSucursalFranquiciaUseCase;
import co.com.bancolombia.usecase.sucursal.GetSucursalUseCase;
import co.com.bancolombia.usecase.sucursal.GetSucursalesUseCase;
import co.com.bancolombia.usecase.sucursal.SaveSucursalUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
@WebFluxTest(RouterRest.class)
@Import({FranquiciaController.class, SucursalController.class, ProductoController.class, HealthController.class})
@DisplayName("Router Rest Integration Tests")
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GetFranquiciaUseCase getFranquiciaUseCase;

    @MockitoBean
    private GetFranquiciasUseCase getFranquiciasUseCase;

    @MockitoBean
    private SaveFranquiciaUseCase saveFranquiciaUseCase;

        @MockitoBean
        private co.com.bancolombia.usecase.franquicia.GetFranquiciaDetailedUseCase getFranquiciaDetailedUseCase;

    @MockitoBean
    private GetSucursalUseCase getSucursalUseCase;

    @MockitoBean
    private GetSucursalesUseCase getSucursalesUseCase;

    @MockitoBean
    private SaveSucursalUseCase saveSucursalUseCase;

    @MockitoBean
    private GetSucursalFranquiciaUseCase getSucursalFranquiciaUseCase;

    @MockitoBean
    private GetProductoUseCase getProductoUseCase;

    @MockitoBean
    private GetProductosUseCase getProductosUseCase;

    @MockitoBean
    private SaveProductoUseCase saveProductoUseCase;

    @MockitoBean
    private GetProductosSucursalUseCase getProductosSucursalUseCase;

    @MockitoBean
        private co.com.bancolombia.usecase.producto.ModifyStockUseCase modifyStockUseCase;

        @MockitoBean
        private co.com.bancolombia.usecase.producto.DeleteProductoSucursalUseCase deleteProductoSucursalUseCase;

        @MockitoBean
        private co.com.bancolombia.usecase.producto.DeleteProductoUseCase deleteProductoUseCase;

        @MockitoBean
        private co.com.bancolombia.usecase.producto.GetMaxStockProductsByFranquiciaUseCase getMaxStockProductsByFranquiciaUseCase;

    @Test
    @DisplayName("Health endpoint should be accessible")
    void testHealthEndpoint() {
        webTestClient.get()
                .uri("/api/health")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Franquicia routes should be properly registered")
    void testFranquiciaRoutes() {
        when(getFranquiciasUseCase.execute())
                .thenReturn(Flux.empty());

        webTestClient.get()
                .uri("/api/franquicias")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Sucursal routes should be properly registered")
    void testSucursalRoutes() {
        when(getSucursalesUseCase.execute())
                .thenReturn(Flux.empty());

        webTestClient.get()
                .uri("/api/sucursales")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Producto routes should be properly registered")
    void testProductoRoutes() {
        when(getProductosUseCase.execute())
                .thenReturn(Flux.empty());

        webTestClient.get()
                .uri("/api/productos")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Get franquicia by id endpoint should work")
    void testGetFranquiciaByIdEndpoint() {
        Franquicia franquicia = Franquicia.builder()
                .id(1L)
                .nombre("Test Franquicia")
                .build();

        when(getFranquiciaUseCase.execute(1L))
                .thenReturn(Mono.just(franquicia));

        webTestClient.get()
                .uri("/api/franquicias/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franquicia.class)
                .isEqualTo(franquicia);
    }

    @Test
    @DisplayName("Get sucursal by franquicia id endpoint should work")
    void testGetSucursalByFranquiciaIdEndpoint() {
        Sucursal sucursal = Sucursal.builder()
                .id(1L)
                .nombre("Test Sucursal")
                .franquiciaId(1L)
                .build();

        when(getSucursalFranquiciaUseCase.execute(1L))
                .thenReturn(Flux.just(sucursal));

        webTestClient.get()
                .uri("/api/sucursales/franquicia/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Sucursal.class)
                .contains(sucursal);
    }

    @Test
    @DisplayName("Get producto by sucursal id endpoint should work")
    void testGetProductoBySucursalIdEndpoint() {
        Producto producto = Producto.builder()
                .id(1L)
                .nombre("Test Producto")
                .stock(50)
                .sucursalId(1L)
                .build();

        when(getProductosSucursalUseCase.execute(1L))
                .thenReturn(Flux.just(producto));

        webTestClient.get()
                .uri("/api/productos/sucursal/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Producto.class)
                .contains(producto);
    }
}
