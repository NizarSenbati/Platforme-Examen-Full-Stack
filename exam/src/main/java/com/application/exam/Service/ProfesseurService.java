package com.application.exam.Service;

import com.application.exam.Model.ModuleElement;
import com.application.exam.Model.Professeur;
import com.application.exam.Repository.ProfesseurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesseurService {

    @Autowired
    private ProfesseurRepository professeurRepository;

    public List<Professeur> getAll() {
        return this.professeurRepository.findAll();
    }

    public Professeur getById(int id) {
    return this.professeurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Professeur not found with id: " + id));
}

    @Transactional
    public Professeur save(Professeur professeur) {
        if(!professeur.getModules().isEmpty()){
            professeur.getModules().forEach(module -> module.setProfesseur(professeur));
        }
        return professeurRepository.save(professeur);
    }

    public Professeur update(int id, Professeur updatedProfesseur) {
        return this.professeurRepository.findById(id)
                .map(existingProf -> {
                    existingProf.setCode(updatedProfesseur.getCode());
                    existingProf.setFirstName(updatedProfesseur.getFirstName());
                    existingProf.setLastName(updatedProfesseur.getLastName());
                    existingProf.setModules(updatedProfesseur.getModules());
                    return professeurRepository.save(existingProf);
                })
                .orElseThrow(() -> new RuntimeException("Professeur not found with id: " + id));
    }


    public void delete(int id) {
        this.professeurRepository.deleteById(id);
    }

    public List<ModuleElement> getProfesseurModules(int id) {

        return this.professeurRepository.findById(id)
            .map(Professeur::getModules)
            .orElseThrow(() -> new RuntimeException("Professeur not found with id: " + id)
            );
    }

}
