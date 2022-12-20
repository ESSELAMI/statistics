package dz.me.dashboard.filters;

import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import dz.me.dashboard.services.implement.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@Component
@Getter
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtRefreshSecret}")
  private String jwtRefreshSecret;

  @Value("${app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${app.jwtRefreshExpirationMs}")
  private int jwtRefreshExpirationMs;

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
    return JWT.create().withSubject(userPrincipal.getUsername())
        .withExpiresAt(new Date((new Date()).getTime() + jwtExpirationMs))
        .withClaim("roles",
            userPrincipal.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList()))
        .sign(algorithm);
    /*
     * return Jwts.builder()
     * .setSubject((userPrincipal.getUsername())).claim("role",
     * userPrincipal.getAuthorities())
     * .setIssuedAt(new Date())
     * .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
     * .signWith(SignatureAlgorithm.HS512, jwtSecret)
     * .compact();
     */
  }

  public String generateRefreshJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256(jwtRefreshSecret);

    return JWT.create().withSubject(userPrincipal.getUsername())
        .withExpiresAt(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
        .withClaim("roles",
            userPrincipal.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList()))
        .sign(algorithm);
    /*
     * return Jwts.builder()
     * .setSubject((userPrincipal.getUsername()))
     * .setIssuedAt(new Date())
     * .setExpiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
     * .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
     * .compact();
     */
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public String getEmailFromRefreshJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(token).getBody().get("email", String.class);
  }

  public String getIdJwtRefreshToken(String token) {
    return Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(token).getBody().getId();
  }

  public String getUserNameFromJwtRefreshToken(String token) {
    return Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtRefreshToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public DecodedJWT decodedJWT(HttpServletRequest request) {
    String authorizationToken = request.getHeader("Authorization");
    String jwt = authorizationToken.substring("Bearer ".length());
    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
    return jwtVerifier.verify(jwt);
  }

  public long ClaimAsLong(HttpServletRequest request, String claimTitle) {
    return decodedJWT(request).getClaim(claimTitle).asLong();
  }

  public String ClaimAsString(HttpServletRequest request, String claimTitle) {
    return decodedJWT(request).getClaim(claimTitle).asString();
  }

  public Authentication authentication(HttpServletRequest request, String claimTitle) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      return authentication;
    }
    return null;
  }

}
