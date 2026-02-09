package co.com.bancolombia.usecase.sucursal;

import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.model.sucursal.gateways.SucursalRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetSucursalUseCase {
    private final SucursalRepository repository;

    public Mono<Sucursal> execute(Long id) {
        // Lógica del caso de uso utilizando el repositorio
        return repository.findById(id);
    }
}
