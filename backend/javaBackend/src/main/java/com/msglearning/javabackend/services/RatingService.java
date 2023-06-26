package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.RatingConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.RatingRepository;
import com.msglearning.javabackend.repositories.UserRepository;
import com.msglearning.javabackend.to.RatingTO;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;  // Assuming you have UserRepository
    private final GameRepository gameRepository;  // Assuming you have GameRepository
    private final RatingConverter ratingConverter;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository,
                         GameRepository gameRepository, RatingConverter ratingConverter) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.ratingConverter = ratingConverter;
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

    public ResponseEntity<RatingTO> saveRating(Rating rating) throws NotFoundException {

        User user = userRepository.findById(rating.getUser().getId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + rating.getUser()));
        Game game = gameRepository.findById(rating.getGame().getId())
                .orElseThrow(() -> new NotFoundException("Game not found with ID: " + rating.getGame()));

        rating.setUser(user);
        rating.setGame(game);
        rating.setRating(rating.getRating());

        Rating savedRating = ratingRepository.save(rating);

        RatingTO savedRatingTO = ratingConverter.toRatingTO(savedRating);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRatingTO);
    }

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
