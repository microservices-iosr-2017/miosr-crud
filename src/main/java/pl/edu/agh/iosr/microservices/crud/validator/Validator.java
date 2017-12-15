package pl.edu.agh.iosr.microservices.crud.validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class Validator {

    @Value("${validator.secret.key}")
    private String validatorSecret;

    private boolean checkToken(String user, String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(validatorSecret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(user).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateHttpRequest(HttpServletRequest request) {
        String user = request.getHeader("username");
        String token = request.getHeader("token");

        return user != null && token != null && checkToken(user, token);
    }
}
