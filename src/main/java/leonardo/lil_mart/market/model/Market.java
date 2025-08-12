package leonardo.lil_mart.market.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="markets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Market implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String cnpj;
    @Enumerated(EnumType.STRING)
    private MarketRole role;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "last_update_at")
    private LocalDateTime lastUpdateAt;

    public Market(String name, String email, String password, String cnpj, MarketRole role, LocalDateTime createdAt, LocalDateTime lastUpdateAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cnpj = cnpj;
        this.role = role;
        this.createdAt = createdAt;
        this.lastUpdateAt = lastUpdateAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == MarketRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_MARKET_ADMIN"), new SimpleGrantedAuthority("ROLE_MARKET"));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_MARKET"));
        }
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {return true;/*UserDetails.super.isAccountNonExpired();*/}

    @Override
    public boolean isAccountNonLocked() {return true;/*UserDetails.super.isAccountNonLocked();*/}

    @Override
    public boolean isCredentialsNonExpired() {return true;/*UserDetails.super.isCredentialsNonExpired();*/}

    @Override
    public boolean isEnabled() {return true;/*UserDetails.super.isEnabled();*/}
}
