package com.application.exam.Service;

import com.application.exam.Model.Note;
import com.application.exam.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Get all notes
    public List<Note> getAll() {
        return this.noteRepository.findAll();
    }

    // Get a note by ID
    public Optional<Note> getById(int id) {
        return this.noteRepository.findById(id);
    }

    // Add a new note
    public Note saveNote(Note note) {
        return this.noteRepository.save(note);
    }

    // Update an existing note
    public Note updateNote(int id, Note updatedNote) {
        return this.noteRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setEtudiant(updatedNote.getEtudiant());
                    existingNote.setExam(updatedNote.getExam());
                    existingNote.setNote(updatedNote.getNote());
                    return noteRepository.save(existingNote);
                })
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    // Delete a note by ID
    public void deleteNote(int id) {
        this.noteRepository.deleteById(id);
    }

    public Note findByEtudiantAndModule(int etudiantId, int moduleElementId) {
        return this.noteRepository.findByEtudiantAndModule(etudiantId, moduleElementId);
    }

    public List<Note> findByEtudiant(int etudiantId) {
        return this.noteRepository.findByEtudiant(etudiantId);
    }

    public List<Note> findByModule(int moduleElementId) {
        return this.noteRepository.findByModule(moduleElementId);
    }

}

