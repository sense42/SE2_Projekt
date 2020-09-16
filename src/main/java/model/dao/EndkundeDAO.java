package model.dao;

import model.dto.EndkundeDTO;
import model.dto.VertrieblerDTO;
import services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EndkundeDAO {

    private static EndkundeDAO endkundeDAO;

    public static EndkundeDAO getInstance(){
        if (endkundeDAO == null) {
            endkundeDAO = new EndkundeDAO();
        }
        return endkundeDAO;
    }

    public void saveEndkunde(EndkundeDTO endkundeDTO) throws DBException {

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement(
                    "INSERT INTO carlook.endkunde(email) VALUES(?)");
            preparedStatement.setString(1,endkundeDTO.getEmail());

            preparedStatement.executeUpdate();
        } catch (DBException | SQLException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "saveEndkunde", throwables);
            throw new DBException("Ein unerwarteter Fehler ist beim Speichern des Endkunden aufgetreten!");
        }
    }

    public List<EndkundeDTO> getAll() throws DBException {
        ResultSet rs;
        List <EndkundeDTO> list = new ArrayList<>();

        try {
            rs = JDBCConnection.getInstance().getStatement().executeQuery("SELECT * FROM carlook.endkunde");

            while (rs != null && rs.next()){
                EndkundeDTO endkunde = new EndkundeDTO();
                endkunde.setEmail(rs.getString(1));

                list.add(endkunde);
            }
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getAll", throwables);
            throw new DBException("Bei dem laden der Kundendaten ist ein Fehler aufgetreten.");
        }
        return list;
    }

}
