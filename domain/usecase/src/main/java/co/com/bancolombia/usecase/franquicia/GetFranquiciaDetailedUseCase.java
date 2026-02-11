package co.com.bancolombia.usecase.franquicia;

import co.com.bancolombia.model.franquicia.Franquicia;
import co.com.bancolombia.model.franquicia.FranquiciaDetail;
import co.com.bancolombia.model.franquicia.gateways.FranquiciaRepository;
import co.com.bancolombia.model.sucursal.Sucursal;
import co.com.bancolombia.model.sucursal.gateways.SucursalRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetFranquiciaDetailedUseCase {
    private final FranquiciaRepository franquiciaRepository;
    private final SucursalRepository sucursalRepository;

    public Mono<FranquiciaDetail> execute(Long id) {
        Mono<Franquicia> franquiciaMono = franquiciaRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia not found with id: " + id)));
        Flux<Sucursal> sucursalesFlux = sucursalRepository.findByFranquiciaId(id)
                .switchIfEmpty(Flux.error(new RuntimeException("No sucursales found for franquicia id: " + id)));
        return franquiciaMono
                .zipWith(sucursalesFlux.collectList(),
                        (franquicia, sucursales) -> {
                            FranquiciaDetail detail = new FranquiciaDetail();
                            detail.setFranquicia(franquicia);
                            detail.setSucursales(sucursales);
                            return detail;
                        });

    }
}
