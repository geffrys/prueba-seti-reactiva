package co.com.bancolombia.r2dbc.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import lombok.Getter;
import lombok.Setter;

@Table(name = "product")
@Getter
@Setter 
public class Product {
    @Id
    private Long id;
    private String name;
    private Long stock;
    @Column("branch_id")
    private Long branchId;
}
