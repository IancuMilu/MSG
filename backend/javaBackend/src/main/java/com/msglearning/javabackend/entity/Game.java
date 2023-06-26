package com.msglearning.javabackend.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table(name = Game.TABLE_NAME)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    static final String TABLE_NAME = "game";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String genres;

    @Column(name = "publisher_name")
    private String publisherName;

    @Column
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "publish_date")
    private LocalDate publishDate;
}

