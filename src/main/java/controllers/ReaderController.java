package controllers;

import domain.Book;
import domain.GenericClassList;
import domain.Reader;
import services.BookService;
import services.ReaderService;
import services.interfaces.IBookService;
import services.interfaces.IReaderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("reader")
public class ReaderController {
    private IReaderService<Reader> readerService = new ReaderService();
    private IBookService<Book> bookService = new BookService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response createNewReader(Reader reader){
        try {
            readerService.addReader(reader);
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
                .entity("Reader was added successfully!")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public Response removeNewReader(Reader reader){
        try {
            boolean check = true;
            List<Book> books = bookService.getBorrowedBooks(reader.getId());
            if(books.size() > 0){
                check = false;
            }
            if(check){
                readerService.removeReader(reader);
            }
            else if(!check){
                return Response
                        .status(Response.Status.ACCEPTED)
                        .entity("This reader has borrowed books!")
                        .build();
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
                .entity("Reader was removed successfully!")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateNewReader(Reader reader){
        try {
            readerService.updateReader(reader);
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
                .entity("Reader was updated successfully!")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllReaders(){
        GenericClassList<Reader> readers;
        try {
            readers = readerService.getAllReaders();
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity("Readers cannot be found")
                    .build();
        }

        if (readers == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Readers do not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(readers)
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{reader_id}")
    public Response getReaderById(@PathParam("reader_id") int id){
        Reader reader;
        try {
            reader = readerService.getReaderById(id);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity("Reader cannot be found")
                    .build();
        }

        if (reader == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Reader do not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(reader)
                    .build();
        }
    }

    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{reader_name}")
    public Response getReaderByName(@PathParam("reader_name") String name){
        Reader reader;
        try {
            reader = readerService.getReaderByName(name);
        } catch (ServerErrorException ex) {
            return Response
                    .serverError()
                    .build();
        } catch (BadRequestException ex) {
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity("Reader cannot be found")
                    .build();
        }

        if (reader == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Reader do not exist!")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(reader)
                    .build();
        }
    }*/

}
