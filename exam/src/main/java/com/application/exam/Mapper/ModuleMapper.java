package com.application.exam.Mapper;

import com.application.exam.Dto.ModuleDTO;
import com.application.exam.Model.Etudiant;
import com.application.exam.Model.Exam;
import com.application.exam.Model.ModuleElement;
import com.application.exam.Model.Professeur;
import com.application.exam.Service.EtudiantService;
import com.application.exam.Service.ExamService;
import com.application.exam.Service.ProfesseurService;
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

    public ModuleElement toEntity(ModuleDTO dto) {
        Professeur professeur = this.professeurService.getById(dto.getProfesseurId());
        Exam exam = this.examService.getById(dto.getExamId());
        List<Etudiant> etudiants = new ArrayList<Etudiant>();
        dto.getEtudiantsIds().forEach(e -> etudiants.add(this.etudiantService.getById(e)));
        return new ModuleElement(dto.getNom(), professeur, dto.getAnnee(), dto.getSession(), etudiants, exam);
    }
}
