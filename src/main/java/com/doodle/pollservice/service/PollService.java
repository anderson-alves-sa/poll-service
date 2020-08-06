package com.doodle.pollservice.service;

import com.doodle.pollservice.domain.Poll;
import com.doodle.pollservice.repository.PollRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.util.Assert.notNull;

@Service
public final class PollService {

    private final PollRepository pollRepository;

    public PollService(final PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public List<Poll> getAllPollsCreatedAfter(final Date createdDate) {
        notNull(createdDate, "created date must not be null");
        return pollRepository.findByCreatedOnAfter(createdDate);
    }

}
