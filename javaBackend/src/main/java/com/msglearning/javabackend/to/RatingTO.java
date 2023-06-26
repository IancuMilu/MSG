package com.msglearning.javabackend.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RatingTO {

    private int rating;
    private String userName;
    private String gameName;
}
