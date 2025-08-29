package leonardo.lil_mart.product.service;

import leonardo.lil_mart.market.model.Market;
import leonardo.lil_mart.product.dto.ProductDTO;
import leonardo.lil_mart.product.dto.ProductDTOMapper;
import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.model.ProductBuilder;
import leonardo.lil_mart.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDTOMapper productDTOMapper;

    public ResponseEntity createProduct(ProductDTO productDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Market market = (Market)authentication.getPrincipal();

        Product newProduct = ProductBuilder.builder()
                .name(productDTO.name())
                .description(productDTO.description())
                .category(productDTO.category())
                .price(productDTO.price())
                .unitMeasurement(productDTO.unitMeasurement())
                .stockQuantity(productDTO.stockQuantity())
                .isActive(true)
                .market(market)
                .createdAt(LocalDateTime.now())
                .lastUpdateAt(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(productRepository.save(newProduct));
    }

    public ResponseEntity createImageProduct(Integer id, MultipartFile file) {
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

    public List<ProductDTO> getProductsByName(String name){
        List<Product> products = productRepository.searchByName(name);

        return products.stream().map(productDTOMapper).collect(Collectors.toList());
    }

    public Page<ProductDTO> findAllProductsByMarket(Market market, Pageable pageable){
        return productRepository.findAllByMarketAndIsActiveTrue(market, pageable).map(productDTOMapper);
    }

    public ResponseEntity<Resource> getImageProduct(Integer id) {
        Product product = this.productRepository.getById(id);

        byte[] image = product.getImage();
        ByteArrayResource resource = new ByteArrayResource(image);
        return ResponseEntity.ok()
                .contentLength(image.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    public Product updateProduct(Integer id, ProductDTO productDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Market market = (Market)authentication.getPrincipal();

        Optional<Product> foundProduct = productRepository.findById(id);
        if(foundProduct.isPresent()) {
            Product existingProduct = foundProduct.get();

            existingProduct.setName(productDTO.name());
            existingProduct.setDescription(productDTO.description());
            existingProduct.setCategory(productDTO.category());
            existingProduct.setUnitMeasurement(productDTO.unitMeasurement());
            existingProduct.setStockQuantity(productDTO.stockQuantity());
            existingProduct.setMarket(market);
            existingProduct.setCreatedAt(LocalDateTime.now());
            existingProduct.setLastUpdateAt(LocalDateTime.now());

            return productRepository.save(existingProduct);
        }

        return null;
    }

    public Product inactivateProduct(Integer id){
        Optional<Product> foundProduct = productRepository.findById(id);

        if(foundProduct.isPresent()) {
            Product existingProduct = foundProduct.get();
            existingProduct.setIsActive(false);
            return productRepository.save(existingProduct);
        }

        return null;
    }
}
