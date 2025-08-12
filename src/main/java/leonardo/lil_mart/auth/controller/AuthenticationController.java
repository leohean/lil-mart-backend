package leonardo.lil_mart.auth.controller;

import jakarta.validation.Valid;
import leonardo.lil_mart.auth.dto.LoginResponseDTO;
import leonardo.lil_mart.auth.dto.RegisterMarketDTO;
import leonardo.lil_mart.infra.service.TokenService;
import leonardo.lil_mart.auth.dto.AuthenticationDTO;
import leonardo.lil_mart.auth.dto.RegisterDTO;
import leonardo.lil_mart.market.service.MarketService;
import leonardo.lil_mart.user.model.User;
import leonardo.lil_mart.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        return ResponseEntity.ok().body(userService.register(registerDTO));
    }

    @PostMapping("/registermarket")
    public ResponseEntity registerMarket(@RequestBody @Valid RegisterMarketDTO registerMarketDTO){
        return ResponseEntity.ok().body(marketService.registerMarket(registerMarketDTO));
    }

}
