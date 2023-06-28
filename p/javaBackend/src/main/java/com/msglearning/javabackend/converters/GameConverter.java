package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameConverter {

    public GameTO toGameTO(Game game){
        return GameTO.builder()
                .id(game.getId())
                .name(game.getName())
                .description(game.getDescription())
                .price(game.getPrice())
                .imageUrl(game.getImageUrl())
                .publishDate(game.getPublishDate())
                .build();
    }


    public List<GameTO> convertToTOList(List<Game> games) {
        return games.stream()
                .map(this::toGameTO)
                .collect(Collectors.toList());
    }
}
