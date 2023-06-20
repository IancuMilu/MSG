package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Publisher;
import com.msglearning.javabackend.errors.ErrorResponse;
import com.msglearning.javabackend.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public ResponseEntity<Publisher> getPublisherById(Long id) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        return optionalPublisher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> savePublisher(Publisher publisher) {
        if (StringUtils.isEmpty(publisher.getName()) || StringUtils.isEmpty(publisher.getWebsite())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Name and website are required."));
        }

        if (publisherRepository.existsByName(publisher.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Publisher with the same name already exists."));
        }

        if (publisherRepository.existsByWebsite(publisher.getWebsite())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Publisher with the same website already exists."));
        }

        Publisher savedPublisher = publisherRepository.save(publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
    }


    public ResponseEntity<Publisher> updatePublisher(Long id, Publisher publisher) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if (optionalPublisher.isPresent()) {
            Publisher existingPublisher = optionalPublisher.get();
            existingPublisher.setName(publisher.getName());
            existingPublisher.setWebsite(publisher.getWebsite());
            Publisher updatedPublisher = publisherRepository.save(existingPublisher);
            return ResponseEntity.ok(updatedPublisher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
