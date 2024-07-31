package com.example.healthboy.timeblock.repository;

import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.timeblock.entity.TimeBlock;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Long> {

    @EntityGraph(value = "TimeBlock.profile", type = EntityGraph.EntityGraphType.LOAD)
    Optional<TimeBlock> findById(Long id);

    @EntityGraph(value = "TimeBlock.profile", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT tb FROM TimeBlock tb WHERE tb.scheduleProfile = :scheduleProfile")
    List<TimeBlock> findBySP(@Param("scheduleProfile") ScheduleProfile scheduleProfile);

    @EntityGraph(value = "TimeBlock.profile", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT tb FROM TimeBlock tb WHERE tb.scheduleProfile = :scheduleProfile AND (tb.endTime > :startTime OR tb.startTime < :endTime)")
    List<TimeBlock> findBySPAndTimeRange(@Param("scheduleProfile") ScheduleProfile scheduleProfile,
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);

    @Query("SELECT count(tb) FROM TimeBlock tb WHERE tb.scheduleProfile = :scheduleProfile AND (tb.endTime > :startTime OR tb.startTime < :endTime)")
    long countBySPAndTimeRange(@Param("scheduleProfile") ScheduleProfile scheduleProfile,
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);

}
