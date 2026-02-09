package co.com.bancolombia.api.dto;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@RequiredArgsConstructor
@Getter
public class SaveProductoDTO {
    private String nombre;
    private Integer stock;
    private Long sucursalId;
}
