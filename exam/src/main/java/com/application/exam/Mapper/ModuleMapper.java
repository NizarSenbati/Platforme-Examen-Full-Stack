package com.application.exam.Mapper;

import com.application.exam.Dto.ModuleDTO;
import com.application.exam.Model.*;
import com.application.exam.Service.EtudiantService;
import com.application.exam.Service.ExamService;
import com.application.exam.Service.ProfesseurService;
import com.application.exam.Service.RessourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModuleMapper {
    @Autowired
    private ProfesseurService professeurService;
    @Autowired
    private ExamService examService;
    @Autowired
    private EtudiantService etudiantService;
    @Autowired
    private RessourcesService ressourceService;

    public ModuleElement toEntity(ModuleDTO dto) {
        Professeur professeur = null;
        if (dto.getProfesseurId() != 0)
                professeur = this.professeurService.getById(dto.getProfesseurId());
        Exam exam = null;
        if (dto.getExamId() != 0) {
            exam = this.examService.getById(dto.getExamId());
        }
        List<Etudiant> etudiants;
        if (!dto.getEtudiantsIds().isEmpty()) {
            etudiants = new ArrayList<Etudiant>();
            dto.getEtudiantsIds().forEach(e -> etudiants.add(this.etudiantService.getById(e)));
        } else {
            etudiants = null;
        }
        List<Ressource> ressources;
        if(!dto.getRessourcesIds().isEmpty()){
            ressources = new ArrayList<Ressource>();
            dto.getRessourcesIds().forEach(r -> ressources.add(this.ressourceService.getById(r)));
        } else {
            ressources = null;
        }
        return new ModuleElement(dto.getNom(), professeur, dto.getAnnee(), dto.getSession(), dto.getSemestre(), etudiants, exam, ressources);
    }
}
