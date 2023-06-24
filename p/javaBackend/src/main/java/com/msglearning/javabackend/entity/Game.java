package com.msglearning.javabackend.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @OneToMany(mappedBy = "game")
    private List<GameGenre> gameGenres;
}

