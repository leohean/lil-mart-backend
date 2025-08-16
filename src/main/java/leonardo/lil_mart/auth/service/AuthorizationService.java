package leonardo.lil_mart.auth.service;

import leonardo.lil_mart.market.repository.MarketRepository;
import leonardo.lil_mart.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MarketRepository marketRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username);
        if (user != null) { return user;}

        UserDetails market = marketRepository.findByEmail(username);
        if (market != null) { return market;}

        throw new UsernameNotFoundException(username);
    }
}
