package com.application.exam.Model;

import com.application.exam.Configuration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant extends User{

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "etudiant_module",
            joinColumns = @JoinColumn(name = "etudiant_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<ModuleElement> modules;

    @JsonIgnore
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    public Etudiant(int code, String email, String firstName, String lastName, List<ModuleElement> modules, List<Note> notes) {
        super(code, email, firstName, lastName, Role.ETUDIANT);
        this.modules = modules;
        this.notes = notes;
    }

    @PrePersist
    public void prePersist() {
        setRole(Role.ETUDIANT);
    }



    public List<ModuleElement> addModules(List<ModuleElement> modules) {
        modules.forEach(module -> {
            if (!module.getEtudiants().contains(this)) {
                module.addEtudiants(List.of(this));
            }
        });
        this.modules.addAll(modules);
        return this.modules;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + super.getId() +
                ", code=" + super.getCode() +
                ", fullName='" + super.getFirstName() + ' ' + super.getLastName() + '\'' +
                '}';
    }
}
