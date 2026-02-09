package co.com.bancolombia.r2dbc.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("franchise")
public class Franchise {

    @Id
    private Long id;
    private String name;
}
