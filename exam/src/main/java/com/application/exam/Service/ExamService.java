package com.application.exam.Service;

import com.application.exam.Model.Exam;
import com.application.exam.Model.ModuleElement;
import com.application.exam.Model.Question;
import com.application.exam.Repository.ExamRepository;
import com.application.exam.Repository.ModuleRepository;
import com.application.exam.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    // Get all exams
    public List<Exam> getAll() {
        return this.examRepository.findAll();
    }

    // Get an exam by ID
    public Exam getById(int id) {
        return this.examRepository.findById(id).orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
    }

    // Add a new exam
    public Exam saveExam(Exam exam) {
        return this.examRepository.save(exam);
    }

    // Update an existing exam
    public Exam updateExam(int id, Exam updatedExam) {
        return this.examRepository.findById(id)
                .map(existingExam -> {
                    existingExam.setTitre(updatedExam.getTitre());
                    existingExam.setDuree(updatedExam.getDuree());
                    existingExam.setNotes(updatedExam.getNotes());
                    existingExam.setModule(updatedExam.getModule());
                    return examRepository.save(existingExam);
                })
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
    }

    // Delete an exam by ID
    public void deleteExam(int id) {
        this.examRepository.deleteById(id);
    }

    public void assignQuestions(Exam exam, int id_question){
        Optional<Question> optionalQuestion = questionRepository.findById(id_question);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            exam.addQuestions(List.of(question));
            this.examRepository.save(exam);
        }
    }

    public Exam assingModule(int exam_id, int module_id){
        Optional<Exam> optionalExam = examRepository.findById(exam_id);
        Optional<ModuleElement> optionalModule = moduleRepository.findById(module_id);
        if (optionalExam.isPresent() && optionalModule.isPresent()) {
            Exam exam = optionalExam.get();
            ModuleElement module = optionalModule.get();
            module.setExam(exam);
            exam.setModule(module);
            exam = this.examRepository.save(exam);
            moduleRepository.save(module);
            return exam;
        }else {
            throw new RuntimeException("Exam or Module not found");
        }
    }

}

