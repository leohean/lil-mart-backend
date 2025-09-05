package leonardo.lil_mart.product.service;

import leonardo.lil_mart.market.model.Market;
import leonardo.lil_mart.product.dto.ProductDTO;
import leonardo.lil_mart.product.dto.ProductDTOMapper;
import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.model.ProductBuilder;
import leonardo.lil_mart.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
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

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;

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

    public void uploadImage(Integer id, MultipartFile file) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(id.toString())
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes()));

        String imageUrl = "https://lil-mart-products-image.s3.amazonaws.com/" + id.toString();

        Optional<Product> foundProduct = productRepository.findById(id);
        if(foundProduct.isPresent()) {
            Product existingProduct = foundProduct.get();
            existingProduct.setImage(imageUrl);
            productRepository.save(existingProduct);
        }
    }

    public List<ProductDTO> getProductsByName(String name){
        List<Product> products = productRepository.searchByName(name);

        return products.stream().map(productDTOMapper).collect(Collectors.toList());
    }

    public Page<ProductDTO> findAllProductsByMarket(Market market, Pageable pageable){
        return productRepository.findAllByMarketAndIsActiveTrue(market, pageable).map(productDTOMapper);
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
