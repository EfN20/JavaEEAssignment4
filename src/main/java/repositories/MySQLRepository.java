package repositories;

import repositories.interfaces.IDBRepository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLRepository implements IDBRepository {
    @Override
    public Connection getConnection() {
        try {
            Context initialContext = new InitialContext();
            Context envCtx = (Context) initialContext.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/Assignment4");
            return ds.getConnection();
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
