package co.com.bancolombia.model.franquicia.gateways;
import co.com.bancolombia.model.franquicia.Franquicia;

import java.util.Optional;

public interface FranquiciaRepository {
    Optional<Franquicia> findById(String id);
    Optional<Franquicia> save(Franquicia franquicia);
    Boolean deleteById(String id);
    Boolean existsById(String id);
}
