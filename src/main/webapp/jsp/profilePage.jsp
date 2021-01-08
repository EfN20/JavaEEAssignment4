<%@ page import="java.util.List" %>
<%@ page import="domain.Book" %>
<%@ page import="domain.GenericClassList" %>
<%@ page import="domain.Reader" %>
<%@ page import="java.util.LinkedList" %><%--
  Created by IntelliJ IDEA.
  User: Berikkali
  Date: 01.11.2020
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title>Profile page</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="d-flex justify-content-center align-items-center my-2">
            <h1 style="width: auto">Profile page of <c:out value="${signedUser.name}" />
                [<c:if test="${!signedUser.isLibrarian}">Reader</c:if><c:if test="${signedUser.isLibrarian}">Librarian</c:if>]</h1>
            <a class="btn btn-dark btn-sm mx-2" href="logout" role="button" style="height: 30px">Log out</a>
            <a class="btn btn-dark btn-sm mx-2" href="profile" role="button" style="height: 30px">Books page</a>
        </div>

        <c:forEach var="book" items="${borrowedBooks}">
            <div class="card mb-3 my-2 mx-auto" style="max-width: 720px;">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="<c:out value="${book.url_img}"/>" class="card-img" alt="DEADLINES DUSHAT">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title nameBook"><c:out value="${book.name}"/></h5>
                            <p class="card-text authorBook"><c:out value="${book.author}"/></p>
                            <p class="card-text">ISBN: <c:out value="${book.ISBN}"/></p>
                            <p class="card-text countBook"> Count of copies: <c:out value="${book.count}"/></p>
                            <form method="post">
                                <input class="btn btn-dark deleteBorrowed" type="submit" value="Delete from borrowed list">
                                <input hidden class="bookIsbn" name="bookIsbn" value="<c:out value="${book.ISBN}"/>">
                                <input hidden class="readerId" name="readerId" value="<c:out value="${signedUser.id}"/>">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${signedUser.isLibrarian}">
            <c:forEach var="reader" items="${readers.list}">
                <%-- user card --%>
                <div class="card mb-3 my-2 mx-auto" style="max-width: 720px;">
                    <div class="row no-gutters">
                        <div class="col-md-8">
                            <div class="card-body">
                                <form method="post">
                                    <p class="card-text">ID of reader: <input type="text" class="card-name addReaderId" value="<c:out value="${reader.id}"/>" disabled></p>
                                    <p class="card-text">Name of reader: <input type="text" class="card-name addReaderName" value="<c:out value="${reader.name}"/>"></p>
                                    <p class="card-text">Password: <input type="text" class="card-text addReaderPass" value="<c:out value="${reader.password}"/>"/></p>
                                    <p class="card-text">Role: <input type="text" class="card-text addReaderRole" value="<c:if test="${!reader.isLibrarian}">Reader</c:if><c:if test="${reader.isLibrarian}">Librarian</c:if>"/> [Librarian | Reader]</p>
                                    <input class="btn btn-dark updReader" type="submit" value="Update user">
                                    <input class="btn btn-dark deleteReader" type="submit" value="Delete user"><br>
                                    <form method="post">
                                        <select class="custom-select addIsbn" style="width: 100px;">
                                            <option selected>ISBN</option>
                                            <c:forEach var="ct" items="${available.stack}">
                                                <c:if test="${ct.count > 0}">
                                                    <option value="<c:out value="${ct.ISBN}"/>"><c:out value="${ct.ISBN}"/></option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <input class="btn btn-dark forceBorrowed" type="submit" value="Add to borrowed list">
                                        <input hidden class="readerId" name="readerId" value="<c:out value="${reader.id}"/>">
                                    </form>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </c:if>
    </div>
