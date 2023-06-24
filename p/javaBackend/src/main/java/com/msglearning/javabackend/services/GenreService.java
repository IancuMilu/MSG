package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Genre;
import com.msglearning.javabackend.repositories.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public ResponseEntity<Genre> getGenreById(Long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        return optionalGenre.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Genre> saveGenre(Genre genre) {
        if (genre.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (genreRepository.existsByName(genre.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict if name already exists
        }

        Genre savedGenre = genreRepository.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
    }

    public ResponseEntity<Genre> updateGenre(Long id, Genre genre) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isPresent()) {
            Genre existingGenre = optionalGenre.get();
            existingGenre.setName(genre.getName());
            Genre updatedGenre = genreRepository.save(existingGenre);
            return ResponseEntity.ok(updatedGenre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
}
