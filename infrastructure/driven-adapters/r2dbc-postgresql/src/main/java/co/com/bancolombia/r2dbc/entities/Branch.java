package co.com.bancolombia.r2dbc.entities;

    

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import lombok.Getter;
import lombok.Setter;


@Table(name = "branch")
@Getter
@Setter
public class Branch {
    @Id
    private Long id;
    private String name;
    @Column("franchise_id")
    private Long franchiseId;
}
