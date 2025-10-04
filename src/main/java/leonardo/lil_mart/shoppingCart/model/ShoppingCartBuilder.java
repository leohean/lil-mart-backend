package leonardo.lil_mart.shoppingCart.model;

import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.user.model.User;

import java.time.LocalDateTime;

public class ShoppingCartBuilder {
    private ShoppingCart shoppingCart;

    private ShoppingCartBuilder() { shoppingCart = new ShoppingCart(); }

    public static ShoppingCartBuilder builder() { return new ShoppingCartBuilder(); }

    public ShoppingCartBuilder user(User user) {
        this.shoppingCart.setUser(user);
        return this;
    }

    public ShoppingCartBuilder product(Product product) {
        this.shoppingCart.setProduct(product);
        return this;
    }

    public ShoppingCartBuilder productQuantity(Double productQuantity) {
        this.shoppingCart.setProductQuantity(productQuantity);
        return this;
    }

    public ShoppingCartBuilder createdAt(LocalDateTime createdAt) {
        this.shoppingCart.setCreatedAt(createdAt);
        return this;
    }

    public ShoppingCartBuilder lastUpdateAt(LocalDateTime lastUpdateAt) {
        this.shoppingCart.setLastUpdateAt(lastUpdateAt);
        return this;
    }

    public ShoppingCart build() {
        return this.shoppingCart;
    }
}
