package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.repositories.RatingRepository;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public double getAverageRatingForGame(Long gameId) {
        List<Rating> ratings = ratingRepository.findByGameId(gameId);
        int totalRatings = ratings.size();
        if (totalRatings == 0) {
            return 0.0;
        }
        double sumRatings = ratings.stream().mapToInt(Rating::getRating).sum();
        return sumRatings / totalRatings;
    }

    public Optional<Rating> getRatingForGameByUser(Long gameId, Long userId) {
        return ratingRepository.findByGameIdAndUserId(gameId, userId);
    }

    public ResponseEntity<Rating> saveRating(Rating rating) {
        Rating savedRating = ratingRepository.save(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long id, @RequestBody Rating updatedRating) throws NotFoundException {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rating not found with ID: " + id));

        existingRating.setRating(updatedRating.getRating());

        Rating savedRating = ratingRepository.save(existingRating);

        return new ResponseEntity<>(savedRating, HttpStatus.OK);
    }

    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}
