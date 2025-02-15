package com.application.exam.Service;

import com.application.exam.Model.Etudiant;
import com.application.exam.Model.Exam;
import com.application.exam.Model.ModuleElement;
import com.application.exam.Model.Professeur;
import com.application.exam.Repository.ModuleRepository;
import com.application.exam.Repository.ProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ProfesseurRepository professeurRepository;

    // Get all modules
    public List<ModuleElement> getAll() {
        return this.moduleRepository.findAll();
    }

    // Get a module by ID
    public ModuleElement getById(int id) {
        return this.moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    // Add a new module
    public ModuleElement saveModule(ModuleElement module) {
        return this.moduleRepository.save(module);
    }

    // Update an existing module
    public ModuleElement updateModule(int id, ModuleElement updatedModule) {
        return this.moduleRepository.findById(id)
                .map(existingModule -> {
                    existingModule.setNom(updatedModule.getNom());
                    existingModule.setProfesseur(updatedModule.getProfesseur());
                    existingModule.setEtudiants(updatedModule.getEtudiants());
                    existingModule.setExam(updatedModule.getExam());
                    existingModule.setAnnee(updatedModule.getAnnee());
                    existingModule.setSession((updatedModule.getSession()));
                    return moduleRepository.save(existingModule);
                })
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));
    }

    // Delete a module by ID
    public void deleteModule(int id) {
        this.moduleRepository.deleteById(id);
    }

    public Exam getExamduModule(int id_module){
        return this.moduleRepository.findById(id_module)
                .map(ModuleElement::getExam)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id_module));
    }

    public Professeur getProfduModule(int id_module){
        return this.moduleRepository.findById(id_module)
                .map(ModuleElement::getProfesseur)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id_module));
    }

    public List<Etudiant> getEtudiantsModule(int id_module){
        return this.moduleRepository.findById(id_module)
                .map(ModuleElement :: getEtudiants)
                .orElseThrow( () -> new RuntimeException("Module not found with id " + id_module));
    }

    public ModuleElement assignProf(int id_prof, int id_module){
        Professeur prof = this.professeurRepository.findById(id_prof)
                .orElseThrow(() -> new RuntimeException("Professeur not found with id: " + id_prof));

        ModuleElement module = this.moduleRepository.findById(id_module)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id_module));

        module.setProfesseur(prof);

        return this.moduleRepository.save(module);
    }
}

