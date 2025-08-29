package leonardo.lil_mart.product.dto;

public record ProductDTO(
        Integer id,
        String name,
        String category,
        String description,
        Double price,
        String unitMeasurement,
        Double stockQuantity
) {
}
