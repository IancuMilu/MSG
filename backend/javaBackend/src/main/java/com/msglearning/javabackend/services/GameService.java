package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.repositories.RatingRepository;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final RatingRepository ratingRepository;
    private final PurchaseRepository purchaseRepository;

    public GameService(GameRepository gameRepository, RatingRepository ratingRepository,
                       PurchaseRepository purchaseRepository) {
        this.gameRepository = gameRepository;
        this.ratingRepository = ratingRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
    
    public ResponseEntity<Game> getGameById(Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean existsGameByName(String name) {
        return gameRepository.existsByName(name);
    }

    public ResponseEntity<Game> saveGame(Game game) {
        Game newGame = gameRepository.save(game);
        return ResponseEntity.ok(newGame);
    }
    
    public ResponseEntity<Game> updateGame(Long id, Game newGame) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game existingGame = optionalGame.get();

            // Update game attributes if new values are not null
            if(newGame.getName() != null)
                existingGame.setName(newGame.getName());
            if(newGame.getDescription() != null)
                existingGame.setDescription(newGame.getDescription());
            if(newGame.getImageUrl() != null)
                existingGame.setImageUrl(newGame.getImageUrl());
            if(newGame.getPublishDate() != null)
                existingGame.setPublishDate(newGame.getPublishDate());
            if(newGame.getPrice() != null)
                existingGame.setPrice(newGame.getPrice());
            if(newGame.getGenres() != null)
                existingGame.setGenres(newGame.getGenres());
            if(newGame.getPublisherName() != null)
                existingGame.setPublisherName(newGame.getPublisherName());

            //Save the newGame
            Game updatedGame = gameRepository.save(existingGame);
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public String deleteGame(Long id) throws NotFoundException{
        //Check if game exists in table
        if(!gameRepository.existsById(id))
            throw new NotFoundException("Game id does not exist");

        //Delete purchases which contain the game
        List<Purchase> purchases = purchaseRepository.findByGameId(id);
        purchases.forEach(purchase -> purchaseRepository.deleteById(purchase.getId()));

        // Delete all ratings related to the game
        List<Rating> ratings = ratingRepository.findByGameId(id);
        for (Rating rating : ratings) {
            ratingRepository.deleteById(rating.getId());
        }

        //Delete the game
        gameRepository.deleteById(id);

        // Return success message
        return "Game deleted successfully.";
    }
}
