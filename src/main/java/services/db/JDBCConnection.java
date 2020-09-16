package services.db;

import model.dao.DBException;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCConnection {

    // TODO: logging, catch/throw, aufbau bereinigen.

    private final String url = "jdbc:postgresql://dumbo.inf.h-brs.de:5432/fduman2s";
    private final String password = "fduman2s";
    private final String user = "fduman2s";
    private final String schema = "carlook";

    private static JDBCConnection connection = null;
    private Connection conn;


    public static JDBCConnection getInstance(){
        if (connection == null){
            connection = new JDBCConnection();
        }
        return connection;
    }

    private JDBCConnection() {
        this.initConnection();
    }

    public void initConnection(){
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.openConnection();
    }

    public void openConnection(){
        try {

            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("currentSchema", schema);

            conn = DriverManager.getConnection(url, properties);
            conn.setSchema(schema);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Statement getStatement() throws DBException {
        try {
            if (this.conn.isClosed()) {
                this.openConnection();
            }
            conn.setSchema(schema);
            Statement statement = this.conn.createStatement();
            return this.conn.createStatement();

        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getStatement", throwables);
            throw new DBException("Fehler bei dem Versuch eine Verbindung mit der Datenbank aufzubauen!");
        }
    }

    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public PreparedStatement getPreparedStatement(String sql) throws DBException {
        try {
            if (this.conn.isClosed()){
                this.openConnection();
            }
            return this.conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getPreparedStatement", throwables);
            throw new DBException("Fehler bei dem Versuch eine Verbindung mit der Datenbank aufzubauen!");
        }
    }


}
