package com.berlinclock.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/** Starts the whole application on a random port and checks that the documentation is reachable. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SwaggerUiTest {

    @Value("${local.server.port}")
    private int port;

    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    @Test
    void servesTheContractFile() throws Exception {
        HttpResponse<String> response = get("/berlin-clock.yaml");

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("title: Berlin Clock API"));
    }

    @Test
    void swaggerUiIsReachableAtSwaggerUiHtml() throws Exception {
        HttpResponse<String> response = get("/swagger-ui.html");

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Swagger UI"));
    }

    private HttpResponse<String> get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
