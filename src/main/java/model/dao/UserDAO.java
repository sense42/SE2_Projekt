package model.dao;

import model.dto.UserDTO;
import services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private static UserDAO userDAO;

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public void saveUser(UserDTO user) throws DBException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement(
                    "INSERT INTO carlook.user(email, passwort, vorname, nachname) VALUES(?,?,?,?)");

        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getVorname());
        preparedStatement.setString(4, user.getNachname());

        preparedStatement.executeUpdate();

        } catch (Exception throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "saveUser", throwables);
            throw new DBException("Bei dem speichern des Nutzers ist ein Fehler aufgetreten.");
        }
    }

    public List <UserDTO> getAll() throws DBException {
        ResultSet rs;
        List <UserDTO> list = new ArrayList<>();

        try {
            rs = JDBCConnection.getInstance().getStatement().executeQuery("SELECT * FROM carlook.user");

            while (rs != null && rs.next()){
                UserDTO user = new UserDTO();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("passwort"));
                user.setVorname(rs.getString("vorname"));
                user.setNachname(rs.getString("nachname"));
                list.add(user);
            }
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getAll", throwables);
            throw new DBException("Bei dem laden der Nutzerdaten ist ein Fehler aufgetreten.");
        }
        return list;
    }

    public UserDTO getUser(String email){
        ResultSet rs;
        PreparedStatement preparedStatement;
        UserDTO user = null;
        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement("SELECT * FROM carlook.user WHERE carlook.user.email = ?");
//        rs = JDBCConnection.getInstance().getStatement().executeQuery("SELECT * FROM user WHERE user.email = ?");

            preparedStatement.setString(1, email);

            preparedStatement.execute();

            rs = preparedStatement.getResultSet();

            user = new UserDTO();
            rs.next();
            user.setEmail(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setVorname(rs.getString(3));
            user.setNachname(rs.getString(4));


        } catch (DBException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

}
