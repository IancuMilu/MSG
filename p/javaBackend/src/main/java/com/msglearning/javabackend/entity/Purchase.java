package com.msglearning.javabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Table(name = Purchase.TABLE_NAME)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    static final String TABLE_NAME = "purchase";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;


    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", user=" + user +
                ", game=" + game +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
