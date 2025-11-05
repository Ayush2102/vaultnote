package com.jain.vaultnote.service;

import com.jain.vaultnote.dto.NoteRequestDTO;
import com.jain.vaultnote.dto.NoteResponseDTO;
import com.jain.vaultnote.entity.Note;
import com.jain.vaultnote.mapper.NoteMapper;
import com.jain.vaultnote.repository.NoteRepository;
import com.jain.vaultnote.utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final EncryptionUtil encryptionUtil;
    private final NoteMapper noteMapper;

    public NoteResponseDTO createNote(String userId, NoteRequestDTO dto) {
        Note note = Note.builder()
                .userId(userId)
                .title(dto.getTitle())
                .encryptedContent(encryptionUtil.encrypt(dto.getContent()))
                .tags(dto.getTags())
                .createdAt(new Date())
                .updatedAt(new Date())
                .expiresAt(dto.getExpiresAt())
                .isPublic(dto.isPublic())
                .build();

        Note savedNote = noteRepository.save(note);
        return noteMapper.toDto(savedNote);
    }

    public List<NoteResponseDTO> getAllNotes(String userId) {
        return noteRepository.findByUserId(userId).stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<NoteResponseDTO> getNoteById(String userId, String id) {
        return noteRepository.findById(new ObjectId(id))
                .filter(note -> note.getUserId().equals(userId))
                .map(noteMapper::toDto);
    }

    public boolean deleteNote(String userId, String id) {
        Optional<Note> note = noteRepository.findById(new ObjectId(id));
        if (note.isPresent() && note.get().getUserId().equals(userId)) {
            noteRepository.deleteById(new ObjectId(id));
            return true;
        }
        return false;
    }

    public Optional<NoteResponseDTO> updateNote(String userId, String id, NoteRequestDTO dto) {
        Optional<Note> noteOpt = noteRepository.findById(new ObjectId(id));
        if (noteOpt.isEmpty() || !noteOpt.get().getUserId().equals(userId)) {
            return Optional.empty();
        }

        Note note = noteOpt.get();
        note.setTitle(dto.getTitle());
        note.setEncryptedContent(encryptionUtil.encrypt(dto.getContent()));
        note.setTags(dto.getTags());
        note.setUpdatedAt(new Date());
        note.setExpiresAt(dto.getExpiresAt());
        note.setPublic(dto.isPublic());

        return Optional.of(noteMapper.toDto(noteRepository.save(note)));
    }
}
