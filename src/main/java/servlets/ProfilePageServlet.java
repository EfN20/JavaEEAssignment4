package servlets;

import domain.Book;
import domain.GenericClassList;
import domain.GenericClassStack;
import domain.Reader;
import services.BookService;
import services.ReaderService;
import services.interfaces.IBookService;
import services.interfaces.IReaderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProfilePageServlet")
public class ProfilePageServlet extends HttpServlet {
    private IBookService<Book> bookService = new BookService();
    private IReaderService<Reader> readerService = new ReaderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Reader signedReader = (Reader) session.getAttribute("signedUser");
        Cookie cookie = new Cookie("username", signedReader.getName());
        cookie.setMaxAge(60*60*24*14);
        response.addCookie(cookie);

        if(signedReader.getIsLibrarian()){
            GenericClassList<Reader> readers = readerService.getAllReaders();
            request.setAttribute("readers", readers);
            GenericClassStack<Book> available = bookService.getAllBooks();
            request.setAttribute("available", available);
        }
        List<Book> borrowedBooks = bookService.getBorrowedBooks(signedReader.getId());
        request.setAttribute("borrowedBooks", borrowedBooks);

        request.getRequestDispatcher("profile-page").forward(request, response);
    }
}
