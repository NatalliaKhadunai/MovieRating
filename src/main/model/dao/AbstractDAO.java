package main.model.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<T> {
    protected final static Logger logger = Logger.getLogger("DAOLogger");
    private static DataSource ds = null;
    protected Connection connection;

    static {
        try {
            Context envCtx = (Context) (new InitialContext().lookup("java:comp/env"));
            ds = (DataSource) envCtx.lookup("jdbc/dbfilm");
        }
        catch (NamingException e) {
            logger.error(e);
        }
    }

    public AbstractDAO() {
        try {
            connection = ds.getConnection();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public T getEntity(String identifier) {
        throw new UnsupportedOperationException();
    }
    public void addEntity(T entity) {
        throw new UnsupportedOperationException();
    }
    public List<T> getAllEntities() {
        throw new UnsupportedOperationException();
    }
}
