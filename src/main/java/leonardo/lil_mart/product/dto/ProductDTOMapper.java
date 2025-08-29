package leonardo.lil_mart.product.dto;

import leonardo.lil_mart.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    public ProductDTO apply(Product product){
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getPrice(),
                product.getUnitMeasurement(),
                product.getStockQuantity()
        );
    }
}
