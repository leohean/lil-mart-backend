package leonardo.lil_mart.market.service;

import leonardo.lil_mart.auth.dto.RegisterMarketDTO;
import leonardo.lil_mart.market.model.Market;
import leonardo.lil_mart.market.repository.MarketRepository;
import leonardo.lil_mart.product.dto.ProductDTO;
import leonardo.lil_mart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MarketService {
    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private ProductService productService;

    public ResponseEntity registerMarket(RegisterMarketDTO registerMarketDTO) {
        if(marketRepository.findByEmail(registerMarketDTO.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerMarketDTO.password());
        Market newMarket = new Market(
                                    registerMarketDTO.name(),
                                    registerMarketDTO.email(),
                                    encryptedPassword,
                                    registerMarketDTO.cnpj(),
                                    registerMarketDTO.role(),
                                    LocalDateTime.now(),
                                    LocalDateTime.now()
                                    );

        marketRepository.save(newMarket);

        return ResponseEntity.ok().build();
    }

    public Market getMarketById(Integer id) {
        return marketRepository.findById(id).orElse(null);
    }

    public Page<ProductDTO> getProductsByMarket(Integer id, Pageable pageable) {
        Market market = getMarketById(id);
        return productService.findAllProductsByMarket(market, pageable);
    }
}
