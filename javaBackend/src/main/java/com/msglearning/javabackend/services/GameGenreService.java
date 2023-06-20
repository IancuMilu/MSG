package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.GameGenre;
import com.msglearning.javabackend.repositories.GameGenreRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameGenreService {
    private final GameGenreRepository gameGenreRepository;

    public GameGenreService(GameGenreRepository gameGenreRepository) {
        this.gameGenreRepository = gameGenreRepository;
    }

    public List<Game> getGamesByGenreIdSortedByTitle(Long genreId, Sort sort) {
        List<GameGenre> gameGenres = gameGenreRepository.findByGenreId(genreId, sort);
        return gameGenres.stream().map(GameGenre::getGame).collect(Collectors.toList());
    }
}
