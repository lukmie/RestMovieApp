package com.lukmie.restmovieapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerIT {

    @LocalServerPort
    int randomServerPort;
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void deletingTwoTimesSameEntityShouldReturnError404() {
        long movieId = 1L;
        String baseUrl = "http://localhost:" + randomServerPort;

        ResponseEntity<JsonNode> firsResult = testRestTemplate.getForEntity(baseUrl + "/api/movies/" + movieId, JsonNode.class);
        assertThat(firsResult.getStatusCode(), is(HttpStatus.OK));

        ResponseEntity<JsonNode> secondResult = testRestTemplate.getForEntity(baseUrl + "/api/movies/" + movieId, JsonNode.class);
        assertThat(secondResult.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}
