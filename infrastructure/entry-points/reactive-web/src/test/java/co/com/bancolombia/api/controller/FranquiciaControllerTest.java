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

import co.com.bancolombia.api.dto.SaveFranquiciaDTO;
import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.usecase.franquicia.GetFranquiciaUseCase;
import co.com.bancolombia.usecase.franquicia.GetFranquiciasUseCase;
import co.com.bancolombia.usecase.franquicia.SaveFranquiciaUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@DisplayName("Franquicia Controller Tests")
class FranquiciaControllerTest {

    @Mock
    private GetFranquiciaUseCase getFranquiciaUseCase;

    @Mock
    private GetFranquiciasUseCase getFranquiciasUseCase;

    @Mock
    private SaveFranquiciaUseCase saveFranquiciaUseCase;

    @InjectMocks
    private FranquiciaController franquiciaController;

    private WebTestClient webTestClient;

    private Franquicia testFranquicia;
    private SaveFranquiciaDTO testSaveDTO;

    @BeforeEach
    void setUp() {
        testFranquicia = Franquicia.builder()
                .id(1L)
                .nombre("Franquicia Test")
                .build();

        testSaveDTO = new SaveFranquiciaDTO();
        testSaveDTO.setNombre("Franquicia Test");
    }

    @Test
    @DisplayName("Should get all franquicias successfully")
    void testGetAllFranquicias() {
        when(getFranquiciasUseCase.execute())
                .thenReturn(Flux.just(testFranquicia));

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(franquiciaController)
        ).build();

        webTestClient.get()
                .uri("/api/franquicias")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Franquicia.class)
                .hasSize(1)
                .contains(testFranquicia);
    }

    @Test
    @DisplayName("Should return empty flux when no franquicias found")
    void testGetAllFranquiciasEmpty() {
        when(getFranquiciasUseCase.execute())
                .thenReturn(Flux.empty());

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(franquiciaController)
        ).build();

        webTestClient.get()
                .uri("/api/franquicias")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Should get franquicia by id successfully")
    void testGetFranquiciaById() {
        when(getFranquiciaUseCase.execute(1L))
                .thenReturn(Mono.just(testFranquicia));

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(franquiciaController)
        ).build();

        webTestClient.get()
                .uri("/api/franquicias/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franquicia.class)
                .isEqualTo(testFranquicia);
    }

    @Test
    @DisplayName("Should create franquicia successfully")
    void testCreateFranquicia() {
        when(saveFranquiciaUseCase.execute(any(Franquicia.class)))
                .thenReturn(Mono.just(testFranquicia));

        webTestClient = WebTestClient.bindToWebHandler(
                (WebHandler) getRouterFunction(franquiciaController)
        ).build();

        webTestClient.post()
                .uri("/api/franquicias")
                .bodyValue(testSaveDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franquicia.class)
                .isEqualTo(testFranquicia);
    }

    private RouterFunction<ServerResponse> getRouterFunction(FranquiciaController franquiciaController) {
        return org.springframework.web.reactive.function.server.RouterFunctions.route(
                org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/franquicias"),
                franquiciaController::getAllFranquicias
        ).andRoute(
                org.springframework.web.reactive.function.server.RequestPredicates.GET("/api/franquicias/{id}"),
                franquiciaController::getFranquiciaById
        ).andRoute(
                org.springframework.web.reactive.function.server.RequestPredicates.POST("/api/franquicias"),
                franquiciaController::createFranquicia
        );
    }
}
