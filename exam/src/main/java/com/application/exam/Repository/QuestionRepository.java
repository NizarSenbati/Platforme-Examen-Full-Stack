package com.application.exam.Repository;

import com.application.exam.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("SELECT q FROM Question q WHERE q.sujet = :sujet")
    List<Question> findBySujet(@Param("sujet") String sujet);
}
