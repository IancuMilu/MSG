package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    //List<Publisher> findAll();    //is already provided by the JpaRepository interface

    boolean existsByName(String name);

    boolean existsByWebsite(String website);
}
