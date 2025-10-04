package leonardo.lil_mart.shoppingCart.dto;

public record ShoppingCartItemDTO(
        Integer idUser,
        Integer idProduct,
        Double productQuantity
) {
}
