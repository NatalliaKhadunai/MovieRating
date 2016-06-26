package main.model.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class {@code AbstractDAO} is a root class for all the DAO objects in the system.
 */

public abstract class AbstractDAO{
    /**
     * Logger for all the DAO objects in the system.
     * {@see org.apache.log4j.Logger}
     * */
    protected final static Logger logger = Logger.getLogger("DAOLogger");
    /**
     * DataSource object, from which Connection object are taken.
     * {@see javax.sql.DataSource}
     * */
    private static DataSource ds = null;
    /**
     *  JDBC Connection.
     * {@see java.sql.Connection}
     * */
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

    /**
     * Method for giving back existing connection.
     */
    public void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }
}
