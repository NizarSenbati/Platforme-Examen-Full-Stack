package com.application.exam.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class ModuleElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private int annee;

    private String session;

    private String semestre;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "module_id")  // This creates a module_id column in the Ressource table
    private List<Ressource> ressources;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professeur_id")
    private Professeur professeur;

    @JsonBackReference
    @ManyToMany(mappedBy = "modules")
    private List<Etudiant> etudiants;

    @OneToOne(cascade = CascadeType.ALL)
    private Exam exam;


    public ModuleElement() {}


    public ModuleElement(String nom, Professeur professeur, int annee, String session, String semestre, List<Etudiant> etudiants, Exam exam, List<Ressource> ressources) {
        this.nom = nom;
        this.professeur = professeur;
        this.etudiants = etudiants;
        this.annee = annee;
        this.session = session;
        this.exam = exam;
        this.semestre = semestre;
        this.ressources = ressources;
    }

    public List<Etudiant> addEtudiants(List<Etudiant> etudiants) {
        etudiants.forEach(etudiant -> {
            if(!etudiant.getModules().isEmpty())
                if (!etudiant.getModules().contains(this)) {
                    etudiant.addModules(List.of(this));
                }
        });
        this.etudiants.addAll(etudiants);
        return this.etudiants;
    }

    public List<Ressource> addRessources(List<Ressource> ressources) {
        if(this.ressources == null)
            this.ressources = ressources;
        else
            this.ressources.addAll(ressources);
        return this.ressources;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id = " + id +
                ", nom = '" + nom + '\'' +
                ", professeur = " + professeur.getFirstName() + ' ' + professeur.getLastName() +
                ", etudiants = " + etudiants.stream().map(Etudiant::toString).collect(Collectors.joining(",\n ")) +
                ", Titre exam = " + exam.getTitre() +
                '}';
    }
}
