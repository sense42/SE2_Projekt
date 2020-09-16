package model.dao;

import model.dto.EndkundeDTO;
import model.dto.UserDTO;
import model.dto.VertrieblerDTO;
import services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VertrieblerDAO {

    private static VertrieblerDAO vertrieblerDAO;

    public static VertrieblerDAO getInstance(){
        if (vertrieblerDAO == null) {
            vertrieblerDAO = new VertrieblerDAO();
        }
        return vertrieblerDAO;
    }

    public void saveVertriebler(VertrieblerDTO vertrieblerDTO) throws DBException {

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement(
                    "INSERT INTO carlook.vertriebler(email) VALUES(?)");
            preparedStatement.setString(1,vertrieblerDTO.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "saveVertriebler", throwables);
            throw new DBException("Ein unerwarteter Fehler ist beim Speichern des Vertrieblers aufgetreten!");
        }
    }

    public List<VertrieblerDTO> getAll() throws DBException {
        ResultSet rs;
        List <VertrieblerDTO> list = new ArrayList<>();

        try {
            rs = JDBCConnection.getInstance().getStatement().executeQuery("SELECT * FROM carlook.vertriebler");

            while (rs != null && rs.next()){
                VertrieblerDTO vertriebler = new VertrieblerDTO();
                vertriebler.setEmail(rs.getString(1));

                list.add(vertriebler);
            }
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getAll", throwables);
            throw new DBException("Bei dem laden der Vertriblerdaten ist ein Fehler aufgetreten.");
        }
        return list;
    }

}
