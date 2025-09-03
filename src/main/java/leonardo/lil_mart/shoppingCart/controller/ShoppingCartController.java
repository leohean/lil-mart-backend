package leonardo.lil_mart.shoppingCart.controller;

import io.swagger.v3.oas.annotations.Operation;
import leonardo.lil_mart.shoppingCart.dto.ShoppingCartItemDTO;
import leonardo.lil_mart.shoppingCart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(description = "Cria um novo item no carrinho do usuário.")
    @PostMapping()
    public ResponseEntity createShoppingCartItem(@RequestBody ShoppingCartItemDTO shoppingCartItemDTO) {
        return ResponseEntity.ok().body(shoppingCartService.createShoppingCartItem(shoppingCartItemDTO));
    }

    @Operation(description = "Deleta um item do carrinho do usuário.")
    @DeleteMapping("{id}")
    public ResponseEntity deleteShoppingCartItem(@PathVariable("id") Integer id) {
        shoppingCartService.deleteShoppingCartItem(id);
        return ResponseEntity.ok().build();
    }

}
