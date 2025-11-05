package com.jain.vaultnote.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NoteRequestDTO {
    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Content cannot be empty")
    private String content;

    private List<String> tags;

    private Date expiresAt;

    private boolean isPublic;
}
