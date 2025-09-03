package leonardo.lil_mart.shoppingCart.service;

import leonardo.lil_mart.exception.ResourceNotFoundException;
import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.repository.ProductRepository;
import leonardo.lil_mart.shoppingCart.dto.ShoppingCartItemDTO;
import leonardo.lil_mart.shoppingCart.model.ShoppingCart;
import leonardo.lil_mart.shoppingCart.repository.ShoppingCartRepository;
import leonardo.lil_mart.user.model.User;
import leonardo.lil_mart.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public ShoppingCart createShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO) {
        Optional<User> foundUser = userRepository.findById(shoppingCartItemDTO.idUser());
        Optional<Product> foundProduct = productRepository.findById(shoppingCartItemDTO.idProduct());

        if (foundUser.isPresent() && foundProduct.isPresent()) {
            User existingUser = foundUser.get();
            Product existingProduct = foundProduct.get();

            ShoppingCart newShoppingCart = new ShoppingCart();

            newShoppingCart.setUser(existingUser);
            newShoppingCart.setProduct(existingProduct);
            newShoppingCart.setProductQuantity(shoppingCartItemDTO.productQuantity());
            newShoppingCart.setCreatedAt(LocalDateTime.now());
            newShoppingCart.setLastUpdateAt(LocalDateTime.now());
            return shoppingCartRepository.save(newShoppingCart);
        }

        return null;
    }

    public Page<Product> getShoppingCartItems(Integer idUser, Pageable page) {
        Optional<User> foundUser = userRepository.findById(idUser);

        if (foundUser.isPresent()) {
            User existingUser = foundUser.get();
            return shoppingCartRepository.findProductsByUser(existingUser, page);
        }

        return null;
    }

    public void deleteShoppingCartItem(Integer id) {
        if (!shoppingCartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item does not exist - id: " + id);
        }
        shoppingCartRepository.deleteById(id);
    }
}
