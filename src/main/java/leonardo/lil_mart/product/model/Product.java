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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String category;
    private String description;
    private Double price;
    private String unitMeasurement;
    private Double stockQuantity;

    @Column(name = "is_active")
    private Boolean isActive;

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

    protected Product() {}

}
