package com.msglearning.javabackend.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GameNoPriceTO implements Serializable {

    private String name;
    private String description;
    private String imageUrl;
    private LocalDate publishDate;
    private List<String> genreNames;
    private String publisherName;
}