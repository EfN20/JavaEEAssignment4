package controllers;

import domain.Book;
import domain.GenericClassStack;
import services.BookService;
import services.interfaces.IBookService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.List;

@Path("book")
public class BookController {
    private IBookService<Book> bookService = new BookService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response createNewBook(Book book){
        try {
            GenericClassStack<Book> books = bookService.getAllBooks();
            boolean check = true;
            Iterator<Book> bookIterator = books.getStack().iterator();
            while (bookIterator.hasNext()){
                if(book.getISBN() == bookIterator.next().getISBN()){
                    check = false;
                }
            }
            if(check == false){
                return Response
                        .status(Response.Status.ACCEPTED)
                        .entity("There is already book with same ISBN!")
                        .build();
            }
            else{
                bookService.addBook(book);
            }
        } catch (ServerErrorException ex){
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity("Book was added successfully!")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public Response removeNewBook(Book book){
        try {
            bookService.removeBook(book);
        } catch (ServerErrorException ex){
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity("Book was removed successfully!")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateNewBook(Book book){
        try {
            bookService.updateBook(book);
        } catch (ServerErrorException ex){
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity("Book was updated successfully!")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllBooks(){
        GenericClassStack<Book> books;
        try {
            books = bookService.getAllBooks();
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity("Books cannot be found")
                    .build();
        }

        if (books == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Books do not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(books)
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/borrowed_all/{reader_id}")
    public Response getBorrowedBooks(@PathParam("reader_id") int id){
        List<Book> borrowedBooks;
        try {
            borrowedBooks = bookService.getBorrowedBooks(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity("Borrowed books cannot be found")
                    .build();
        }

        if (borrowedBooks == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Borrowed books do not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(borrowedBooks)
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{isbn}")
    public Response getBookByISBN(@PathParam("isbn") String isbn){
        Book book;
        try {
            book = bookService.getBookByISBN(isbn);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity("Book cannot be found")
                    .build();
        }

        if (book == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Book do not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(book)
                    .build();
        }
    }

    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public Response getBookByName(@PathParam("name") String name){
        Book book;
        try {
            book = bookService.getBookByName(name);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity("Book cannot be found")
                    .build();
        }

        if (book == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Book do not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(book)
                    .build();
        }
    }*/

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/borrowed_add/{reader_id}/{isbn}")
    public Response addBookToBorrowedList(@PathParam("isbn") String isbn, @PathParam("reader_id") int reader_id){
        try {
            bookService.addBookToBorrowedList(isbn, reader_id);
        } catch (ServerErrorException ex){
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity("Book was added to list successfully!")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/borrowed_remove/{reader_id}/{isbn}")
    public Response removeBookToBorrowedList(@PathParam("isbn") String isbn, @PathParam("reader_id") int reader_id){
        try {
            bookService.removeBookFromBorrowedList(isbn, reader_id);
        } catch (ServerErrorException ex){
            return Response
                    .serverError()
                    .entity(ex.getMessage())
                    .build();
        } catch (BadRequestException ex){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
        return Response
                .status(Response.Status.CREATED)
                .entity("Book was removed from list successfully!")
                .build();
    }

}
