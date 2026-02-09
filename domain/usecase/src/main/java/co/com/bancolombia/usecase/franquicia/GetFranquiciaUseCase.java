package co.com.bancolombia.usecase.franquicia;


import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.model.franquicia.gateways.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetFranquiciaUseCase {
    private final FranquiciaRepository repository;

    public Mono<Franquicia> execute(Long id) {
        // Lógica del caso de uso utilizando el repositorio
        return repository.findById(id);
    }
}
