package com.application.exam.Controller;

import com.application.exam.Dto.ModuleDTO;
import com.application.exam.Mapper.ModuleMapper;
import com.application.exam.Model.Etudiant;
import com.application.exam.Model.Exam;
import com.application.exam.Model.ModuleElement;
import com.application.exam.Model.Professeur;
import com.application.exam.Service.ModuleService;
import com.application.exam.Service.ProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ProfesseurService professeurService;
    @Autowired
    private ModuleMapper mapper;

    // Get all modules
    @GetMapping
    public ResponseEntity<List<ModuleElement>> getAllModules() {
        List<ModuleElement> modules = moduleService.getAll();
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    // Add a new module
    @PostMapping
    public ResponseEntity<ModuleElement> addModule(@RequestBody ModuleDTO DTO) {
        ModuleElement savedModule = moduleService.saveModule(mapper.toEntity(DTO));
        return new ResponseEntity<>(savedModule, HttpStatus.CREATED);
    }

    // Get a specific module by ID
    @GetMapping("/{id}")
    public ResponseEntity<ModuleElement> getModuleById(@PathVariable("id") int id) {
        try{
        ModuleElement module = moduleService.getById(id);
        return new ResponseEntity<>(module, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a module by ID
    @PutMapping("/{id}")
    public ResponseEntity<ModuleElement> updateModule(@PathVariable("id") int id, @RequestBody ModuleElement module) {
        try {
            ModuleElement updatedModule = moduleService.updateModule(id, module);
            return new ResponseEntity<>(updatedModule, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a module by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable("id") int id) {
        try {
            moduleService.deleteModule(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/exams")
    public ResponseEntity<Exam> getExam(@PathVariable("id") int id_module){
        try{
            Exam exam = moduleService.getExamduModule(id_module);
            return new ResponseEntity<>(exam, HttpStatus.FOUND);
        } catch (RuntimeException e){
            System.out.println("erreur lors de getExam: "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id_module}/professeurs")
    public ResponseEntity<Professeur> getProf(@PathVariable("id_module") int id_module){
        try{
            Professeur prof = moduleService.getProfduModule(id_module);
            return new ResponseEntity<>(prof, HttpStatus.FOUND);
        } catch (RuntimeException e){
            System.out.println("erreur lors de getProf: "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id_module}/professeurs/{id_prof}")
    public ResponseEntity<ModuleElement> assignProf(@PathVariable("id_prof") int id_prof, @PathVariable("id_module") int id_module){
        try {
            ModuleElement module = this.moduleService.assignProf(id_prof, id_module);
            return new ResponseEntity<>(module, HttpStatus.OK);
        } catch(RuntimeException e){
            System.out.println("erreur lors de assignProf:   "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id_module}/etudiants")
    public ResponseEntity<List<Etudiant>> getEtudiantsModules(@PathVariable("id_module") int id_module){
        try{
            List<Etudiant> etudiants = moduleService.getEtudiantsModule(id_module);
            return new ResponseEntity<>(etudiants, HttpStatus.FOUND);
        } catch (RuntimeException e){
            System.out.println("erreur lors de getEtudiantsModules:   "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/etudiants")
    public ResponseEntity<List<Etudiant>> addEtudiantsModules(@PathVariable("id") int id, @RequestBody List<Etudiant> etudiants){
        try{
            ModuleElement module = moduleService.getById(id);
            List<Etudiant> allEtudiants = module.addEtudiants(etudiants);
            return new ResponseEntity<>(allEtudiants, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
