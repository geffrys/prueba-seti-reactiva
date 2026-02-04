package co.com.bancolombia.model.producto.gateways;
import co.com.bancolombia.model.producto.Producto;
import java.util.Optional;

public interface ProductoRepository {
    Optional<Producto> findById(String id);
    Optional<Producto> save(Producto producto);
    Boolean deleteById(String id);
    Boolean existsById(String id);
}
