package co.com.bancolombia.api;

import co.com.bancolombia.api.controller.FranquiciaController;
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

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler, HealthController healthController, FranquiciaController franquiciaController) {
        return route(GET("/api/usecase/path"), handler::listenGETUseCase)
        .andRoute(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
        .andRoute(GET("/api/health"), healthController::health)
        
        
        // FRANQUICIA ROUTES
        .andRoute(GET("/api/franquicias"), franquiciaController::getAllFranquicias) 
        .andRoute(GET("/api/franquicias/{id}"), franquiciaController::getFranquiciaById)
        .andRoute(POST("/api/franquicias"), franquiciaController::createFranquicia)


        .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));

    }
}
