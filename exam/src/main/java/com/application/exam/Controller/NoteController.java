package com.application.exam.Controller;

import com.application.exam.Model.Note;
import com.application.exam.Service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // Get all notes
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAll();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    // Get a specific note by ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable int id) {
        Optional<Note> note = noteService.getById(id);
        return note.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Add a new note
    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        Note savedNote = noteService.saveNote(note);
        return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
    }

    // Update a note by ID
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") int id, @RequestBody Note note) {
        try {
            Note updatedNote = noteService.updateNote(id, note);
            return new ResponseEntity<>(updatedNote, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a note by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") int id) {
        try {
            noteService.deleteNote(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all notes by student ID
    @GetMapping("/etudiant/{id}")
    public ResponseEntity<List<Note>> getNotesByEtudiantId(@PathVariable("id") int id) {
        List<Note> notes = noteService.findByEtudiant(id);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/module/{id}")
    public ResponseEntity<List<Note>> getNotesByModuleId(@PathVariable("id") int id) {
        List<Note> notes = noteService.findByModule(id);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/etudiant/{etudiantId}/module/{moduleId}")
    public ResponseEntity<Note> getNoteByEtudiantAndModule(@PathVariable("etudiantId") int etudiantId, @PathVariable("moduleId") int moduleId) {
        Note note = noteService.findByEtudiantAndModule(etudiantId, moduleId);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

}
