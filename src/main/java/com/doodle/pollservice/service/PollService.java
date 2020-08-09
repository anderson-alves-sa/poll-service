package com.doodle.pollservice.service;

import com.doodle.pollservice.domain.Poll;
import com.doodle.pollservice.repository.PollRepository;
import com.doodle.pollservice.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.util.Assert.notNull;

@Service
public final class PollService {

    private final PollRepository pollRepository;

    public PollService(final PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public List<Poll> getPollsCreatedAfter(final Date createdDate) {
        notNull(createdDate, "created date must not be null");
        return pollRepository.findByCreatedOnAfter(createdDate);
    }

    public List<Poll> getPollsByUserEmail(final String initiatorEmail) {
        notNull(initiatorEmail, "initiator email must not be null");
        return pollRepository.findAllByInitiatorEmail(initiatorEmail);
    }

    public void createPoll(final Poll poll) {
        notNull(poll, "poll must not be null");
        poll.setId(UUID.randomUUID().toString());
        poll.setCreatedOn(new Date());
        pollRepository.save(poll);
    }

    public void deletePoll(final String pollId) {
        notNull(pollId, "poll id must not be null");
        pollRepository.deleteById(pollId);
    }

    public Poll getPollById(final String id) {
        notNull(id, "poll id must not be null");
        return pollRepository.findById(id).orElseThrow(() -> new NotFoundException(format("poll id [%s] not found", id)));
    }

}
