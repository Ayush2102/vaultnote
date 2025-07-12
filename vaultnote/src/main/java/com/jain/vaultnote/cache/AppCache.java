package com.jain.vaultnote.cache;

import com.jain.vaultnote.entity.ConfigVaultNote;
import com.jain.vaultnote.repository.ConfigVaultNoteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigVaultNoteRepository configVaultNoteRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigVaultNote> all = configVaultNoteRepository.findAll();
        for (ConfigVaultNote configVaultNote : all) {
            appCache.put(configVaultNote.getKey(), configVaultNote.getValue());
        }

    }
}
