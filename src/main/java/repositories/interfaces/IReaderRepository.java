package repositories.interfaces;

import domain.GenericClassList;
import domain.Reader;

public interface IReaderRepository<T> extends IEntityRepository<Reader> {
    GenericClassList<T> getAllReaders();

    T getReaderById(int id);

    T getReaderByName(String name);

    T loginReader(String name, String password);

}
