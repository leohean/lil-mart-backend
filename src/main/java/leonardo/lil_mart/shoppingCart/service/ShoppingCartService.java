package leonardo.lil_mart.shoppingCart.service;

import leonardo.lil_mart.exception.ResourceNotFoundException;
import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.repository.ProductRepository;
import leonardo.lil_mart.shoppingCart.dto.ShoppingCartCompleteItemDTO;
import leonardo.lil_mart.shoppingCart.dto.ShoppingCartItemDTO;
import leonardo.lil_mart.shoppingCart.model.ShoppingCart;
import leonardo.lil_mart.shoppingCart.model.ShoppingCartBuilder;
import leonardo.lil_mart.shoppingCart.repository.ShoppingCartRepository;
import leonardo.lil_mart.user.model.User;
import leonardo.lil_mart.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

            Double stockQuantity = existingProduct.getStockQuantity();
            stockQuantity = stockQuantity - shoppingCartItemDTO.productQuantity();

            existingProduct.setStockQuantity(stockQuantity);
            productRepository.save(existingProduct);

            ShoppingCart newShoppingCart = ShoppingCartBuilder.builder()
                    .user(existingUser)
                    .product(existingProduct)
                    .productQuantity(shoppingCartItemDTO.productQuantity())
                    .createdAt(LocalDateTime.now())
                    .lastUpdateAt(LocalDateTime.now())
                    .build();

            return shoppingCartRepository.save(newShoppingCart);
        }

        return null;
    }

    public Page<ShoppingCartCompleteItemDTO> getAllShoppingCartItemsByUser(Integer idUser, Pageable page) {
        Optional<User> foundUser = userRepository.findById(idUser);

        if (foundUser.isPresent()) {
            User existingUser = foundUser.get();
            return shoppingCartRepository.findProductsByUser(existingUser, page);
        }
        return null;
    }

    public ShoppingCart updateShoppingCartItem(Integer id, ShoppingCartItemDTO shoppingCartItemDTO){
        Optional<ShoppingCart> foundShoppingCartItem = shoppingCartRepository.findById(id);

        if (foundShoppingCartItem.isPresent()) {
            ShoppingCart exitingShoppingCartItem = foundShoppingCartItem.get();
            Product existingProduct = exitingShoppingCartItem.getProduct();

            Double shoppingCartItemQuantity = exitingShoppingCartItem.getProductQuantity();
            Double productStockQuantity = existingProduct.getStockQuantity();

            Double newStockQuantity = (productStockQuantity + shoppingCartItemQuantity) - shoppingCartItemDTO.productQuantity();
            existingProduct.setStockQuantity(newStockQuantity);
            productRepository.save(existingProduct);

            exitingShoppingCartItem.setProductQuantity(shoppingCartItemDTO.productQuantity());
            return shoppingCartRepository.save(exitingShoppingCartItem);
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
