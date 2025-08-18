package leonardo.lil_mart.product.service;

import leonardo.lil_mart.market.model.Market;
import leonardo.lil_mart.product.dto.ProductDTO;
import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity createProduct(ProductDTO productDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Market market = (Market)authentication.getPrincipal();

        Product newProduct = new Product(
                productDTO.name(),
                productDTO.category(),
                productDTO.description(),
                productDTO.unitMeasurement(),
                productDTO.stockQuantity(),
                market,
                LocalDateTime.now(),
                LocalDateTime.now()
                );

        return ResponseEntity.ok(productRepository.save(newProduct));
    }

    public ResponseEntity uploadImage(Integer id, MultipartFile file) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        try {
            product.setImage(file.getBytes());
            product.setLastUpdateAt(LocalDateTime.now());
            productRepository.save(product);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error");
        }

        return ResponseEntity.ok().build();
    }

    public byte[] getProductImage(Integer id) {
        Product product = this.productRepository.getById(id);
        return product.getImage();
    }

    public List<Product> getProductsByName(String name){
        return productRepository.searchByName(name);
    }
}
