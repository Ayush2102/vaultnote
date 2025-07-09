package com.jain.vaultnote.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    private ObjectId id;

    @Indexed
    private String userId; // to map note to a specific user

    private String title;

    private String encryptedContent;

    private List<String> tags;

    private Date createdAt;

    private Date updatedAt;

    private Date expiresAt;

    @JsonProperty("isPublic")
    private boolean isPublic;
}
