package com.application.exam.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class EtudiantDTO {
    private int code;

    private String email;

    private String password;

    private String firstName;
    private String lastName;

    private Set<Integer> moduleIds;

    private Set<Integer> noteIds;
}
