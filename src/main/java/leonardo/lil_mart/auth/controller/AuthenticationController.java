package leonardo.lil_mart.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import leonardo.lil_mart.auth.dto.LoginResponseDTO;
import leonardo.lil_mart.auth.dto.RegisterMarketDTO;
import leonardo.lil_mart.infra.security.service.TokenService;
import leonardo.lil_mart.auth.dto.AuthenticationDTO;
import leonardo.lil_mart.auth.dto.RegisterDTO;
import leonardo.lil_mart.market.model.Market;
import leonardo.lil_mart.market.service.MarketService;
import leonardo.lil_mart.user.model.User;
import leonardo.lil_mart.user.model.UserRole;
import leonardo.lil_mart.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private TokenService tokenService;

    @Operation(description = "Cria um novo user na aplicação.")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        return ResponseEntity.ok().body(userService.register(registerDTO));
    }

    @Operation(description = "Cria um novo market na aplicação.")
    @PostMapping("/registermarket")
    public ResponseEntity registerMarket(@RequestBody @Valid RegisterMarketDTO registerMarketDTO){
        return ResponseEntity.ok().body(marketService.registerMarket(registerMarketDTO));
    }

    @Operation(description = "Faz o login na aplicação.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data, HttpServletResponse response){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserDetails)auth.getPrincipal());

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        var user = (UserDetails) auth.getPrincipal();
        Integer id = null;
        if (user instanceof User) {
            id = ((User) user).getId();
        } else if (user instanceof Market) {
            id = ((Market) user).getId();
        }

        var role = user.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        return ResponseEntity.ok(new LoginResponseDTO(id, role));
    }

    @GetMapping("/getlogin")
    public ResponseEntity<LoginResponseDTO> getLogin(Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = (UserDetails) authentication.getPrincipal();

        Integer id = null;
        if (user instanceof User) {
            id = ((User) user).getId();
        } else if (user instanceof Market) {
            id = ((Market) user).getId();
        }

        var role = user.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        return ResponseEntity.ok(new LoginResponseDTO(id, role));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }
}
