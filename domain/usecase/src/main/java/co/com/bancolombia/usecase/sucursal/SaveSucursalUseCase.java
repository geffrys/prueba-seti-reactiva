package co.com.bancolombia.usecase.sucursal;

import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.model.sucursal.gateways.SucursalRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SaveSucursalUseCase {
    private final SucursalRepository repository;

    public Mono<Sucursal> execute(Sucursal sucursal) {
        // Lógica del caso de uso utilizando el repositorio
        System.out.println("Saving Sucursal: " + sucursal.getNombre() + " with ID: " + sucursal.getId());
        return repository.save(sucursal);
    }
}
