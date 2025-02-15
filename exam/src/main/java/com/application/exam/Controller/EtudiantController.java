package com.application.exam.Controller;

import com.application.exam.Authentication.AuthenticationService;
import com.application.exam.Dto.EtudiantDTO;
import com.application.exam.Dto.ExamDTO;
import com.application.exam.Mapper.EtudiantMapper;
import com.application.exam.Model.*;
import com.application.exam.Service.EtudiantService;
import com.application.exam.Service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;
    @Autowired
    private EtudiantMapper mapper;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private AuthenticationService authService;

    // Get all students
    @GetMapping
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.getAll();
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    // Get a specific student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable("id") int id) {
        Etudiant etudiant = etudiantService.getById(id);
        return new ResponseEntity<>(etudiant, HttpStatus.OK);
    }

    // Create a new student
//    @PostMapping
//    public ResponseEntity<Etudiant> addEtudiant(@RequestBody Etudiant etudiant) {
//        Etudiant savedEtudiant = etudiantService.saveEtudiant(etudiant);
//        return new ResponseEntity<>(savedEtudiant, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Etudiant> createExam(@RequestBody EtudiantDTO etudiantDTO) {
        return new ResponseEntity<>(etudiantService.saveEtudiant(mapper.toEntity(etudiantDTO)), HttpStatus.CREATED);
    }

    // Update a student by ID
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(
            @PathVariable int id,
            @RequestBody Etudiant etudiant) {
        try {
            Etudiant updatedEtudiant = etudiantService.updateEtudiant(id, etudiant);
            return new ResponseEntity<>(updatedEtudiant, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable("id") int id) {
        try {
            etudiantService.deleteEtudiant(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/modules")
    public ResponseEntity<List<ModuleElement>> addEtudiantsModules(@PathVariable("id") int id, @RequestBody List<Integer> modulesIds){
        try{
            Etudiant etudiant = etudiantService.getById(id);
            List<ModuleElement> allModules = new ArrayList<ModuleElement>();
            modulesIds.forEach(e -> allModules.add(moduleService.getById(e)));
            etudiant.addModules(allModules);
            return new ResponseEntity<>(etudiant.getModules(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/modules")
    public ResponseEntity<List<ModuleElement>> getEtudiantsModules(@PathVariable("id") int id){
        try{
            Etudiant etudiant = etudiantService.getById(id);
            List<ModuleElement> modules = etudiant.getModules();
            return new ResponseEntity<>(modules, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/exams")
    public ResponseEntity<List<Exam>> getExamEtudiant(@PathVariable("id") int id){
        try{
            Etudiant etudiant = etudiantService.getById(id);
            List<Exam> exams = new ArrayList<>();
            etudiant.getNotes().forEach(e -> exams.add(e.getExam()));
            return new ResponseEntity<>(exams, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/notes")
    public ResponseEntity<Map<String, Float>> getEtudiantNotes(@PathVariable("id") int id){
        try{
            Etudiant etudiant = etudiantService.getById(id);
            List<Note> notes = etudiant.getNotes();

            Map<String, Float> moduleNotes = new HashMap<>();
            notes.forEach(e -> {
                moduleNotes.put(e.getExam().getModule().getNom(), e.getNote());
            });
            return new ResponseEntity<>(moduleNotes, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

