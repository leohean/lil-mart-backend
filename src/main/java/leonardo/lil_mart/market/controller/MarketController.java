package leonardo.lil_mart.market.controller;

import io.swagger.v3.oas.annotations.Operation;
import leonardo.lil_mart.market.service.MarketService;
import leonardo.lil_mart.product.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("market")
public class MarketController {
    @Autowired
    private MarketService marketService;

    @Operation(description = "Pega todos os produtos ativos de um mercado.")
    @GetMapping("{id}/products")
    public ResponseEntity<Page<ProductDTO>>getProductsByMarket(@PathVariable Integer id, Pageable page){
        return ResponseEntity.ok().body(marketService.getProductsByMarket(id, page));
    }
}
