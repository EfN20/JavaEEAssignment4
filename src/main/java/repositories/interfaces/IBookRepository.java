package repositories.interfaces;

import domain.Book;
import domain.GenericClassStack;

import java.util.List;

public interface IBookRepository<T> extends IEntityRepository<Book>{
    GenericClassStack<T> getAllBooks();

    List<T> getBorrowedBooks(int reader_id);

    T getBookByISBN(String ISBN);

    T getBookByName(String name);

    void addBookToBorrowedList(String isbn, int reader_id);

    void removeBookFromBorrowedList(String isbn, int reader_id);
}
