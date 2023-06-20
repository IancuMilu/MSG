package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.services.RatingService;
import com.msglearning.javabackend.to.RatingTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(ControllerConstants.API_PATH_RATING)
public class RatingController {
    private static final String ID_PATH = "/{id}";

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/game/{gameId}/average")
    public double getAverageRatingForGame(@PathVariable Long gameId) {
        return ratingService.getAverageRatingForGame(gameId);
    }

    @GetMapping("/game/{gameId}/user/{userId}")
    public ResponseEntity<RatingTO> getRatingForGameByUser(@PathVariable Long gameId, @PathVariable Long userId) {
        Optional<Rating> rating = ratingService.getRatingForGameByUser(gameId, userId);
        if (rating.isPresent()) {
            RatingTO ratingTO = RatingTO.builder()
                    .rating(rating.get().getRating())
                    .build();
            return ResponseEntity.ok(ratingTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating) {
        return ratingService.saveRating(rating);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<Rating> updateRating(@PathVariable Long id, @RequestBody Rating rating) {
        return ratingService.updateRating(id, rating);
    }

    @DeleteMapping(ID_PATH)
    public void deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
    }
}
