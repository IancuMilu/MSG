package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.repositories.RatingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<Rating> updateRating(Long id, Rating rating) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            Rating existingRating = optionalRating.get();
            // Update rating value
            existingRating.setRating(rating.getRating());
            Rating updatedRating = ratingRepository.save(existingRating);
            return ResponseEntity.ok(updatedRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}