</body>
</html>
<script>
    $(document).ready(function(){
        //delete book from borrowed
        $('.deleteBorrowed').click(function(event){
            event.preventDefault();
            var button = $(this);
            var bookIsbn;
            var readerId;
            $(this).siblings().each(function(){
                if($(this).hasClass("bookIsbn")){
                    bookIsbn = $(this).val();
                    console.log(bookIsbn);
                }
                if($(this).hasClass("readerId")){
                    readerId = $(this).val();
                    console.log(readerId);
                }
            });
            $.ajax({
                url: "rest/book/borrowed_remove/" + readerId + "/" + bookIsbn,
                type: "POST",
                success: function(message){
                    console.log(message);
                    if(message == "Book was removed from list successfully!"){
                        // button.val(message);
                        button.closest('.card').remove();
                    }
                }
            });
        });
        //delete book from borrowed

        //update reader data
        $('.updReader').click(function(event){
            event.preventDefault();
            var button2 = $(this);
            var updId;
            var updName;
            var updPassword;
            var updRole;
            var card = button2.closest('.card-body');
            updId = card.find('.addReaderId').val();
            updName = card.find('.addReaderName').val();
            updPassword = card.find('.addReaderPass').val();
            updRole = card.find('.addReaderRole').val();
            var admin;
            if(updRole == "Librarian"){
                admin = "true"
            }
            else if (updRole == "Reader"){
                admin = "false"
            }
            var updReader = {id: updId, name: updName, password: updPassword, isLibrarian: admin};
            console.log(updReader);
            var jsonReader = JSON.stringify(updReader);
            console.log(jsonReader);
            $.ajax({
                url: "rest/reader/update",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: jsonReader,
                success: function(message){
                    if(message == "Reader was updated successfully!"){
                        $.ajax({
                            url: "rest/reader/" + updId,
                            type: "GET",
                            success: function(responseJson){
                                console.log(responseJson);
                                var card = button2.closest('.card-body');
                                card.find('.addReaderName').val(responseJson.name);
                                card.find('.addReaderPass').val(responseJson.password);
                                if(responseJson.isLibrarian == "true"){
                                    card.find('.addReaderRole').val("Librarian")
                                }
                                else if(responseJson.isLibrarian == "false"){
                                    card.find('.addReaderRole').val("Reader")
                                }
                                button2.val(message);
                            }
                        });
                    }
                }
            });
        });
        //update reader data

        //delete reader data
        $('.deleteReader').click(function(event){
            event.preventDefault();
            var button3 = $(this);
            var deleteId;
            var deleteName;
            var deletePassword;
            var deleteRole;
            $(this).siblings().each(function(){
                if($(this).hasClass("addReaderName")){
                    deleteName = $(this).val();
                    console.log(deleteName);
                }
                if($(this).hasClass("addReaderPass")){
                    deletePassword = $(this).val();
                    console.log(deletePassword);
                }
                if($(this).hasClass("addReaderRole")){
                    deleteRole = $(this).val();
                    console.log(deleteRole);
                }
                if($(this).hasClass("addReaderId")){
                    deleteId = $(this).val();
                    console.log(deleteId);
                }
            });
            var card2 = button3.closest('.card-body');
            deleteId = card2.find('.addReaderId').val();
            deleteName = card2.find('.addReaderName').val();
            deletePassword = card2.find('.addReaderPass').val();
            deleteRole = card2.find('.addReaderRole').val();
            var admin3;
            if(deleteRole == "Librarian"){
                admin3 = "true"
            }
            else if (deleteRole == "Reader"){
                admin3 = "false"
            }
            var deleteReader = {id: deleteId, name: deleteName, password: deletePassword, isLibrarian: admin3};
            var jsonReader3 = JSON.stringify(deleteReader);
            $.ajax({
                url: "rest/reader/remove",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: jsonReader3,
                success: function(message){
                    console.log(message);
                    if(message == "Reader was removed successfully!"){
                        button3.closest('.card').remove();
                    }
                    else if(message == "This reader has borrowed books!"){
                        button3.val(message);
                    }
                }
            });
        });
        //delete reader data

        //Add book to borrowed list
        $('.forceBorrowed').click(function(event){
            event.preventDefault();
            var button4 = $(this);
            var bookIsbn2;
            var readerId2;
            $(this).siblings().each(function(){
                if($(this).hasClass("addIsbn")){
                    bookIsbn2 = $(this).val();
                    console.log(bookIsbn2);
                }
                if($(this).hasClass("readerId")){
                    readerId2 = $(this).val();
                    console.log(readerId2);
                }
            });

            $.ajax({
                url: "rest/book/borrowed_add/" + readerId2 + "/" + bookIsbn2,
                type: "POST",
                success: function(message){
                    console.log(message);
                    if(message == "Book was added to list successfully!"){
                        button4.val(message);
                    }
                }
            });
        });
        //Add book to borrowed list
    });
</script>