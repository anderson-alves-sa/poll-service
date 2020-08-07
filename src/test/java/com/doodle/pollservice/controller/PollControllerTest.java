package com.doodle.pollservice.controller;

import com.doodle.pollservice.domain.Poll;
import com.doodle.pollservice.repository.PollRepository;
import org.junit.jupiter.api.*;
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

    private static final String HOST = "http://localhost";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String CREATED_ON = LocalDate.now().minusDays(4).format(FORMATTER);
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    private static final Date CREATED_ON_DATE = Date.from(LocalDate.parse(CREATED_ON, FORMATTER).atStartOfDay(DEFAULT_ZONE_ID).toInstant());
    private static final String POLL_3_USER_ID = "D9521A10-8013-402B-8AB9-95349FE35627";
    private static final String POLL_3_ID = "A0270442-2B60-42DA-AEC0-FA28FB71240D";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PollRepository pollRepository;

    @BeforeEach
    private void setTestData() {
        pollRepository.saveAll(List.of(
                new Poll("F3F9F4E1-18B6-4618-9349-FAB66435793D", "Doodle 1", Date.from(LocalDate.now().minusDays(5).atStartOfDay(DEFAULT_ZONE_ID).toInstant()), UUID.fromString("EB206FE4-D79E-4085-9B82-1B83E9599F20")),
                new Poll("A169EFDB-1356-4A40-8055-0A1FFD792E6B", "Doodle 2", Date.from(LocalDate.now().minusDays(3).atStartOfDay(DEFAULT_ZONE_ID).toInstant()), UUID.fromString("DC1E240A-16EE-44A3-BAAD-5077401E6924")),
                new Poll("A0270442-2B60-42DA-AEC0-FA28FB71240D", "Doodle 3", Date.from(LocalDate.now().minusDays(1).atStartOfDay(DEFAULT_ZONE_ID).toInstant()), UUID.fromString("D9521A10-8013-402B-8AB9-95349FE35627"))
        ));
    }

    @AfterEach
    private void removeTestData() {
        pollRepository.deleteAll();
    }

    @Test
    public void getPollsCreatedAfter() {
       final List<Poll> pollsCreatedAfter = Arrays.asList(
               this.testRestTemplate.getForObject(
                format("%s:%d/polls?createdAfter=%s", HOST, port, CREATED_ON),
                Poll[].class));
       assertThat(pollsCreatedAfter.size()).isEqualTo(2);
       assertThat(pollsCreatedAfter.stream().filter(this::createdOnBefore).collect(toList()).size()).isEqualTo(0);
    }

    @Test
    public void getPollsByUserId() {
        final List<Poll> pollsCreatedAfter = Arrays.asList(
                this.testRestTemplate.getForObject(
                        format("%s:%d/polls/user/%s", HOST, port, POLL_3_USER_ID),
                        Poll[].class));
        assertThat(pollsCreatedAfter.size()).isEqualTo(1);
        assertThat(pollsCreatedAfter.stream().findAny().get().getId()).isEqualTo(POLL_3_ID);
    }

    private boolean createdOnBefore(final Poll poll) {
        return poll.getCreatedOn().before(CREATED_ON_DATE);
    }

}
