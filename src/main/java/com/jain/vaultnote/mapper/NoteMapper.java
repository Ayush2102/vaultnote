package com.jain.vaultnote.mapper;

import com.jain.vaultnote.dto.NoteResponseDTO;
import com.jain.vaultnote.entity.Note;
import com.jain.vaultnote.utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoteMapper {

    private final EncryptionUtil encryptionUtil;

    public NoteResponseDTO toDto(Note note) {
        return NoteResponseDTO.builder()
                .id(note.getId().toHexString())
                .title(note.getTitle())
                .content(encryptionUtil.decrypt(note.getEncryptedContent()))
                .tags(note.getTags())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .expiresAt(note.getExpiresAt())
                .isPublic(note.isPublic())
                .build();
    }
}
