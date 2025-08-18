package leonardo.lil_mart.product.controller;

import leonardo.lil_mart.product.dto.ProductDTO;
import leonardo.lil_mart.product.model.Product;
import leonardo.lil_mart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok().body(productService.createProduct(productDTO));
    }

    @PostMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(productService.uploadImage(id, image));
    }

    @GetMapping("/image/{id}")
    public byte[] getProductImage(@PathVariable Integer id){
        return productService.getProductImage(id);
    }

    @GetMapping("{name}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name) {
        return ResponseEntity.ok().body(productService.getProductsByName(name));
    }
}
