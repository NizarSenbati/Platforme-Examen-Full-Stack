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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professeur_id")
    private Professeur professeur;

    @JsonBackReference
    @ManyToMany(mappedBy = "modules")
    private List<Etudiant> etudiants;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    private Exam exam;


    public ModuleElement() {}


    public ModuleElement(String nom, Professeur professeur, int annee, String session, List<Etudiant> etudiants, Exam exam) {
        this.nom = nom;
        this.professeur = professeur;
        this.etudiants = etudiants;
        this.annee = annee;
        this.session = session;
        this.exam = exam;
    }

    public List<Etudiant> addEtudiants(List<Etudiant> etudiants) {
        etudiants.forEach(etudiant -> {
            if (!etudiant.getModules().contains(this)) {
                etudiant.addModules(List.of(this));
            }
        });
        this.etudiants.addAll(etudiants);
        return this.etudiants;
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
