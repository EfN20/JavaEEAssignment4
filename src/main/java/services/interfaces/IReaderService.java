package services.interfaces;

import domain.GenericClassList;

public interface IReaderService<T> {
    void addReader(T entity);

    void updateReader(T entity);

    void removeReader(T entity);

    GenericClassList<T> getAllReaders();

    T getReaderById(int id);

    T getReaderByName(String name);

    T loginReader(String name, String password);
}
