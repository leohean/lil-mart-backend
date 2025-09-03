package leonardo.lil_mart.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import leonardo.lil_mart.auth.dto.LoginResponseDTO;
import leonardo.lil_mart.auth.dto.RegisterMarketDTO;
import leonardo.lil_mart.infra.service.TokenService;
import leonardo.lil_mart.auth.dto.AuthenticationDTO;
import leonardo.lil_mart.auth.dto.RegisterDTO;
import leonardo.lil_mart.market.service.MarketService;
import leonardo.lil_mart.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(description = "Faz o login na aplicação.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserDetails)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

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

}
