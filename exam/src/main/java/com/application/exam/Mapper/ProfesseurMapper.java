package com.application.exam.Mapper;

import com.application.exam.Dto.ProfesseurDTO;
import com.application.exam.Model.ModuleElement;
import com.application.exam.Model.Professeur;
import com.application.exam.Service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfesseurMapper {
    @Autowired
    ModuleService moduleService;

    public Professeur toEntity(ProfesseurDTO dto) {
        List<ModuleElement> modules = new ArrayList<>();

        dto.getModuleIds().forEach(e -> modules.add(this.moduleService.getById(e)));

        return new Professeur(dto.getCode(), dto.getEmail(), dto.getFirstName(), dto.getLastName(), modules, dto.getDepartement());
    }
}
