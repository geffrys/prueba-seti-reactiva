package co.com.bancolombia.api;

import co.com.bancolombia.api.controller.HealthController;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    // private final HealthController healthController;
    // private final Handler handler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler, HealthController healthController) {
        return route(GET("/api/usecase/path"), handler::listenGETUseCase)
        .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
        .andRoute(GET("/api/health"), healthController::health)
        .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));
    }
}
