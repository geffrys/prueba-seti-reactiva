package co.com.bancolombia.model.sucursal.gateways;

import java.util.Optional;

public interface SucursalRepository {
    Optional<Sucursal> findById(String id);
    Optional<Sucursal> save(Sucursal sucursal);
    Boolean deleteById(String id);
    Boolean existsById(String id);
}
