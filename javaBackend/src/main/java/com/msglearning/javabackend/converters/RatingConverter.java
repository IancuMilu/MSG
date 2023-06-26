package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.to.RatingTO;
import org.springframework.stereotype.Component;

@Component
public class RatingConverter {

    public RatingTO toRatingTO(Rating rating){
        return RatingTO.builder()
                .rating(rating.getRating())
                .userName(rating.getUser().getFullName())
                .gameName(rating.getGame().getName())
                .build();
    }
}
