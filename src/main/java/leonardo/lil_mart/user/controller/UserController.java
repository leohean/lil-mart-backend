package leonardo.lil_mart.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import leonardo.lil_mart.shoppingCart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Operation(description = "Pega todos os itens do carrinho de um usuário")
    @GetMapping("{id}/shoppingcart")
    public ResponseEntity getShoppingCartItems(@PathVariable("id") Integer idUser, Pageable page) {
        return ResponseEntity.ok().body(shoppingCartService.getShoppingCartItems(idUser, page));
    }
}
