package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.converters.GameConverter;
import com.msglearning.javabackend.services.GameService;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_PATH_GAME)
public class GameController {
    private static final String ID_PATH = "/{id}";

    private final GameService gameService;
    private final GameConverter gameConverter;

    public GameController(GameService gameService, GameConverter gameConverter) {
        this.gameService = gameService;
        this.gameConverter = gameConverter;
    }

    @GetMapping("/all")
    public List<GameTO> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<GameTO> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @PostMapping
    public ResponseEntity<GameTO> saveGame(@RequestBody GameTO gameTO) {
        if (gameService.existsGameByName(gameTO.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return gameService.saveGame(gameTO);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<GameTO> updateGame(@PathVariable Long id, @RequestBody GameTO gameTO) {
        return gameService.updateGame(id, gameTO);
    }

    @DeleteMapping(ID_PATH)
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
