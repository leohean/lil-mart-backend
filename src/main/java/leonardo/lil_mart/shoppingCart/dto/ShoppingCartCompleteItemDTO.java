package leonardo.lil_mart.shoppingCart.dto;

public record ShoppingCartCompleteItemDTO(
        Integer idShoppingCartItem,
        String name,
        String category,
        String description,
        Double price,
        String unitMeasurement,
        String image,
        Double quantityShoppingCartItem
) {
}
