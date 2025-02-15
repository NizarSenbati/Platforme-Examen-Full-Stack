package com.application.exam.Service;

import com.application.exam.Model.Etudiant;
import com.application.exam.Model.Professeur;
import com.application.exam.Model.User;
import com.application.exam.Repository.EtudiantRepository;
import com.application.exam.Repository.ProfesseurRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {


    private static final String SECRET_KEY = "7e61a543fa3847977574d1e7c43bb2c16a4df070925978d95a18560b421da867";
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private ProfesseurRepository professeurRepository;

    // Generate signing key
    private SecretKey getSiginingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate token with claims
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extractClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSiginingKey(), Jwts.SIG.HS256)
                .compact();
    }

    // Generate token without extra claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract a specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSiginingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public User getUserFromToken(String token) {
        Claims claims = extractAllClaims(token);
        String email = claims.getSubject();
        Optional<Etudiant> etudiantOp = etudiantRepository.findByEmail(email);
        Optional<Professeur> professeurOptional = Optional.ofNullable(professeurRepository.findByEmail(email));
        if (etudiantOp.isEmpty() && professeurOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        if (etudiantOp.isPresent()) {
            return etudiantOp.get();
        } else {
            return professeurOptional.get();
        }
    }
}
