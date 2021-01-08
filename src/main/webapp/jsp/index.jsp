<%@ page import="domain.Reader" %><%--
  Created by IntelliJ IDEA.
  User: Berikkali
  Date: 16.10.2020
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title>Index page</title>
</head>
<body onload="startTime()">
    <%@include file="header.jsp"%>
    <%-- Two form: new and registred users --%>
    <div class="container" style="height: calc(100vh - 200px); display: grid; align-items: center;">
        <div class="row">
            <div class="col">
                <h2>Registration: </h2>
                <form method="get" action="register">
                   Enter Name: <input class="form-control" type="text" name="username"><br>
                   Enter Password: <input class="form-control" type="password" name="password"><br>
                    <input class="btn btn-success" type="submit" value="Register">
                </form>
            </div>

            <div class="col">
                <h2>Login: </h2>
                <form method="post" action="register">
                    Name: <input class="form-control" type="text" name="usernameLog"><br>
                    Password: <input class="form-control" type="password" name="passwordLog"><br>
                    <input class="btn btn-primary" type="submit" value="Login">
                </form>
            </div>
        </div>
    </div>

    <%@include file="footer.jsp"%>
</body>
</html>

<%-- TEST IF LOGOUT WORK AND SESSION WAS DESTROYED --%>
<%
    if(session.getAttribute("signedUser") != null){
        Reader reader = (Reader) session.getAttribute("signedUser");
        System.out.println(reader.getName() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
%>
<%-- TEST IF LOGOUT WORK AND SESSION WAS DESTROYED --%>
