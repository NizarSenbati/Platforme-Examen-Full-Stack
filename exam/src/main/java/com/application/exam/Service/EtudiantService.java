package com.application.exam.Service;

import com.application.exam.Configuration.Role;
import com.application.exam.Model.Etudiant;
import com.application.exam.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    // Get all students
    public List<Etudiant> getAll() {
        return this.etudiantRepository.findAll();
    }

    // Get a student by ID
    public Etudiant getById(int id) {
        return this.etudiantRepository.findById(id).orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + id));
    }

    // Add a new student
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return this.etudiantRepository.save(etudiant);
    }

    // Update an existing student
    public Etudiant updateEtudiant(int id, Etudiant updatedEtudiant) {
        return this.etudiantRepository.findById(id)
                .map(existingEtudiant -> {
                    existingEtudiant.setCode(updatedEtudiant.getCode());
                    existingEtudiant.setLastName(updatedEtudiant.getLastName());
                    existingEtudiant.setFirstName(updatedEtudiant.getFirstName());
                    existingEtudiant.setModules(updatedEtudiant.getModules());
                    return etudiantRepository.save(existingEtudiant);
                })
                .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + id));
    }

    // Delete a student by ID
    public void deleteEtudiant(int id) {
        this.etudiantRepository.deleteById(id);
    }
}

