package io.agileintelligence.ppmtool.security;

import io.agileintelligence.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.agileintelligence.ppmtool.security.SecurityConstant.EXPIRATION_TIME;
import static io.agileintelligence.ppmtool.security.SecurityConstant.SECRET;

@Component
public class JwtTokenProvider {

    // Generate the token

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        String userId = Long.toString(user.getMyUserId());
        Claims claims = Jwts.claims();
        claims.setId(Long.toString(user.getMyUserId()));
        claims.put("id", (Long.toString(user.getMyUserId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());
        return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    // Validate the token

    public boolean validateToken(String token) {
        try {
            // Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("signature is not correct");
        } catch (MalformedJwtException e) {
            System.out.println("Malformed is not correct");
        } catch (ExpiredJwtException e) {
            System.out.println("ExpiredJwtException is not correct");
        } catch (UnsupportedJwtException e) {
            System.out.println("unsupprot is not correct: " + e);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException is not correct" + e);
        }
        return false;
    }

    // Get user Id from token

    public String getUserIDFrom_token(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstant.SECRET).parseClaimsJws(token).getBody();
        String get_UserId = (String) claims.getId();
        return get_UserId;
    }
}