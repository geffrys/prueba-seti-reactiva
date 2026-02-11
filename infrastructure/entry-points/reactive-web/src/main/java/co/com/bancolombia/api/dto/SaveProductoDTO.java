package co.com.bancolombia.api.dto;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@RequiredArgsConstructor
@Getter
@Setter
public class SaveProductoDTO {
    private String nombre;
    private Integer stock;
    private Long sucursalId;
}
