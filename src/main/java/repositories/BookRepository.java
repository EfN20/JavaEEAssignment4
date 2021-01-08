package repositories;

import domain.Book;
import domain.GenericClassStack;
import repositories.interfaces.IBookRepository;
import repositories.interfaces.IDBRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BookRepository implements IBookRepository<Book> {
    private IDBRepository dbrepo = new MySQLRepository();

    @Override
    public void add(Book entity) {
        try{
            String sql = "insert into books(isbn, name, author, count_of_copies, url) " +
                    "values(?, ?, ?, ?, ?)";
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            stmt.setString(1, entity.getISBN());
            stmt.setString(2, entity.getName());
            stmt.setString(3, entity.getAuthor());
            stmt.setInt(4, entity.getCount());
            stmt.setString(5, entity.getUrl_img());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Book entity) {
        String sql = "update books set ";
        if(entity.getName() !=null){
            sql += "name = ?, ";
        }
        if(entity.getAuthor() != null){
            sql += "author = ?, ";
        }
        if(entity.getCount() >= 0){
            sql += "count_of_copies = ?, ";
        }

        sql = sql.substring(0, sql.length() - 2);
        sql += "where isbn = ?";
        try{
            int i = 1;
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            if(entity.getName() != null){
                stmt.setString(i++, entity.getName());
            }
            if(entity.getAuthor() != null){
                stmt.setString(i++, entity.getAuthor());
            }
            if(entity.getCount() >= 0){
                stmt.setInt(i++, entity.getCount());
            }
            stmt.setString(i++, entity.getISBN());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(Book entity) {
        String sql = "delete from books where isbn = " + entity.getISBN();
        try {
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Book queryOne(String sql) {
        try{
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return new Book(
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("count_of_copies"),
                        rs.getString("url")
                );
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public GenericClassStack<Book> getAllBooks() {
        try{
            String sql = "select * from books";
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            GenericClassStack<Book> books = new GenericClassStack<>();
            Stack<Book> listBooks = new Stack<>();
            while(rs.next()){
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("count_of_copies"),
                        rs.getString("url")
                );
                listBooks.push(book);
            }
            books.setStack(listBooks);
            return books;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getBorrowedBooks(int reader_id) {
        try{
            String sql = "select books.isbn, books.name, books.author, books.count_of_copies, books.url from books\n" +
                    "inner join borrowed_books on borrowed_books.book_isbn = books.isbn\n" +
                    "where borrowed_books.reader_id = " + reader_id;
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Book> listBooks = new LinkedList<>();
            while(rs.next()){
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("count_of_copies"),
                        rs.getString("url")
                );
                listBooks.add(book);
            }
            return listBooks;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        String sql = "select * from books where isbn = '" + ISBN + "' limit 1";
        return queryOne(sql);
    }

    @Override
    public Book getBookByName(String name) {
        String sql = "select * from books where name = '" + name + "' limit 1";
        return queryOne(sql);
    }

    @Override
    public void addBookToBorrowedList(String isbn, int reader_id) {
        try {
            Book book = getBookByISBN(isbn);
            if(book.getCount() > 0){
                String sql = "insert into borrowed_books " +
                        "values (?, ?)";
                PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
                stmt.setInt(1, reader_id);
                stmt.setString(2, isbn);
                int check = stmt.executeUpdate();
                if(check == 1){
                    int bookCount = book.getCount() - 1;
                    book.setCount(bookCount);
                    update(book);
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void removeBookFromBorrowedList(String isbn, int reader_id) {
        try {
            Book book = getBookByISBN(isbn);
            String sql = "delete from borrowed_books where reader_id = " + reader_id +
                    " and book_isbn = '" + isbn + "'";
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            int check = stmt.executeUpdate();
            if(check == 1){
                int bookCount = book.getCount() + 1;
                book.setCount(bookCount);
                update(book);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
