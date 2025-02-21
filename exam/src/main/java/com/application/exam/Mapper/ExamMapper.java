package com.application.exam.Mapper;

import com.application.exam.Dto.ExamDTO;
import com.application.exam.Model.Exam;
import com.application.exam.Model.ModuleElement;
import com.application.exam.Model.Question;
import com.application.exam.Service.ModuleService;
import com.application.exam.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExamMapper {
    @Autowired
    QuestionService questionService;
    @Autowired
    ModuleService moduleService;

    public Exam toEntity(ExamDTO dto) {
        ModuleElement moduleElement = this.moduleService.getById(dto.getModuleElementId());
        List<Question> questions = new ArrayList<>();

        dto.getQuestionIds().forEach(e -> questions.add(this.questionService.getOne(e)));

        Exam exam = new Exam(dto.getTitre(), dto.getDuree(), moduleElement, questions);
        moduleElement.setExam(exam);
        moduleService.saveModule(moduleElement);
        return exam;
    }
}
