package leonardo.lil_mart.product.model;

import jakarta.persistence.*;
import leonardo.lil_mart.market.model.Market;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String category;
    private String description;
    private String unitMeasurement;
    private Double stockQuantity;

    @Column(name = "image")
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "id_market")
    private Market market;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_update_at")
    private LocalDateTime lastUpdateAt;

    public Product(String name, String category, String description, String unitMeasurement, Double stockQuantity, Market market, LocalDateTime createdAt, LocalDateTime lastUpdateAt) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.unitMeasurement = unitMeasurement;
        this.stockQuantity = stockQuantity;
        this.market = market;
        this.createdAt = createdAt;
        this.lastUpdateAt = lastUpdateAt;
    }
}
