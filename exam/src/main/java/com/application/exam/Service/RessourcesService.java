package com.application.exam.Service;

import com.application.exam.Model.Ressource;
import com.application.exam.Repository.RessourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RessourcesService {
    @Autowired
    private RessourcesRepository ressourcesRepository;

    public Ressource getById(int id){
        return this.ressourcesRepository.findById(id).orElseThrow(() -> new RuntimeException("Ressource not found"));
    }

}
