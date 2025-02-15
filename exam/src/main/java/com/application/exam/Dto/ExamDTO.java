package com.application.exam.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ExamDTO {
    private String titre;

    private int duree;

    private int moduleElementId;

    private Set<Integer> questionIds;  // Using Set to avoid duplicates
}