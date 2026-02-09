package co.com.bancolombia.usecase.producto;

import lombok.RequiredArgsConstructor;
import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetProductoUseCase {
	private final ProductoRepository repository;

	public Mono<Producto> execute(Long id) {
		// Lógica del caso de uso utilizando el repositorio
		return repository.findById(id);
	}
}
