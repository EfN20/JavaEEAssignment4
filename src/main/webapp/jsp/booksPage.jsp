<%--
  Created by IntelliJ IDEA.
  User: Berikkali
  Date: 30.10.2020
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <title>Books</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="d-flex justify-content-center align-items-center my-2">
            <h1 style="width: auto">Welcome, <c:out value="${signedUser.name}" />
                [<c:if test="${!signedUser.isLibrarian}">Reader</c:if><c:if test="${signedUser.isLibrarian}">Librarian</c:if>]</h1>
            <a class="btn btn-dark btn-sm mx-2" href="logout" role="button" style="height: 30px">Log out</a>
            <a class="btn btn-dark btn-sm mx-2" href="profile" role="button" style="height: 30px">Profile page</a>
        </div>

        <c:if test="${signedUser.isLibrarian}">
            <c:forEach var="book" items="${books.stack}">
                <div class="card mb-3 my-2 mx-auto" style="max-width: 720px;">
                    <div class="row no-gutters">
                        <div class="col-md-4">
                            <img src="<c:out value="${book.url_img}"/>" class="card-img" alt="DEADLINES DUSHAT">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <form method="post">
                                    <p class="card-text">Book name: <input type="text" class="card-title nameBook" value="<c:out value="${book.name}"/>"></p>
                                    <p class="card-text">Book author: <input type="text" class="card-text authorBook" value="<c:out value="${book.author}"/>"></p>
                                    <p class="card-text">ISBN: <c:out value="${book.ISBN}"/></p>
                                    <p class="card-text">Count of copies: <input type="number" class="card-text countBook" value="<c:out value="${book.count}"/>"></p>
                                    <p class="card-text">URL: <input type="url" class="card-text urlBook" value=" <c:out value="${book.url_img}"/>"></p>
                                    <input class="btn btn-dark refacor" type="submit" value="Update data">
                                </form>
                                <form method="post">
                                    <input class="btn btn-dark addBorrowed" type="submit" value="Add to borrowed list">
                                    <input hidden class="bookIsbn" name="bookIsbn" value="<c:out value="${book.ISBN}"/>">
                                    <input hidden class="readerId" name="readerId" value="<c:out value="${signedUser.id}"/>">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <div class="card mb-3 my-2 mx-auto" style="max-width: 720px;">
                <div class="row no-gutters">
                    <div class="col-md-8">
                        <div class="card-body">
                            <form method="post">
                                Name of reader: <input class="form-control addReaderName" type="text"><br>
                                Password: <input class="form-control addReaderPass" type="password"><br>
                                <input class="btn btn-dark addNewReader" type="submit" value="Add new reader">
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card mb-3 my-2 mx-auto" style="max-width: 720px;">
                <div class="row no-gutters">
                    <div class="col-md-8">
                        <div class="card-body">
                            <form method="post" action="newBook">
                                <p class="card-text newIsbn">ISBN of Book: <input type="text" name="newIsbn" class="card-text" value=""/></p>
                                <p class="card-text newName">Book name: <input type="text" name="newName" class="card-name" value=""></p>
                                <p class="card-text newAuthor">Book author: <input type="text" name="newAuthor" class="card-title" value=""/></p>
                                <p class="card-text newCount">Count of Copies: <input type="number" name="newCount" class="card-text" value=""/></p>
                                <p class="card-text newUrl">URL of photo: <input type="url" name="newUrl" class="card-text" value=""/></p>
                                <input class="btn btn-dark addNewBook" type="submit" value="Add new book">
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </c:if>

        <c:if test="${!signedUser.isLibrarian}">
            <c:forEach var="book" items="${books.stack}">
                <div class="card mb-3 my-2 mx-auto" style="max-width: 720px;">
                    <div class="row no-gutters">
                        <div class="col-md-4">
                            <img src="<c:out value="${book.url_img}"/>" class="card-img" alt="DEADLINES DUSHAT">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title"><span class="nameBook"><c:out value="${book.name}"/></span></h5>
                                <p class="card-text"><span class="authorBook"><c:out value="${book.author}"/></span></p>
                                <p class="card-text">ISBN: <c:out value="${book.ISBN}"/></p>
                                <p class="card-text"> Count of copies: <span class="countBook"><c:out value="${book.count}"/></span></p>
                                <form method="post">
                                    <input class="btn btn-dark addBorrowedReader" type="submit" value="Add to borrowed list" <c:if test="${book.count < 1}">disabled</c:if> >
                                    <input hidden class="bookIsbn" name="bookIsbn" value="<c:out value="${book.ISBN}"/>">
                                    <input hidden class="readerId" name="readerId" value="<c:out value="${signedUser.id}"/>">
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
        //Add book to borrowed list
        $('.addBorrowed').click(function(event){
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
                url: "rest/book/borrowed_add/" + readerId + "/" + bookIsbn,
                type: "POST",
                success: function(message){
                    console.log(message);
                    // Update book info
                    if(message == "Book was added to list successfully!"){
                        $.ajax({
                            url: "rest/book/" + bookIsbn,
                            type: "GET",
                            success: function(responseJson){
                                console.log(responseJson);
                                var card = button.closest('.row');
                                card.find('.card-img').attr('src', responseJson.url_img);
                                card.find('.authorBook').val(responseJson.author);
                                card.find('.countBook').val(responseJson.count);
                                card.find('.urlBook').val(responseJson.url_img);
                                card.find('.nameBook').val(responseJson.name);
                            }
                        });
                    }
                    // Update book info
                    button.val(message);
                }
            });
        });
        //Add book to borrowed list

        //Add book to borrowed list from reader
        $('.addBorrowedReader').click(function(event){
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
                url: "rest/book/borrowed_add/" + readerId + "/" + bookIsbn,
                type: "POST",
                success: function(message){
                    console.log(message);
                    // Update book info
                    if(message == "Book was added to list successfully!"){
                        $.ajax({
                            url: "rest/book/" + bookIsbn,
                            type: "GET",
                            success: function(responseJson){
                                console.log(responseJson);
                                var card = button.closest('.row');
                                card.find('.card-img').attr('src', responseJson.url_img);
                                card.find('.authorBook').text(responseJson.author);
                                card.find('.countBook').text(responseJson.count);
                                card.find('.urlBook').text(responseJson.url_img);
                                card.find('.nameBook').text(responseJson.name);
                            }
                        });
                    }
                    // Update book info
                    button.val(message);
                }
            });
        });
        //Add book to borrowed list from reader

        //Update data of book
        $('.refacor').click(function(event){
            event.preventDefault();
            var button2 = $(this);
            var card2 = $(this).closest('.row');
            var bookIsbn2 = card2.find('.bookIsbn').val();
            console.log(bookIsbn2);
            console.log(card2.find('.nameBook').val());
            console.log(card2.find('.authorBook').val());
            console.log(card2.find('.countBook').val());
            console.log(card2.find('.urlBook').val());
            var toSend = {
                isbn: bookIsbn2,
                name: card2.find('.nameBook').val(),
                author: card2.find('.authorBook').val(),
                count: card2.find('.countBook').val(),
                url_img: card2.find('.urlBook').val()
            };
            var jsonSend = JSON.stringify(toSend);
            $.ajax({
                url: "rest/book/update",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                data: jsonSend,
                success: function(message){
                    if(message == "Book was updated successfully!"){
                        $.ajax({
                            url: "rest/book/" + bookIsbn2,
                            type: "GET",
                            success: function(responseJson){
                                console.log(responseJson);
                                card2.find('.card-img').attr('src', responseJson.url_img);
                                card2.find('.authorBook').val(responseJson.author);
                                card2.find('.countBook').val(responseJson.count);
                                card2.find('.urlBook').val(responseJson.url_img);
                                card2.find('.nameBook').val(responseJson.name);
                            }
                        });
                    }
                    button2.val(message);
                }
            });
        });
        //Update data of book

        //Create new reader
        $('.addNewReader').click(function(event){
           event.preventDefault();
           var addButton = $(this);
           var usernameNew;
           var passwordNew;
           $(this).siblings().each(function(){
                if($(this).hasClass("addReaderName")){
                    usernameNew = $(this).val();
                    console.log(usernameNew);
                }
                if($(this).hasClass("addReaderPass")){
                    passwordNew = $(this).val();
                    console.log(passwordNew);
                }
           });
           var userSend = {reader_name: usernameNew, password: passwordNew};
           var userJson = JSON.stringify(userSend);
           $.ajax({
               url: "rest/reader/add",
               type: "POST",
               contentType: "application/json; charset=utf-8",
               data: userJson,
               success: function(message){
                   addButton.val(message);
               }
           });
        });
        //Create new reader
    });
</script>