package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Publisher;
import com.msglearning.javabackend.errors.ErrorResponse;
import com.msglearning.javabackend.services.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_PUBLISHER })
public class PublisherController {
    private static final String ID_PATH = "/{id}";

    // Using constructor injection is generally considered a best practice.
    // It promotes better encapsulation and makes dependencies explicit
    // Can improve code readability, testability, and maintainability.
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/all")
    public List<Publisher> getAllPublishers() { return publisherService.getAllPublishers(); }

    @GetMapping(ID_PATH)
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        return publisherService.getPublisherById(id);
    }

    //ToDo: PublisherController PostMapping
    @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody Publisher publisher) {
        return publisherService.savePublisher(publisher);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @RequestBody Publisher publisher) {
        return publisherService.updatePublisher(id, publisher);
    }
    @DeleteMapping(ID_PATH)
    public void deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
    }
}