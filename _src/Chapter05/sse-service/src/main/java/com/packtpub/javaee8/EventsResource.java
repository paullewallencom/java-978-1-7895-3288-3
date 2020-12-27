package com.packtpub.javaee8;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.io.IOException;
import java.time.LocalDateTime;

@ApplicationScoped
@Path("events")
public class EventsResource {

    private volatile SseEventSink eventSink;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void openEventStream(@Context final SseEventSink eventSink) {
        this.eventSink = eventSink;
    }

    @POST
    public void sendEvent(String message, @Context Sse sse) {
        final SseEventSink localSink = eventSink;
        if (localSink == null) return;

        // send simple event
        OutboundSseEvent event = sse.newEvent(message);
        localSink.send(event);

        // send simple string event
        OutboundSseEvent stringEvent = sse.newEvent("stringEvent", message + " From server.");
        localSink.send(stringEvent);

        // send primitive long event using builder
        OutboundSseEvent primitiveEvent = sse.newEventBuilder()
                .name("primitiveEvent")
                .data(System.currentTimeMillis()).build();
        localSink.send(primitiveEvent);

        // send JSON-B marshalling to send event
        OutboundSseEvent jsonbEvent = sse.newEventBuilder()
                .name("jsonbEvent")
                .data(new JsonbSseEvent(message))
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .build();
        localSink.send(jsonbEvent);
    }

    @DELETE
    public void closeEventStream() throws IOException {
        final SseEventSink localSink = eventSink;
        if (localSink != null) {
            this.eventSink.close();
        }
        this.eventSink = null;
    }

    @JsonbPropertyOrder({"time", "message"})
    public static class JsonbSseEvent {
        String message;

        LocalDateTime today = LocalDateTime.now();

        public JsonbSseEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public LocalDateTime getToday() {
            return today;
        }

        public void setToday(LocalDateTime today) {
            this.today = today;
        }
    }
}
