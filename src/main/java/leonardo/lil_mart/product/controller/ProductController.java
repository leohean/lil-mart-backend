package leonardo.lil_mart.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import leonardo.lil_mart.product.dto.ProductDTO;
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

    @Operation(description = "Cria um novo produto.")
    @PostMapping()
    public ResponseEntity createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok().body(productService.createProduct(productDTO));
    }

    @Operation(description = "Faz o upload da imagem de um novo produto criado.")
    @PostMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createImageProduct(@PathVariable Integer id, @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(productService.createImageProduct(id, image));
    }

    @Operation(description = "Pesquisa todos os produtos ativos que começam pela string informada.")
    @GetMapping("{name}")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@PathVariable String name) {
        return ResponseEntity.ok().body(productService.getProductsByName(name));
    }

    @Operation(description = "Pega a image de um produto.")
    @GetMapping("/{id}/image")
    public ResponseEntity getImageProduct(@PathVariable Integer id){
        return productService.getImageProduct(id);
    }

    @Operation(description = "Atualiza um produto exitesnte.")
    @PutMapping("{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok().body(productService.updateProduct(id, productDTO));
    }

    @Operation(description = "Inativa um produto.")
    @PostMapping("{id}/inactivateproduct")
    public ResponseEntity inactivateProduct(@PathVariable Integer id) {
        return ResponseEntity.ok().body(productService.inactivateProduct(id));
    }

}
