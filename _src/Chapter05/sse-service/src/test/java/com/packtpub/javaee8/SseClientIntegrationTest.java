package com.packtpub.javaee8;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseEventSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SseClientIntegrationTest {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private Client client;
    private WebTarget webTarget;

    private ScheduledExecutorService executorService;

    @Before
    public void setUp() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                .build();

        webTarget = client.target("http://localhost:8080").path("/sse-service/api/events");
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @After
    public void tearDown() {
        client.close();
        executorService.shutdown();
    }

    @Test
    public void receiveSse() throws Exception {

        executorService.scheduleWithFixedDelay(() -> {
            webTarget.request().post(Entity.entity("Hello SSE JAX-RS client.", MediaType.TEXT_PLAIN_TYPE));
        }, 250, 500, TimeUnit.MILLISECONDS);

        try (SseEventSource eventSource = SseEventSource.target(webTarget).build()) {
            eventSource.register((e) -> LOGGER.log(Level.INFO, "Recieved event {0} with data {1}.",
                    new Object[]{e.getName(), e.readData()}));
            eventSource.open();

            TimeUnit.SECONDS.sleep(3);
        }
    }
}
