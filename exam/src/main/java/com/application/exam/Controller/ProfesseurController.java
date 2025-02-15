package com.application.exam.Controller;

import com.application.exam.Dto.ProfesseurDTO;
import com.application.exam.Mapper.ProfesseurMapper;
import com.application.exam.Model.Etudiant;
import com.application.exam.Model.ModuleElement;
import com.application.exam.Model.Professeur;
import com.application.exam.Service.ModuleService;
import com.application.exam.Service.ProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/professeurs")
public class ProfesseurController {

    @Autowired
    private ProfesseurService professeurService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ProfesseurMapper mapper;


    @GetMapping
//    public String getAll(){
//        return "hello lamana from spring boot exam management app";
//    }
    public ResponseEntity<List<Professeur>> getAll() {
        try {
            List<Professeur> professeurs = professeurService.getAll();
            return new ResponseEntity<>(professeurs, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println("l'exception: "+ e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Professeur> getById(@PathVariable("id") int id) {
        try {
            Professeur prof = professeurService.getById(id);
            return new ResponseEntity<>(prof, HttpStatus.FOUND);
        } catch(RuntimeException e){
            System.out.println("l'exception: "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<Professeur> save(@RequestBody ProfesseurDTO dto) {
        Professeur savedProfesseur = professeurService.save(mapper.toEntity(dto));
        return new ResponseEntity<>(savedProfesseur, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Professeur> update(@PathVariable("id") int id, @RequestBody Professeur professeur) {
        try {
            Professeur updatedProfesseur = professeurService.update(id, professeur);
            return new ResponseEntity<>(updatedProfesseur, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println("l'exception: "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Professeur> delete(@PathVariable("id") int id) {
        try {
            Professeur rep = professeurService.getById(id);
            professeurService.delete(id);
            return new ResponseEntity<>(rep, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println("l'exception: "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/modules")
    public ResponseEntity<List<ModuleElement>> getModulesProf(@PathVariable("id") int id){
        try {
            List<ModuleElement> modules = professeurService.getProfesseurModules(id);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        }catch (RuntimeException e){
            System.out.println("l'exception: "+ e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/modules")
    public ResponseEntity<List<ModuleElement>> addModulesProf(@PathVariable("id") int id, @RequestBody List<Integer> modulesIds){
        try{
            Professeur prof = professeurService.getById(id);
            List<ModuleElement> modules = new ArrayList<ModuleElement>();
            modulesIds.forEach( modulesId -> modules.add(this.moduleService.getById(modulesId)));
            List<ModuleElement> allModules = prof.addModules(modules);
            this.professeurService.save(prof);
            return new ResponseEntity<>(allModules, HttpStatus.ACCEPTED);
        }
        catch (RuntimeException e){
            System.out.println("erreur lors de l'ajout des modules : "+ e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
