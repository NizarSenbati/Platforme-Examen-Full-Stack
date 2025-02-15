package com.application.exam.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ModuleDTO {
    private int code;

    private String nom;

    private int annee;

    private String session;

    private int professeurId;

    private int examId;

    private Set<Integer> etudiantsIds;
}
