# Building Web Services with Java EE 8 <br>Section 3: Using Server-sent Events (SSE)

## Videos

### Video 5.1: What are Server-Sent Events?

In this video we are talking about the benefits and possible usage scenarios of Server-sent events.

### Video 5.2: Implementing SSE on the server-side

In this video we are implementing simple asynchronous web services.

| Method  | URI | Media Type | Description |
|---------|-----|--------|-------------|
| GET     | /api/events  | text/event-stream | Get and open the SSE stream to receive push events |
| POST    | /api/events  | */* | Post any message to be pushed to an open SSE stream |
| DELETE  | /api/events  | */* | Close any open SSE event stream |

### Video 5.3: Implementing SSE REST clients

In this video we are showing how to implement a JAX-RS REST client sending and receiving SSE.

### Video 5.4: Implementing and sending SSE broadcasts

In this video is showing how to subscribe and broadcast SSE events to all registered clients.

| Method | URI | Media Type | Description |
|--------|-----|--------|-------------|
| GET    | /api/broadcast | text/event-stream | Get and open the SSE stream of broadcasted messages |
| POST   | /api/broadcast | application/x-www-form-urlencoded | Send a broadcast message |


## Building and Running

```bash
$ mvn clean verify

$ docker build -t sse-service:1.0 .
$ docker run -it -p 8080:8080 sse-service:1.0
```
