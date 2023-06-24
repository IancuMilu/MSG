package com.msglearning.javabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = Genre.TABLE_NAME)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    static final String TABLE_NAME = "genre";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}