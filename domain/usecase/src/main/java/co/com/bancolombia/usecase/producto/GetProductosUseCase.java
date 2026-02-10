package co.com.bancolombia.usecase.producto;

import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetProductosUseCase {
	private final ProductoRepository repository;

	public Flux<Producto> execute() {
		// Lógica del caso de uso utilizando el repositorio
		return repository.findAll();
	}
}
