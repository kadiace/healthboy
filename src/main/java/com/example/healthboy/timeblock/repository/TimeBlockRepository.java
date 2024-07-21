package com.example.healthboy.timeblock.repository;

import com.example.healthboy.timeblock.entity.TimeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Long> {
}
