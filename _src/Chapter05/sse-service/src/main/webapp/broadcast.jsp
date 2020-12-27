<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Simple SSE broadcast</title>
</head>
<body>

<form action="/sse-service/api/broadcast" method="post">
    Message <input type="text" name="message"/>
    <input type="submit" value="Submit"/>
</form>

<h2>Chat messages</h2>
<div id="messages"></div>

<script>
    if (typeof(EventSource) !== "undefined") {
        var source = new EventSource("http://localhost:8080/sse-service/api/broadcast");

        source.addEventListener("message", function (e) {
            document.getElementById("messages").innerHTML += e.data + "<br>";
        }, false);
    } else {
        document.getElementById("messages").innerHTML = "Sorry, your browser does not support server-sent events...";
    }
</script>
</body>
</html>
