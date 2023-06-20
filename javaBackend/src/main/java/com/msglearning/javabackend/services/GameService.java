package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.GameConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameConverter gameConverter;

    public GameService(GameRepository gameRepository, GameConverter gameConverter) {
        this.gameRepository = gameRepository;
        this.gameConverter = gameConverter;
    }

    public List<GameTO> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return gameConverter.convertToTOList(games);
    }

    public ResponseEntity<GameTO> getGameById(Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            GameTO gameTO = gameConverter.convertToTO(game);
            return ResponseEntity.ok(gameTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean existsGameByName(String name) {
        return gameRepository.existsByName(name);
    }

    public ResponseEntity<GameTO> saveGame(GameTO gameTO) {
        Game game = gameConverter.convertToEntity(gameTO);
        Game savedGame = gameRepository.save(game);
        GameTO savedGameTO = gameConverter.convertToTO(savedGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGameTO);
    }

    public ResponseEntity<GameTO> updateGame(Long id, GameTO gameTO) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game existingGame = optionalGame.get();
            // Update game attributes
            existingGame.setName(gameTO.getName());
            existingGame.setDescription(gameTO.getDescription());
            existingGame.setImageUrl(gameTO.getImageUrl());
            existingGame.setPublishDate(gameTO.getPublishDate());
            Game updatedGame = gameRepository.save(existingGame);
            GameTO updatedGameTO = gameConverter.convertToTO(updatedGame);
            return ResponseEntity.ok(updatedGameTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}

