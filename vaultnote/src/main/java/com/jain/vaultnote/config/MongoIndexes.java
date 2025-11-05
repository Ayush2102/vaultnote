package com.jain.vaultnote.config;

import com.jain.vaultnote.entity.Note;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoIndexes {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        // TTL index — deletes notes automatically when 'expiresAt' time passes
        Index ttlIndex = new Index()
                .on("expiresAt", Sort.Direction.ASC)
                .expire(0);
        // Create TTL index asynchronously (non-blocking, preferred)
        mongoTemplate.indexOps(Note.class).createIndex(ttlIndex);

        // Secondary indexes for faster lookups
        mongoTemplate.indexOps(Note.class).createIndex(
                new Index().on("userId", Sort.Direction.ASC)
        );

        mongoTemplate.indexOps(Note.class).createIndex(
                new Index().on("isPublic", Sort.Direction.ASC)
        );
    }
}
