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
import org.springframework.web.server.WebHandler;

import co.com.bancolombia.api.dto.SaveSucursalDTO;
import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.usecase.sucursal.GetSucursalFranquiciaUseCase;
import co.com.bancolombia.usecase.sucursal.GetSucursalUseCase;
import co.com.bancolombia.usecase.sucursal.GetSucursalesUseCase;
import co.com.bancolombia.usecase.sucursal.SaveSucursalUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@DisplayName("Sucursal Controller Tests")
class SucursalControllerTest {

    @Mock
    private GetSucursalUseCase getSucursalUseCase;

    @Mock
    private GetSucursalesUseCase getSucursalesUseCase;

    @Mock
    private SaveSucursalUseCase saveSucursalUseCase;

    @Mock
    private GetSucursalFranquiciaUseCase getSucursalFranquiciaUseCase;

    @InjectMocks
    private SucursalController sucursalController;

    private WebTestClient webTestClient;

    private Sucursal testSucursal;
    private SaveSucursalDTO testSaveDTO;

    @BeforeEach
    void setUp() {
        testSucursal = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal Test")
                .franquiciaId(1L)
                .build();

        testSaveDTO = new SaveSucursalDTO();
        testSaveDTO.setNombre("Sucursal Test");
        testSaveDTO.setFranquiciaId(1L);
    }

    @Test
    @DisplayName("Should get all sucursales successfully")
    void testGetAllSucursales() {
        when(getSucursalesUseCase.execute())
                .thenReturn(Flux.just(testSucursal));

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(sucursalController, null, null, null)
        ).build();

        webTestClient.get()
                .uri("/api/sucursales")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Sucursal.class)
                .hasSize(1)
                .contains(testSucursal);
    }

    @Test
    @DisplayName("Should return empty flux when no sucursales found")
    void testGetAllSucursalesEmpty() {
        when(getSucursalesUseCase.execute())
                .thenReturn(Flux.empty());

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(sucursalController, null, null, null)
        ).build();

        webTestClient.get()
                .uri("/api/sucursales")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Should get sucursal by id successfully")
    void testGetSucursalById() {
        when(getSucursalUseCase.execute(1L))
                .thenReturn(Mono.just(testSucursal));

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(sucursalController, null, null, null)
        ).build();

        webTestClient.get()
                .uri("/api/sucursales/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Sucursal.class)
                .isEqualTo(testSucursal);
    }

    @Test
    @DisplayName("Should create sucursal successfully")
    void testCreateSucursal() {
        when(saveSucursalUseCase.execute(any(Sucursal.class)))
                .thenReturn(Mono.just(testSucursal));

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(sucursalController, null, null, null)
        ).build();

        webTestClient.post()
                .uri("/api/sucursales")
                .bodyValue(testSaveDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Sucursal.class)
                .isEqualTo(testSucursal);
    }

    @Test
    @DisplayName("Should get sucursales by franquicia id successfully")
    void testGetSucursalByFranquiciaId() {
        when(getSucursalFranquiciaUseCase.execute(1L))
                .thenReturn(Flux.just(testSucursal));

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(sucursalController, null, null, null)
        ).build();

        webTestClient.get()
                .uri("/api/sucursales/franquicia/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Sucursal.class)
                .hasSize(1)
                .contains(testSucursal);
    }

    private RouterFunction<ServerResponse> getRouterFunction(
            SucursalController sucursalController,
            Object franquiciaController,
            Object productoController,
            Object healthController) {
        return org.springframework.web.reactive.function.server.RouterFunctions.route(
                org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/sucursales"),
                sucursalController::getAllSucursales
        ).andRoute(
                org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/sucursales/{id}"),
                sucursalController::getSucursalById
        ).andRoute(
                org.springframework.web.reactive.function.server.RequestPredicates.POST("/api/sucursales"),
                sucursalController::createSucursal
        ).andRoute(
                org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/sucursales/franquicia/{franquiciaId}"),
                sucursalController::getSucursalByFranquiciaId
        );
    }
}
