package leonardo.lil_mart.product.service;

import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsByName(String name){
        return productRepository.searchByName(name);
    }
}
