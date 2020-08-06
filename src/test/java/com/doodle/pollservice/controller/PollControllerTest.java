package com.doodle.pollservice.controller;

import com.doodle.pollservice.domain.Poll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollControllerTest {

    private final static String host = "http://localhost";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getPollsCreatedAfter() throws Exception {
        this.testRestTemplate.getForObject(
                format("%s:%d/polls?createdAfter=%s", host, port, "02.02.2020"),
                List.class);
    }

}
