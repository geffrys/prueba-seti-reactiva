package co.com.bancolombia.api.dto;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@RequiredArgsConstructor
@Setter
@Getter
public class SaveProductoDTO {
    private String nombre;
    private Long stock;
    private Long sucursalId;
}
