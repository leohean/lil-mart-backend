package leonardo.lil_mart.shoppingCart.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

public record ShoppingCartItemDTO(
        Integer idUser,
        Integer idProduct,
        Double productQuantity
) {
}
