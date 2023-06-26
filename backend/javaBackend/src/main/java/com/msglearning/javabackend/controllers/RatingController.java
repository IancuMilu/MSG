package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.converters.RatingConverter;
import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.services.RatingService;
import com.msglearning.javabackend.to.RatingTO;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(ControllerConstants.API_PATH_RATING)
public class RatingController {
    private static final String ID_PATH = "/{id}";

    private final RatingService ratingService;

    private final RatingConverter ratingConverter;

    public RatingController(RatingService ratingService, RatingConverter ratingConverter) {
        this.ratingService = ratingService;
        this.ratingConverter = ratingConverter;
    }

    @GetMapping("/game/{gameId}/average")
    public double getAverageRatingForGame(@PathVariable Long gameId) {
        return ratingService.getAverageRatingForGame(gameId);
    }

    @GetMapping("/game/{gameId}/user/{userId}")
    public ResponseEntity<RatingTO> getRatingForGameByUser(@PathVariable Long gameId, @PathVariable Long userId) {
        Optional<Rating> rating = ratingService.getRatingForGameByUser(gameId, userId);
        if (rating.isPresent()) {
            RatingTO ratingTO = ratingConverter.toRatingTO(rating.get());
            return ResponseEntity.ok(ratingTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RatingTO> saveRating(@RequestBody Rating rating) throws NotFoundException {
        return ratingService.saveRating(rating);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<Rating> updateRating(@PathVariable Long id, @RequestBody Rating rating) throws NotFoundException {
        return ratingService.updateRating(id, rating);
    }

    @DeleteMapping(ID_PATH)
    public void deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
    }
}
