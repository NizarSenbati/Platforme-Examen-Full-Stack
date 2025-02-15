package com.application.exam.Model;

import com.application.exam.Configuration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professeur extends User{

    private String departement;

    @JsonIgnore
    @OneToMany(mappedBy = "professeur")
    private List<ModuleElement> modules;

    @PrePersist
    public void prePersist() {
        setRole(Role.PROFESSEUR);
    }

    public Professeur(int code, String email, String firstName, String lastName, List<ModuleElement> modules, String departement) {
        super(code, email, firstName, lastName, Role.ETUDIANT);
        this.modules = modules;
        this.departement = departement;
    }

    public List<ModuleElement> addModules(List<ModuleElement> modules){
        this.modules.addAll(modules);
        modules.forEach(e -> e.setProfesseur(this));
        return this.modules;
    }

}
