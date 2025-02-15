package com.application.exam.Controller;

import com.application.exam.Model.Exam;
import com.application.exam.Model.Question;
import com.application.exam.Service.ExamService;
import com.application.exam.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.application.exam.Dto.ExamDTO;
import com.application.exam.Mapper.ExamMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
@Validated
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private ExamMapper mapper;
    @Autowired
    private QuestionService questionService;

    // Get all exams
    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.getAll();
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    // Add a new exam
//    @PostMapping
//    public ResponseEntity<Exam> addExam(@RequestBody Exam exam) {
//        Exam savedExam = examService.saveExam(exam);
//        return new ResponseEntity<>(savedExam, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<Exam> createExam(@RequestBody ExamDTO examDTO) {
        return ResponseEntity.ok(examService.saveExam(mapper.toEntity(examDTO)));
    }

    // Get a specific exam by ID
    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") int id) {
        try {
            Exam exam = examService.getById(id);
            return new ResponseEntity<>(exam, HttpStatus.OK);
        } catch (RuntimeException e){
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(@PathVariable("id") int id, @RequestBody Exam exam) {
        try {
            Exam updatedExam = examService.updateExam(id, exam);
            return new ResponseEntity<>(updatedExam, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an exam by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable("id") int id) {
        try {
            examService.deleteExam(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{exam_id}/module/{module_id}")
    public ResponseEntity<Exam> assingModule(@PathVariable("exam_id") int exam_id, @PathVariable("module_id") int module_id) {
        try {
            Exam exam = examService.assingModule(exam_id, module_id);
            return new ResponseEntity<Exam>(exam, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{exam_id}/questions")
    public ResponseEntity<Exam> assignQuestions(@PathVariable("exam_id") int exam_id, @RequestBody List<Integer> questionIds) {
        try {
            List<Question> questions = new ArrayList<>();
            questionIds.forEach(id -> questions.add(questionService.getOne(id)));
            Exam exam = examService.getById(exam_id);
            exam.addQuestions(questions);
            exam = examService.saveExam(exam);
            return new ResponseEntity<>(exam, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}



