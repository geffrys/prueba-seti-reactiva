package co.com.bancolombia.api.config;

import co.com.bancolombia.api.RouterRest;
import co.com.bancolombia.api.controller.FranquiciaController;
import co.com.bancolombia.api.controller.HealthController;
import co.com.bancolombia.api.controller.ProductoController;
import co.com.bancolombia.api.controller.SucursalController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {HealthController.class})
@ContextConfiguration(classes = RouterRest.class)
@Import({RouterRest.class, CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private HealthController healthController;

    @MockitoBean
    private FranquiciaController franquiciaController;

    @MockitoBean
    private ProductoController productoController;

    @MockitoBean
    private SucursalController sucursalController;

    @BeforeEach
    void setUp() {
        when(healthController.health(any()))
                .thenReturn(ServerResponse.ok().build());
    }

    @Test
    void corsConfigurationShouldAllowOrigins() {
        webTestClient.get()
            .uri("/api/health")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }

}