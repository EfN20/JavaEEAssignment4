package servlets;

import domain.Book;
import domain.GenericClassStack;
import domain.Reader;
import services.BookService;
import services.ReaderService;
import services.interfaces.IBookService;
import services.interfaces.IReaderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserServlet")
public class ReaderLoginServlet extends HttpServlet {
    private IReaderService<Reader> readerService = new ReaderService();
    private IBookService<Book> bookService = new BookService();

    //FOR LOGIN ALREADY EXIST USER
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("usernameLog");
        String password = request.getParameter("passwordLog");
        if(username != null && password != null){
            Reader signedReader = readerService.loginReader(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("signedUser", signedReader);
            //taking from DB books and sending to jsp
            GenericClassStack<Book> books = bookService.getAllBooks();
            request.setAttribute("books", books);
            //

            request.getRequestDispatcher("books-page").forward(request, response);
        }
        else{
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.print("<h1>Invalid name or password</h1>");
            pw.close();
        }
    }

    //FOR REGISTER NEW USERS
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username != null && password != null){
            Reader newReader = new Reader(username, password);
            readerService.addReader(newReader);
            Reader signedReader = readerService.loginReader(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("signedUser", signedReader);
            //taking from DB books and sending to jsp
            GenericClassStack<Book> books = bookService.getAllBooks();
            request.setAttribute("books", books);
            //

            request.getRequestDispatcher("books-page").forward(request, response);
        }
        else{
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.print("<h1>There should not be empty field</h1>");
            pw.close();
        }
    }

}
