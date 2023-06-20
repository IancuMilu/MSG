package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    boolean existsByName(String name);
}
