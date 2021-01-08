package services;

import domain.GenericClassList;
import domain.Reader;
import repositories.ReaderRepository;
import repositories.interfaces.IReaderRepository;
import services.interfaces.IReaderService;

public class ReaderService implements IReaderService<Reader> {
    private IReaderRepository<Reader> readerRepo = new ReaderRepository();

    @Override
    public void addReader(Reader entity) {
        readerRepo.add(entity);
    }

    @Override
    public void updateReader(Reader entity) {
        readerRepo.update(entity);
    }

    @Override
    public void removeReader(Reader entity) {
        readerRepo.remove(entity);
    }

    @Override
    public GenericClassList<Reader> getAllReaders() {
        return readerRepo.getAllReaders();
    }

    @Override
    public Reader getReaderById(int id) {
        return readerRepo.getReaderById(id);
    }

    @Override
    public Reader getReaderByName(String name) {
        return readerRepo.getReaderByName(name);
    }

    @Override
    public Reader loginReader(String name, String password) {
        return readerRepo.loginReader(name, password);
    }
}
