package co.com.bancolombia.api.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class SaveSucursalDTO {
    private String nombre;
    private Long franquiciaId;
}
