package com.doodle.pollservice.controller;

import com.doodle.pollservice.domain.Poll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollControllerTest {

    private static final String host = "http://localhost";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String CREATED_ON = LocalDate.now().minusDays(4).format(FORMATTER);
    private static final ZoneId defaultZoneId = ZoneId.systemDefault();
    private static final Date CREATED_ON_DATE = Date.from(LocalDate.parse(CREATED_ON, FORMATTER).atStartOfDay(defaultZoneId).toInstant());
    private static final String POLL_3_USER_ID = "D9521A10-8013-402B-8AB9-95349FE35627";
    private static final String POLL_3_ID = "A0270442-2B60-42DA-AEC0-FA28FB71240D";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getPollsCreatedAfter() {
       final List<Poll> pollsCreatedAfter = Arrays.asList(
               this.testRestTemplate.getForObject(
                format("%s:%d/polls?createdAfter=%s", host, port, CREATED_ON),
                Poll[].class));
       assertThat(pollsCreatedAfter.size()).isEqualTo(2);
       assertThat(pollsCreatedAfter.stream().filter(this::createdOnBefore).collect(toList()).size()).isEqualTo(0);
    }

    @Test
    public void getPollsByUserId() {
        final List<Poll> pollsCreatedAfter = Arrays.asList(
                this.testRestTemplate.getForObject(
                        format("%s:%d/polls/user/%s", host, port, POLL_3_USER_ID),
                        Poll[].class));
        assertThat(pollsCreatedAfter.size()).isEqualTo(1);
        assertThat(pollsCreatedAfter.stream().findAny().get().getId()).isEqualTo(UUID.fromString(POLL_3_ID));
    }

    private boolean createdOnBefore(final Poll poll) {
        return poll.getCreatedOn().before(CREATED_ON_DATE);
    }

}
