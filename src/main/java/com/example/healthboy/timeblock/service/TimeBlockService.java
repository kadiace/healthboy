package com.example.healthboy.timeblock.service;

import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.timeblock.repository.TimeBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class TimeBlockService {

    @Autowired
    private TimeBlockRepository timeBlockRepository;

    @Transactional
    public TimeBlock createTimeBlock(TimeBlock timeBlock) {
        return timeBlockRepository.save(timeBlock);
    }

    public List<TimeBlock> getAllTimeBlocks() {
        return timeBlockRepository.findAll();
    }

    @Transactional
    public void deleteTimeBlock(Long id) {
        timeBlockRepository.deleteById(id);
    }
}
