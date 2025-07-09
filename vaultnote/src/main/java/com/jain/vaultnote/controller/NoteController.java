package com.jain.vaultnote.controller;

import com.jain.vaultnote.dto.NoteRequestDTO;
import com.jain.vaultnote.dto.NoteResponseDTO;
import com.jain.vaultnote.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@Valid @RequestBody NoteRequestDTO requestDTO) {
        String userId = getCurrentUsername();
        return ResponseEntity.ok(noteService.createNote(userId, requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes() {
        String userId = getCurrentUsername();
        return ResponseEntity.ok(noteService.getAllNotes(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable String id) {
        String userId = getCurrentUsername();
        Optional<NoteResponseDTO> note = noteService.getNoteById(userId, id);
        return note.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable String id, @Valid @RequestBody NoteRequestDTO dto) {
        String userId = getCurrentUsername();
        Optional<NoteResponseDTO> updated = noteService.updateNote(userId, id, dto);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable String id) {
        String userId = getCurrentUsername();
        boolean deleted = noteService.deleteNote(userId, id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
