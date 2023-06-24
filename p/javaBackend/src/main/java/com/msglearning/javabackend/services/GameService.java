package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.GameConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.GameGenre;
import com.msglearning.javabackend.entity.Genre;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.GenreRepository;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final GameConverter gameConverter;

    private final GenreRepository genreRepository;

    private final PurchaseRepository purchaseRepository;

    public GameService(GameRepository gameRepository, GameConverter gameConverter, GenreRepository genreRepository, PurchaseRepository purchaseRepository) {
        this.gameRepository = gameRepository;
        this.gameConverter = gameConverter;
        this.genreRepository = genreRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<GameTO> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return gameConverter.convertToTOList(games);
    }

//    public List<Game> getAllGamesRaw() {
//        return gameRepository.findAll();
//    }

    public ResponseEntity<GameTO> getGameById(Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            GameTO gameTO = gameConverter.toGameTO(game);
            return ResponseEntity.ok(gameTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean existsGameByName(String name) {
        return gameRepository.existsByName(name);
    }


    //TODO: saveGame should receive a Game object not GameTO
    public ResponseEntity<Game> saveGame(Game game) {
        if (game.getGameGenres() != null && !game.getGameGenres().isEmpty()) {
            // Create GameGenre entities for each genre ID
            List<GameGenre> gameGenres = new ArrayList<>();
            for (GameGenre gameGenre : game.getGameGenres()) {
                Genre genre = genreRepository.findById(gameGenre.getGenre().getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found"));
                gameGenre.setGame(game);
                gameGenre.setGenre(genre);
                gameGenres.add(gameGenre);
            }
            game.setGameGenres(gameGenres);
        }

        // Save the game with the associated GameGenre entities
        Game savedGame = gameRepository.save(game);
        return ResponseEntity.ok(savedGame);
    }



//    Asa arata body-ul pt un updateGame:

//    {
//            "id": 12,
//            "publisherId": 4,
//            "name": "Merge gameUpdate-ul",
//            "description": "Pentru toti parametrii!!!",
//            "price": 35.0,
//            "imageUrl": "link imagine(pt ca merge update-ul)",
//            "publishDate": "2023-02-01"
//
// }

    public ResponseEntity<GameTO> updateGame(Long id, Game game) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game existingGame = optionalGame.get();
            // Update game attributes
            existingGame.setName(game.getName());
            existingGame.setDescription(game.getDescription());
            existingGame.setImageUrl(game.getImageUrl());
            existingGame.setPublishDate(game.getPublishDate());
            existingGame.setPrice(game.getPrice());
            existingGame.setGameGenres(game.getGameGenres());
            //Convert game to gameTO
            Game updatedGame = gameRepository.save(existingGame);
            GameTO updatedGameTO = gameConverter.toGameTO(updatedGame);
            return ResponseEntity.ok(updatedGameTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @Transactional
//    public void updateGame(Long id, String name, String decription){
//
//    }

    public void deleteGame(Long id) {

        List<Purchase> purchases = purchaseRepository.findByGameId(id);
        for(Purchase purchase: purchases){
            gameRepository.deleteById(purchase.getId());
        }
        gameRepository.deleteById(id);
    }
}
