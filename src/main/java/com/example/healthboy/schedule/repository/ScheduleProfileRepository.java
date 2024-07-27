package com.example.healthboy.schedule.repository;

import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.user.entity.Profile;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleProfileRepository extends JpaRepository<ScheduleProfile, Long> {
    ScheduleProfile findByScheduleAndProfile(Schedule schedule, Profile profile);

    @Query("SELECT sp FROM ScheduleProfile sp WHERE sp.schedule.url = :url AND sp.profile = :profile")
    ScheduleProfile findByScheduleUrlAndProfile(@Param("url") String url, @Param("profile") Profile profile);

    @Query("SELECT sp.schedule FROM ScheduleProfile sp WHERE sp.profile = :profile")
    List<Schedule> findSchedulesByProfile(@Param("profile") Profile profile);

    @Query("SELECT sp.profile FROM ScheduleProfile sp WHERE sp.schedule = :schedule")
    List<Profile> findProfilesBySchedule(@Param("schedule") Schedule schedule);

    boolean existsByScheduleAndProfile(Schedule schedule, Profile profile);

    long countBySchedule(Schedule schedule);

    @Modifying
    @Transactional
    @Query("DELETE FROM ScheduleProfile sp WHERE sp.schedule = :schedule")
    void deleteBySchedule(@Param("schedule") Schedule schedule);

    @Modifying
    @Transactional
    @Query("DELETE FROM ScheduleProfile sp WHERE sp.profile = :profile")
    void deleteByProfile(@Param("profile") Profile profile);
}
