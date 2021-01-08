package repositories;

import domain.GenericClassList;
import domain.Reader;
import repositories.interfaces.IDBRepository;
import repositories.interfaces.IReaderRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ReaderRepository implements IReaderRepository<Reader> {
    private IDBRepository dbrepo = new MySQLRepository();

    @Override
    public void add(Reader entity) {
        try{
            String sql = "insert into readers(reader_name, password, is_librarian) " +
                    "values(?, ?, ?)";
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPassword());
            stmt.setBoolean(3, entity.getIsLibrarian());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Reader entity) {
        String sql = "update readers set ";
        if(entity.getName() !=null){
            sql += "reader_name = ?, ";
        }
        if(entity.getPassword() != null){
            sql += "password = ?, ";
        }
        if(entity.getIsLibrarian()){
            sql += "is_librarian = ?, ";
        }

        sql = sql.substring(0, sql.length() - 2);
        sql += "where id = ?";
        try{
            int i = 1;
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            if(entity.getName() != null){
                stmt.setString(i++, entity.getName());
            }
            if(entity.getPassword() != null){
                stmt.setString(i++, entity.getPassword());
            }
            if(entity.getIsLibrarian()){
                stmt.setBoolean(i++, entity.getIsLibrarian());
            }
            stmt.setInt(i++, entity.getId());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(Reader entity) {
        String sql = "delete from readers where id = " + entity.getId();
        try {
            PreparedStatement stmt = dbrepo.getConnection().prepareStatement(sql);
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Reader queryOne(String sql) {
        try{
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return new Reader(
                        rs.getInt("id"),
                        rs.getString("reader_name"),
                        rs.getString("password"),
                        rs.getBoolean("is_librarian")
                );
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public GenericClassList<Reader> getAllReaders() {
        try{
            String sql = "select * from readers";
            Statement stmt = dbrepo.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            GenericClassList<Reader> readers = new GenericClassList<>();
            List<Reader> listReaders = new LinkedList<>();
            while(rs.next()){
                Reader reader = new Reader(
                        rs.getInt("id"),
                        rs.getString("reader_name"),
                        rs.getString("password"),
                        rs.getBoolean("is_librarian")
                );
                listReaders.add(reader);
            }
            readers.setList(listReaders);
            return readers;
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Reader getReaderById(int id) {
        String sql = "select * from readers where id = " + id + " limit 1";
        return queryOne(sql);
    }

    @Override
    public Reader getReaderByName(String name) {
        String sql = "select * from readers where reader_name = '" + name + "' limit 1";
        return queryOne(sql);
    }

    @Override
    public Reader loginReader(String name, String password) {
        String sql = "select * from readers where reader_name = '" + name +
                "' and password = '" + password + "' limit 1";
        return queryOne(sql);
    }

}
