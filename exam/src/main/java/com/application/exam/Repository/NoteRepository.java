package com.application.exam.Repository;


import com.application.exam.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query("SELECT n FROM Note n WHERE n.etudiant.id = ?1 AND n.exam.id = ?2")
    Note findByEtudiantAndModule(int etudiantId, int examId);

    @Query("SELECT n FROM Note n WHERE n.etudiant.id = ?1")
    List<Note> findByEtudiant(int etudiantId);

    @Query("SELECT n FROM Note n WHERE n.exam.id = ?1")
    List<Note> findByModule(int examId);
}
