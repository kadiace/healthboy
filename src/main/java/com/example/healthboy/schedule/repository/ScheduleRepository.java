package com.example.healthboy.schedule.repository;

import com.example.healthboy.schedule.entity.Schedule;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    Schedule findByUrl(String url);

    @Transactional
    @Modifying
    @Query(value = "UPDATE schedule s1_0 " +
            "LEFT JOIN schedule_profile sp1_0 ON s1_0.url = sp1_0.schedule_url " +
            "SET s1_0.deleted_at = :now " +
            "WHERE s1_0.url IN :scheduleUrls " +
            "AND s1_0.deleted_at IS NULL " +
            "AND sp1_0.id IS NULL", nativeQuery = true)
    void deleteSchedulesWithNoProfiles(@Param("scheduleUrls") List<String> scheduleUrls,
            @Param("now") LocalDateTime now);
}
