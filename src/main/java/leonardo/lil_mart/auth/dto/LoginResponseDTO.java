package leonardo.lil_mart.auth.dto;

import leonardo.lil_mart.market.model.MarketRole;
import leonardo.lil_mart.user.model.UserRole;

public record LoginResponseDTO(Integer id, String role) {
}
