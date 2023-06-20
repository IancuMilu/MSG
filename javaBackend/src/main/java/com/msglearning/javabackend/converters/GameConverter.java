package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.to.GameNoPriceTO;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameConverter {

    public GameTO convertToTO(Game game) {
        return GameTO.builder()
                .id((game.getId()))
                .name(game.getName())
                .description(game.getDescription())
                .price(game.getPrice())
                .imageUrl(game.getImageUrl())
                .publishDate(game.getPublishDate())
                .genreNames(getGenreNames(game))
                .publisherName(game.getPublisher().getName())
                .build();
    }

    public Game convertToEntity(GameTO gameTO) {
        Game game = new Game();
        game.setId(gameTO.getId());
        game.setName(gameTO.getName());
        game.setDescription(gameTO.getDescription());
        game.setPrice(gameTO.getPrice());
        game.setImageUrl(gameTO.getImageUrl());
        game.setPublishDate(gameTO.getPublishDate());

        return game;
    }

    public List<GameTO> convertToTOList(List<Game> games) {
        return games.stream()
                .map(this::convertToTO)
                .collect(Collectors.toList());
    }

    private List<String> getGenreNames(Game game) {
        return game.getGameGenres().stream()
                .map(gameGenre -> gameGenre.getGenre().getName())
                .collect(Collectors.toList());
    }

    public GameNoPriceTO convertToNoPriceTO(Game game) {
        return GameNoPriceTO.builder()
                .name(game.getName())
                .description(game.getDescription())
                .imageUrl(game.getImageUrl())
                .publishDate(game.getPublishDate())
                .genreNames(getGenreNames(game))
                .publisherName(game.getPublisher().getName())
                .build();
    }

    public Game convertNoPriceTOToEntity(GameNoPriceTO gameNoPriceTO) {
        Game game = new Game();
        game.setName(gameNoPriceTO.getName());
        game.setDescription(gameNoPriceTO.getDescription());
        game.setImageUrl(gameNoPriceTO.getImageUrl());
        game.setPublishDate(gameNoPriceTO.getPublishDate());

        return game;
    }
}

