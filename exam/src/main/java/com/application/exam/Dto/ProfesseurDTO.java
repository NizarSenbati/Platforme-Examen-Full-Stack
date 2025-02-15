package com.application.exam.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProfesseurDTO {
    private String email;
    private String password;

    private int code;

    private String firstName;
    private String lastName;

    private String departement;

    private Set<Integer> moduleIds;
}
