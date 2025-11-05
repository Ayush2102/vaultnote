package com.jain.vaultnote.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class NoteResponseDTO {
    private String id;
    private String title;
    private String content;
    private List<String> tags;
    private Date createdAt;
    private Date updatedAt;
    private Date expiresAt;
    private boolean isPublic;
}
