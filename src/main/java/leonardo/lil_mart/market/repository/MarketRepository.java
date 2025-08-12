package leonardo.lil_mart.market.repository;

import leonardo.lil_mart.market.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface MarketRepository extends JpaRepository<Market, Integer> {
    UserDetails findByEmail(String email);
}
