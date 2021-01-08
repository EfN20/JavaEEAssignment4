package services.interfaces;

import domain.GenericClassStack;

import java.util.List;

public interface IBookService<T> {
    void addBook(T entity);

    void updateBook(T entity);

    void removeBook(T entity);

    GenericClassStack<T> getAllBooks();

    List<T> getBorrowedBooks(int reader_id);

    T getBookByISBN(String ISBN);

    T getBookByName(String name);

    void addBookToBorrowedList(String isbn, int reader_id);

    void removeBookFromBorrowedList(String isbn, int reader_id);
}
