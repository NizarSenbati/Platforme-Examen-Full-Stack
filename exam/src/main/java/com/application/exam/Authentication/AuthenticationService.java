package com.application.exam.Authentication;

import com.application.exam.Model.Etudiant;
import com.application.exam.Model.Professeur;
import com.application.exam.Model.User;
import com.application.exam.Repository.EtudiantRepository;
import com.application.exam.Repository.ProfesseurRepository;
import com.application.exam.Service.JwtService;
import com.application.exam.Configuration.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final EtudiantRepository etudiantRepository;
    private final ProfesseurRepository professeurRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    //not needed
    public AuthenticationResponse registerEtu(RegisterRequest registerRequest) {
        var etudiant = new Etudiant();
        etudiant.setEmail(registerRequest.getEmail());
        etudiant.setFirstName(registerRequest.getFirstName());
        etudiant.setCode(etudiantRepository.getLastCode() + 1);
        etudiant.setLastName(registerRequest.getLastName());
        etudiant.setRole(Role.ETUDIANT);
        etudiant.setPassword(encoder.encode(etudiant.getPassword()));
        etudiantRepository.save(etudiant);
        var jwt = jwtService.generateToken(etudiant);
        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse registerProf(RegisterRequest registerRequest) {
        var professeur = new Professeur();
        professeur.setEmail(registerRequest.getEmail());
        professeur.setFirstName(registerRequest.getFirstName());
        professeur.setLastName(registerRequest.getLastName());
        professeur.setCode(professeurRepository.getLastCode() + 1);
        professeur.setRole(Role.PROFESSEUR);
        professeur.setPassword(encoder.encode(professeur.getPassword()));
        professeurRepository.save(professeur);
        var jwt = jwtService.generateToken(professeur);
        return new AuthenticationResponse(jwt);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Optional<Etudiant> etudiantOp = etudiantRepository.findByEmail(request.getEmail());
        Optional<Professeur> professeurOptional = Optional.ofNullable(professeurRepository.findByEmail(request.getEmail()));
        if (etudiantOp.isEmpty() && professeurOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + request.getEmail());
        }

        if (etudiantOp.isPresent()) {
            Etudiant etudiant = etudiantOp.get();
            if (encoder.matches(request.getPassword(), etudiant.getPassword()))
                return new AuthenticationResponse(jwtService.generateToken(etudiant));
            else throw new RuntimeException("Password not correct");
        } else {
            Professeur professeur = professeurOptional.get();
            if (encoder.matches(request.getPassword(), professeur.getPassword()))
                return new AuthenticationResponse(jwtService.generateToken(professeur));
            else throw new RuntimeException("Password not correct");
        }

    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User) {
            System.out.println("User authenticated");
            return (User) principal;
        } else {
            System.out.println("User not authenticated");
            throw new Error("User not authenticated");
        }
    }
}
