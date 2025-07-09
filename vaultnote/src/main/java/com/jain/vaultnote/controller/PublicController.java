package com.jain.vaultnote.controller;

import com.jain.vaultnote.dto.LoginRequestDTO;
import com.jain.vaultnote.dto.NoteResponseDTO;
import com.jain.vaultnote.dto.UserDTO;
import com.jain.vaultnote.entity.Note;
import com.jain.vaultnote.entity.User;
import com.jain.vaultnote.mapper.NoteMapper;
import com.jain.vaultnote.mapper.UserMapper;
import com.jain.vaultnote.repository.NoteRepository;
import com.jain.vaultnote.service.UserDetailsServiceImp;
import com.jain.vaultnote.service.UserService;
import com.jain.vaultnote.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public")
@Slf4j
@Validated
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImp userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck(){
        log.info("Health is OK !");
        return "Ok";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserDTO dto){
        User newUser = UserMapper.toEntity(dto);
        userService.saveNewUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());
            String jwt = jwtUtil.generateToken((userDetails.getUsername()));

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            log.warn("Login failed for username: {}", loginRequest.getUserName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            log.error("Unexpected error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteResponseDTO>> getAllPublicNotes() {
        List<NoteResponseDTO> publicNotes = noteRepository.findByIsPublicTrue()
                .stream()
                .map(noteMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(publicNotes);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<NoteResponseDTO> getPublicNoteById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        return noteRepository.findById(objectId)
                .filter(Note::isPublic)
                .map(noteMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
