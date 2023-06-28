package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.services.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_PATH_GAME)
public class GameController {
    private static final String ID_PATH = "/{id}";

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/all")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @PostMapping
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        if (gameService.existsGameByName(game.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


        return gameService.saveGame(game);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game newGame) {
        return gameService.updateGame(id, newGame);
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<String> deleteGame(@PathVariable Long id) throws Exception {
        String response = gameService.deleteGame(id);
        return ResponseEntity.ok(response);
    }
}
