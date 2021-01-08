package services;

import domain.Book;
import domain.GenericClassStack;
import repositories.BookRepository;
import repositories.interfaces.IBookRepository;
import services.interfaces.IBookService;

import java.util.List;

public class BookService implements IBookService<Book> {
    private IBookRepository<Book> bookRepo = new BookRepository();

    @Override
    public void addBook(Book entity) {
        bookRepo.add(entity);
    }

    @Override
    public void updateBook(Book entity) {
        bookRepo.update(entity);
    }

    @Override
    public void removeBook(Book entity) {
        bookRepo.remove(entity);
    }

    @Override
    public GenericClassStack<Book> getAllBooks() {
        return bookRepo.getAllBooks();
    }

    @Override
    public List<Book> getBorrowedBooks(int reader_id) {
        return bookRepo.getBorrowedBooks(reader_id);
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        return bookRepo.getBookByISBN(ISBN);
    }

    @Override
    public Book getBookByName(String name) {
        return bookRepo.getBookByName(name);
    }

    @Override
    public void addBookToBorrowedList(String isbn, int reader_id) {
        bookRepo.addBookToBorrowedList(isbn, reader_id);
    }

    @Override
    public void removeBookFromBorrowedList(String isbn, int reader_id) {
        bookRepo.removeBookFromBorrowedList(isbn, reader_id);
    }
}
