package leonardo.lil_mart.shoppingCart.model;

import jakarta.persistence.*;
import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "product_quantity")
    private Double productQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_update_at")
    private LocalDateTime lastUpdateAt;
}
