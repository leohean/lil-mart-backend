package leonardo.lil_mart.infra.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import leonardo.lil_mart.market.model.Market;
import leonardo.lil_mart.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Integer id = null;
            String name = null;
            if(userDetails instanceof User) {
                id = ((User) userDetails).getId();
                name = ((User) userDetails).getName();
            }else if(userDetails instanceof Market){
                id = ((Market) userDetails).getId();
                name = ((Market) userDetails).getName();
            }



            String role =  userDetails.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");


            String token = JWT.create()
                    .withIssuer("lil-mart")
                    .withSubject(userDetails.getUsername())
                    .withClaim("id", id)
                    .withClaim("name", name)
                    .withClaim("role", role)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

            return token;
        }catch(JWTCreationException exception){
            throw new RuntimeException("Error while generating token", exception);
        }
    }


    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("lil-mart")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTCreationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3"));
    }
}
