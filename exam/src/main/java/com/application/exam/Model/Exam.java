package com.application.exam.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre;

    private int duree;


    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @JsonBackReference
    @OneToOne(mappedBy = "exam")
    private ModuleElement module;


    @ManyToMany
    @JoinTable(
            name = "exam_question",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    public Exam() {}

    public Exam(String titre, int duree, ModuleElement module, List<Question> questions) {
        this.titre = titre;
        this.duree = duree;
        this.module = module;
        this.questions = questions;
    }

    public List<Question> addQuestions(List<Question> questions) {
        List<Exam> exams = new ArrayList<>();
        for (Question question : questions) {
            if (!this.questions.contains(question)) {
                this.questions.add(question);
                // Avoid infinite recursion by checking if exam is already in question
                if (!question.getExams().contains(this)) {
                    exams = question.addExams(List.of(this));
                }
            }
        }
        System.out.println("exams: "+ exams);
        return this.questions;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", titre=" + titre +
                ", duree=" + duree +
                ", module=" + module +
                ",\nQuestions{ " + questions + " \n}" +
                '}';
    }
}

