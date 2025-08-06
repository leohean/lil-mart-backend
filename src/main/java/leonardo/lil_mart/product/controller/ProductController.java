package leonardo.lil_mart.product.controller;

import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.ok().body("Product was created sucessfully");
    }

    @GetMapping("{name}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name) {
        return ResponseEntity.ok().body(productService.getProductsByName(name));
    }
}
