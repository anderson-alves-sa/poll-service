package com.doodle.pollservice.controller;

import com.doodle.pollservice.domain.Poll;
import com.doodle.pollservice.service.PollService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

@RestController
@RequestMapping("polls")
public final class PollController {

    private final static Logger LOGGER = Logger.getLogger(PollController.class.getName());
    private final PollService pollService;

    public PollController(final PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/")
    public List<Poll> getPollsCreatedAfter(@RequestParam(name = "createdAfter") @DateTimeFormat(pattern = "dd.MM.yyyy") final Date createdAfter) {
        LOGGER.info(format("searching polls created after: %s", createdAfter));
        return pollService.getPollsCreatedAfter(createdAfter);
    }

    @GetMapping("/user/{initiatorEmail}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Poll> getPollsByUserEmail(@PathVariable(value = "initiatorEmail") final String initiatorEmail) {
        LOGGER.info(format("searching polls created by user: %s", initiatorEmail));
        return pollService.getPollsByUserEmail(initiatorEmail);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Poll getPollById(@PathVariable(value = "id") final String id) {
        LOGGER.info(format("fetching poll with id %s", id));
        return pollService.getPollById(id);
    }

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity createPoll(@RequestBody final Poll poll) {
        LOGGER.info(format("creating new poll: %s", poll));
        pollService.createPoll(poll);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePoll(@PathVariable(value = "id") final String id) {
        LOGGER.info(format("deleting poll: %s", id));
        pollService.deletePoll(id);
    }

}
