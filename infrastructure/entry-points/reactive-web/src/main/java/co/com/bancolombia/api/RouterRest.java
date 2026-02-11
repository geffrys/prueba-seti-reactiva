package co.com.bancolombia.api;

import co.com.bancolombia.api.controller.FranquiciaController;
import co.com.bancolombia.api.controller.HealthController;
import co.com.bancolombia.api.controller.ProductoController;
import co.com.bancolombia.api.controller.SucursalController;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(HealthController healthController, FranquiciaController franquiciaController, ProductoController productoController, SucursalController sucursalController) {
        return route(GET("/api/health"), healthController::health)
        .andRoute(GET("/api/health"), healthController::health)
        
        
        // FRANQUICIA ROUTES
        .andRoute(GET("/api/franquicias"), franquiciaController::getAllFranquicias) 
        .andRoute(GET("/api/franquicias/{id}"), franquiciaController::getFranquiciaById)
        .andRoute(POST("/api/franquicias"), franquiciaController::createFranquicia)
        .andRoute(GET("/api/franquicias/detailed/{id}"), franquiciaController::getFranquiciaDetailedById)
        .andRoute(GET("/api/franquicias/{id}/max-stock-products"), productoController::getMaxStockProductsByFranquicia)


        // PRODUCTO ROUTES
        .andRoute(GET("/api/productos"), productoController::getAllProductos)
        .andRoute(GET("/api/productos/{id}"), productoController::getProductoById)
        .andRoute(POST("/api/productos"), productoController::createProducto)
        .andRoute(GET("/api/productos/sucursal/{sucursalId}"), productoController::getProductoBySucursalId)
        .andRoute( PUT("/api/productos/{id}/modify-stock"), productoController::modifyStock)
        .andRoute(DELETE("/api/productos/sucursal/{sucursalId}/producto/{productoId}"), productoController::deleteProductoBySucursalId)
        .andRoute(DELETE("/api/productos/{id}"), productoController::deleteProductoById)



        // SUCURSAL ROUTES
        .andRoute(GET("/api/sucursales"), sucursalController::getAllSucursales)
        .andRoute(GET("/api/sucursales/{id}"), sucursalController::getSucursalById)
        .andRoute(POST("/api/sucursales"), sucursalController::createSucursal)
        .andRoute(GET("/api/sucursales/franquicia/{franquiciaId}"), sucursalController::getSucursalByFranquiciaId); 


    }
}
