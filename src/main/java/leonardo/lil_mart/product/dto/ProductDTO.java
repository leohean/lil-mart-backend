package leonardo.lil_mart.product.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import leonardo.lil_mart.market.model.Market;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record ProductDTO(
        String name,
        String category,
        String description,
        String unitMeasurement,
        Double stockQuantity
) {
}
