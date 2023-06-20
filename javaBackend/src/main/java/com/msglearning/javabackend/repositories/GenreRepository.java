package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    boolean existsByName(String name);
}
