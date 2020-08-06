package com.doodle.pollservice.controller;

import com.doodle.pollservice.domain.Poll;
import com.doodle.pollservice.service.PollService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static java.lang.String.format;

@RestController
public final class PollController {

    private final static Logger LOGGER = Logger.getLogger(PollController.class.getName());
    private final PollService pollService;

    public PollController(final PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/polls")
    public List<Poll> getPollsCreatedAfter(
            @RequestParam(name = "createdAfter")
            @DateTimeFormat(pattern = "dd.MM.yyyy")
            final Date createdAfter) {
        LOGGER.info(format("searching polls created after: %s", createdAfter));
        return pollService.getPollsCreatedAfter(createdAfter);
    }

    @GetMapping("/polls/user/{id}")
    public List<Poll> getPollsByUserId(@PathVariable(value = "id") final UUID userId) {
        LOGGER.info(format("searching polls created by user: %s", userId));
        return pollService.getPollsByUserId(userId);
    }

}
