package leonardo.lil_mart.shoppingCart.repository;

import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.shoppingCart.model.ShoppingCart;
import leonardo.lil_mart.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    @Query("SELECT sc.product FROM ShoppingCart sc WHERE sc.user = :user")
    Page<Product> findProductsByUser(@Param("user") User user, Pageable page);
}
