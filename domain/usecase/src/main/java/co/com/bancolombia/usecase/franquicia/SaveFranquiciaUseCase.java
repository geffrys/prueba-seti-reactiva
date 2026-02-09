package co.com.bancolombia.usecase.franquicia;

import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.model.franquicia.gateways.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SaveFranquiciaUseCase {
    private final FranquiciaRepository repository;

        public Mono<Franquicia> execute(Franquicia franquicia) {
            // Lógica del caso de uso utilizando el repositorio
            System.out.println("Saving Franquicia: " + franquicia.getNombre() + " with ID: " + franquicia.getId());
            return repository.save(franquicia);
        }
}
