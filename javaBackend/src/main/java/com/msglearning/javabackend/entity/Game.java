package com.msglearning.javabackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @JsonIgnore
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

