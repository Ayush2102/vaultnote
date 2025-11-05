package com.jain.vaultnote.repository;

import com.jain.vaultnote.entity.Note;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, ObjectId> {

    List<Note> findByUserId(String userId);

    List<Note> findByUserIdAndTagsContaining(String userId, String tag);

    List<Note> findByUserIdAndExpiresAtBefore(String userId, java.util.Date date);

    List<Note> findByIsPublicTrue();
}
