package repositories.interfaces;

public interface IEntityRepository<T> {
    void add(T entity);

    void update(T entity);

    void remove(T entity);

    T queryOne(String sql);
}
