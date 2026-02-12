package co.com.bancolombia.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.model.franquicia.gateways.FranquiciaRepository;
import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.model.sucursal.gateways.SucursalRepository;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @Bean
        public MyUseCase myUseCase() {
            return new MyUseCase();
        }

        @Bean
        public FranquiciaRepository franquiciaRepository() {
            return new FranquiciaRepository() {
                @Override
                public Mono<Franquicia> findById(Long id) {
                    return Mono.empty();
                }

                @Override
                public Flux<Franquicia> findAll() {
                    return Flux.empty();
                }

                @Override
                public Mono<Franquicia> save(Franquicia franquicia) {
                    return Mono.justOrEmpty(franquicia);
                }
            };
        }

        @Bean
        public SucursalRepository sucursalRepository() {
            return new SucursalRepository() {
                @Override
                public Mono<Sucursal> findById(Long id) {
                    return Mono.empty();
                }

                @Override
                public Flux<Sucursal> findAll() {
                    return Flux.empty();
                }

                @Override
                public Mono<Sucursal> save(Sucursal sucursal) {
                    return Mono.justOrEmpty(sucursal);
                }

                @Override
                public Flux<Sucursal> findByFranquiciaId(Long franquiciaId) {
                    return Flux.empty();
                }
            };
        }

        @Bean
        public ProductoRepository productoRepository() {
            return new ProductoRepository() {
                @Override
                public Flux<Producto> findAll() {
                    return Flux.empty();
                }

                @Override
                public Mono<Producto> save(Producto producto) {
                    return Mono.justOrEmpty(producto);
                }

                @Override
                public Mono<Producto> findById(Long id) {
                    return Mono.empty();
                }

                @Override
                public Flux<Producto> findBySucursalId(Long sucursalId) {
                    return Flux.empty();
                }

                @Override
                public Mono<Void> deleteById(Long id) {
                    return Mono.empty();
                }
            };
        }
    }

    static class MyUseCase {
        public String execute() {
            return "MyUseCase Test";
        }
    }
}