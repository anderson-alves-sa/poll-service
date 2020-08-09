package com.doodle.pollservice.controller;

import com.doodle.pollservice.domain.Poll;
import com.doodle.pollservice.repository.PollRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollControllerTest {

    private static final String HOST = "http://localhost";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String CREATED_ON = LocalDate.now().minusDays(4).format(FORMATTER);
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
    private static final Date CREATED_ON_DATE = Date.from(LocalDate.parse(CREATED_ON, FORMATTER).atStartOfDay(DEFAULT_ZONE_ID).toInstant());

    private static final String POLL_1_ID = "F3F9F4E1-18B6-4618-9349-FAB66435793D";
    private static final String POLL_2_ID = "A169EFDB-1356-4A40-8055-0A1FFD792E6B";
    private static final String POLL_3_ID = "A0270442-2B60-42DA-AEC0-FA28FB71240D";
    private static final String POLL_4_ID = "8AFC2C5F-8722-4F53-85AA-BF429756689D";
    private static final String POLL_1_USER_EMAIL = "initiator1@email.com";
    private static final String POLL_2_USER_EMAIL = "initiator2@email.com";
    private static final String POLL_3_USER_EMAIL = "initiator3@email.com";
    private static final String POLL_1_TITLE = "Doodle 1";
    private static final String POLL_2_TITLE = "Doodle 2";
    private static final String POLL_3_TITLE = "Doodle 3";
    private static final String POLL_4_TITLE = "Doodle 4";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PollRepository pollRepository;

    @Test
    public void getPollsCreatedAfter() {
        final List<Poll> pollsCreatedAfter = Arrays.asList(
                this.testRestTemplate.getForObject(format("%s:%d/polls/?createdAfter=%s", HOST, port, CREATED_ON), Poll[].class));

        assertThat(pollsCreatedAfter.size()).isEqualTo(2);
        assertThat((int) pollsCreatedAfter.stream().filter(this::createdOnBefore).count()).isEqualTo(0);
    }

    @Test
    public void getPollsByUserEmail() {
        final List<Poll> pollsCreatedByUser = Arrays.asList(
                this.testRestTemplate.getForObject(format("%s:%d/polls/user/%s", HOST, port, POLL_3_USER_EMAIL), Poll[].class));

        assertThat(pollsCreatedByUser.size()).isEqualTo(1);
        assertThat(pollsCreatedByUser.stream().findAny().get().getId()).isEqualTo(POLL_3_ID);
    }

    @Test
    public void createPoll() {
        ResponseEntity<String> result = this.testRestTemplate.postForEntity(format("%s:%d/polls/", HOST, port),
                createNewPoll(POLL_4_ID, POLL_4_TITLE, 1, POLL_3_USER_EMAIL), String.class);

        final String newResourceLocation = result.getHeaders().get("Location").stream().findAny().get();
        final Poll poll = this.testRestTemplate.getForObject(newResourceLocation, Poll.class);

        assertThat(poll.getTitle()).isEqualTo(POLL_4_TITLE);
        assertThat(poll.getInitiator().getEmail()).isEqualTo(POLL_3_USER_EMAIL);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void deletePoll() {
        this.testRestTemplate.delete(format("%s:%d/polls/%s", HOST, port, POLL_1_ID));
        final ResponseEntity<String> responseEntity = this.testRestTemplate.getForEntity(format("%s:%d/polls/%s", HOST, port, POLL_1_ID), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getPollById() {
        final Poll poll = this.testRestTemplate.getForObject(format("%s:%d/polls/%s", HOST, port, POLL_1_ID), Poll.class);

        assertThat(poll.getId()).isEqualTo(POLL_1_ID);
        assertThat(poll.getTitle()).isEqualTo(POLL_1_TITLE);
        assertThat(poll.getInitiator().getEmail()).isEqualTo(POLL_1_USER_EMAIL);
    }

    @Test
    public void getPollByIdNotFound() {
        final ResponseEntity<String> response = this.testRestTemplate.getForEntity(format("%s:%d/polls/%s", HOST, port, POLL_1_ID + "ERR"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Object not found: [poll id [F3F9F4E1-18B6-4618-9349-FAB66435793DERR] not found]");
    }

    private boolean createdOnBefore(final Poll poll) {
        return poll.getCreatedOn().before(CREATED_ON_DATE);
    }

    private Poll createNewPoll(final String id, final String title, final int days, final String userEmail) {
        return new Poll(id, title, Date.from(LocalDate.now().minusDays(days).atStartOfDay(DEFAULT_ZONE_ID).toInstant()), userEmail);
    }

    @BeforeEach
    private void setTestData() {
        pollRepository.saveAll(List.of(
                createNewPoll(POLL_1_ID, POLL_1_TITLE, 5, POLL_1_USER_EMAIL),
                createNewPoll(POLL_2_ID, POLL_2_TITLE, 3, POLL_2_USER_EMAIL),
                createNewPoll(POLL_3_ID, POLL_3_TITLE, 1, POLL_3_USER_EMAIL)
        ));
    }

    @AfterEach
    private void removeTestData() {
        pollRepository.deleteAll();
    }

}
