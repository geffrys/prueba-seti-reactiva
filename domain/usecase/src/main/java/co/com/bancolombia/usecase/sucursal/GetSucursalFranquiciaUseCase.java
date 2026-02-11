package co.com.bancolombia.usecase.sucursal;

import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.model.sucursal.gateways.SucursalRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetSucursalFranquiciaUseCase {

    private final SucursalRepository repository;

    public Flux<Sucursal> execute(Long franquiciaId) {
        return repository.findByFranquiciaId(franquiciaId);
    }
    
}
