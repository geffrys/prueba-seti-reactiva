package co.com.bancolombia.usecase.sucursal;

import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.model.sucursal.gateways.SucursalRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetSucursalesUseCase {
    private final SucursalRepository repository;

    public Flux<Sucursal> execute() {
        // Lógica del caso de uso utilizando el repositorio
        return repository.findAll();
    }
}
