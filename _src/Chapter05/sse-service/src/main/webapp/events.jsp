<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Simple SSE events demo</title>
</head>
<body>

<h2>Messages</h2>
<div id="messages"></div>

<h2>Named String Events</h2>
<div id="stringEvents"></div>

<h2>Named Long Events</h2>
<div id="primitiveEvents"></div>

<h2>Named JSON-B Events</h2>
<div id="jsonbEvents"></div>

<script>
    if (typeof(EventSource) !== "undefined") {
        var source = new EventSource("http://localhost:8080/sse-service/api/events");
        source.onmessage = function (event) {
            document.getElementById("messages").innerHTML += event.data + "<br>";
        };

        source.addEventListener("stringEvent", function (e) {
            document.getElementById("stringEvents").innerHTML += e.data + "<br>";
        }, false);

        source.addEventListener("primitiveEvent", function (e) {
            document.getElementById("primitiveEvents").innerHTML += e.data + "<br>";
        }, false);

        source.addEventListener("jsonbEvent", function (e) {
            document.getElementById("jsonbEvents").innerHTML += e.data + "<br>";
        }, false);
    } else {
        document.getElementById("jsonbEvents").innerHTML = "Sorry, your browser does not support server-sent events...";
    }
</script>
</body>
</html>
