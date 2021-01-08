<%--
  Created by IntelliJ IDEA.
  User: Berikkali
  Date: 10.09.2020
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>

<html>
<script>
    function startTime() {
        var today = new Date();
        var h = today.getHours();
        var m = today.getMinutes();
        var s = today.getSeconds();
        m = checkTime(m);
        s = checkTime(s);
        document.getElementById('txt').innerHTML =
            h + ":" + m + ":" + s;
        var t = setTimeout(startTime, 500);
    }
    function checkTime(i) {
        if (i < 10) {i = "0" + i};
        return i;
    }
</script>
<head>
    <title>Header</title>
</head>
<body>
<div class="card-header d-flex justify-content-between">
    <h2>Welcome to the <%= application.getInitParameter("place")%>!</h2>
    <h2 id="txt"></h2>
</div>


