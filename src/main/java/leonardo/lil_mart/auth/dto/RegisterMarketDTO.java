package leonardo.lil_mart.auth.dto;

import leonardo.lil_mart.market.model.MarketRole;

public record RegisterMarketDTO(
        String name,
        String email,
        String password,
        String cnpj,
        MarketRole role
) {
}
