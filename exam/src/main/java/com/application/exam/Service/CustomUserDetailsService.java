package com.application.exam.Service;

import com.application.exam.Model.Etudiant;
import com.application.exam.Model.Professeur;
import com.application.exam.Repository.EtudiantRepository;
import com.application.exam.Repository.ProfesseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final EtudiantRepository etudiantRepository;
    private final ProfesseurRepository professeurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Etudiant> student = etudiantRepository.findByEmail(email);
        if (student.isPresent()) {
            return student.get();
        }

        Optional<Professeur> professeur = Optional.ofNullable(professeurRepository.findByEmail(email));
        if (professeur.isPresent()) {
            return professeur.get();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
