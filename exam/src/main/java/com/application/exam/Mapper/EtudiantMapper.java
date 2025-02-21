package com.application.exam.Mapper;

import com.application.exam.Dto.EtudiantDTO;
import com.application.exam.Model.*;
import com.application.exam.Service.ModuleService;
import com.application.exam.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EtudiantMapper {
    @Autowired
    NoteService noteService;
    @Autowired
    ModuleService moduleService;

    public Etudiant toEntity(EtudiantDTO dto) {
        List<ModuleElement> modules;
        if( !dto.getModuleIds().isEmpty() ) {
            modules = new ArrayList<>();
            dto.getModuleIds().forEach(e -> modules.add(this.moduleService.getById(e)));
        } else
            modules = null;
        List<Note> notes;
        if( !dto.getNoteIds().isEmpty() ) {
            notes = new ArrayList<>();
            dto.getNoteIds().forEach(e -> notes.add(this.noteService.getById(e).orElseThrow(() -> new RuntimeException("Note not found"))));
        }
        else
            notes = null;
        return new Etudiant(dto.getCode(), dto.getEmail(), dto.getFirstName(), dto.getLastName(), modules, notes);
    }
}
