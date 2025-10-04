package leonardo.lil_mart.infra.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors()
                .and()
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                            /* Requisições */
                            .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                            .requestMatchers(HttpMethod.GET,"/auth/getlogin").permitAll()
                            .requestMatchers(HttpMethod.POST,"/auth/register").permitAll()
                            .requestMatchers(HttpMethod.POST,"/auth/registermarket").permitAll()
                            .requestMatchers(HttpMethod.POST,"/auth/logout").permitAll()

                            .requestMatchers(HttpMethod.GET,"/user/{id}/shoppingcart").permitAll()

                            .requestMatchers(HttpMethod.POST,"/shoppingcart").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE,"/shoppingcart/*").hasRole("MARKET")

                            .requestMatchers(HttpMethod.GET,"market/{id}/products").hasRole("MARKET")

                            .requestMatchers(HttpMethod.POST,"/product").hasRole("MARKET")
                            .requestMatchers(HttpMethod.POST,"/product/*/image").hasRole("MARKET")

                            .requestMatchers(HttpMethod.POST,"/product/{id}/inactivateproduct").hasRole("MARKET")
                            .requestMatchers(HttpMethod.GET,"/product/{name}").permitAll()
                            .requestMatchers(HttpMethod.GET,"/product/{id}/image").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/product/{id}").hasRole("MARKET")

                        .requestMatchers("/markethome").hasRole("MARKET")
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers( "/swagger-ui/**", "/v3/api-docs/**" );
    }
}
