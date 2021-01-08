package servlets;

import domain.Book;
import domain.GenericClassStack;
import domain.Reader;
import services.BookService;
import services.interfaces.IBookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "NewBookServlet")
public class NewBookServlet extends HttpServlet {
    private IBookService<Book> bookService = new BookService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String isbn = request.getParameter("newIsbn");
        String name = request.getParameter("newName");
        String author = request.getParameter("newAuthor");
        int count = Integer.parseInt(request.getParameter("newCount"));
        String url = request.getParameter("newUrl");
        Book newBook = new Book(isbn, name, author, count, url);
        List<Book> borrowed = bookService.getBorrowedBooks(((Reader) session.getAttribute("signedUser")).getId());
        for(Book book : borrowed){
            if(book.getISBN() == newBook.getISBN()){
                PrintWriter out = response.getWriter();
                out.println("There is already book with same ISBN!");
            }
            else{
                bookService.addBook(newBook);
                GenericClassStack<Book> allBook = bookService.getAllBooks();
                request.setAttribute("books", allBook);
                request.getRequestDispatcher("books-page").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
