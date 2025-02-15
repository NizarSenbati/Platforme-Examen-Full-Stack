package com.application.exam.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String question;

    private String difficulte;

    private String sujet;

    private String rep1;
    private String rep2;
    private String rep3;
    private String rep4;

    private int indice;

    @ManyToMany(mappedBy = "questions")
    @JsonIgnore
    @JsonBackReference
    private List<Exam> exams;

    public Question(){}

    public Question(String question, String difficulte, String sujet, String rep1, String rep2, String rep3, String rep4, int indice, List<Exam> exams) {
        this.question = question;
        this.difficulte = difficulte;
        this.sujet = sujet;
        this.rep1 = rep1;
        this.rep2 = rep2;
        this.rep3 = rep3;
        this.rep4 = rep4;
        this.indice = indice;
        this.exams = exams;
    }

    public List<Exam> addExams(List<Exam> exams) {
        for (Exam exam : exams) {
            if (!this.exams.contains(exam)) {
                this.exams.add(exam);
                // Avoid infinite recursion by checking if question is already in exam
                if (!exam.getQuestions().contains(this)) {
                    exam.addQuestions(List.of(this));
                }
            }
        }
        return this.exams;
    }

}
