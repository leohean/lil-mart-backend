package leonardo.lil_mart.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String category;
    private String description;
    private String unitMeasurement;
    private Double stockQuantity;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "last_update_at")
    private LocalDateTime lastUpdateAt;
}
