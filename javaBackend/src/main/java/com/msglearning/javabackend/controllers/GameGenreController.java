package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.services.GameGenreService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_PATH_GAME_GENRE)
public class GameGenreController {
    private final GameGenreService gameGenreService;

    public GameGenreController(GameGenreService gameGenreService) {
        this.gameGenreService = gameGenreService;
    }

    @GetMapping("/genre/{genreId}/games")
    public List<Game> getGamesByGenreIdSortedByTitle(@PathVariable Long genreId, @RequestParam(defaultValue = "title") String sortBy) {
        Sort sort = Sort.by(sortBy);
        return gameGenreService.getGamesByGenreIdSortedByTitle(genreId, sort);
    }
}
