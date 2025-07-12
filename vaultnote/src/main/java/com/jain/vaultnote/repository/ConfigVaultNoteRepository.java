package com.jain.vaultnote.repository;

import com.jain.vaultnote.entity.ConfigVaultNote;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigVaultNoteRepository extends MongoRepository<ConfigVaultNote, ObjectId> {
}
