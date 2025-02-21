package com.application.exam.Dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ModuleDTO {

    private String nom;

    private int annee;

    private String session;

    private String semestre;

    private List<Integer> ressourcesIds;

    private int professeurId;

    private int examId;

    private Set<Integer> etudiantsIds;
}
