package com.example.tracker.jpa.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {

    @Query(value = """
    select developer FROM DeveloperEntity developer
    """, countQuery = "select count(developer) FROM DeveloperEntity developer ")
    Page<DeveloperEntity> getAll(Pageable pageable);

}