package co.com.bancolombia.usecase.producto;

import co.com.bancolombia.model.producto.Producto;
import co.com.bancolombia.model.producto.gateways.ProductoRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SaveProductoUseCase {
	private final ProductoRepository repository;

	public Mono<Producto> execute(Producto producto) {
		// Lógica del caso de uso utilizando el repositorio
		System.out.println("Saving Producto: " + producto.getNombre() + " with ID: " + producto.getId());
		return repository.save(producto);
	}
}
