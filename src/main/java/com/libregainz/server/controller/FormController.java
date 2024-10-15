package com.libregainz.server.controller;
import com.libregainz.server.model.Form;
import com.libregainz.server.repo.FormRepo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/forms")
public class FormController {

    private final FormRepo formRepo;
    public FormController(FormRepo formRepo) {
        this.formRepo = formRepo;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadForm(@RequestParam("file") MultipartFile file) {
        try {
            Form form = new Form();
            form.setContent(file.getBytes());
            formRepo.save(form);
            return ResponseEntity.status(HttpStatus.CREATED).body("Form uploaded successfully with ID: " + form.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading form");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getForm(@PathVariable int id) {
        Optional<Form> form = formRepo.findById(id);
        if (form.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "form_" + id);
            return new ResponseEntity<>(form.get().getContent(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

