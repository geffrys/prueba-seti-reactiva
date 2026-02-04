package co.com.bancolombia.model.sucursal.gateways;
import co.com.bancolombia.model.sucursal.Sucursal;
import java.util.Optional;

public interface SucursalRepository {
    Optional<Sucursal> findById(Long id);
    Optional<Sucursal> save(Sucursal sucursal);
    Boolean deleteById(Long id);
    Boolean existsById(Long id);
}
