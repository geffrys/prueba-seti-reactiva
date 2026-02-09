package co.com.bancolombia.api.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SaveSucursalDTO {
    private final String nombre;
    private final Long franquiciaId;
}
