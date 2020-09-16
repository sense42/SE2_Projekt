package model.dao;

import model.dto.InseratDTO;
import model.dto.ReservierungDTO;
import model.dto.VertrieblerDTO;
import services.db.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservierungDAO {


    private static ReservierungDAO reservierungDAO;

    public static ReservierungDAO getInstance(){
        if (reservierungDAO == null) {
            reservierungDAO = new ReservierungDAO();
        }
        return reservierungDAO;
    }

    public void saveReservierung(ReservierungDTO reservierungDTO) throws DBException {

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement(
                    "INSERT INTO carlook.reservierung(email,inseratID) VALUES(?,?)");
            preparedStatement.setString(1,reservierungDTO.getKundeEmail());
            preparedStatement.setInt(2,reservierungDTO.getInseratID());

            preparedStatement.executeUpdate();
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "saveReservierung", throwables);
            throw new DBException("Ein unerwarteter Fehler ist beim Speichern der Reservierung aufgetreten!");
        }
    }

    public List<ReservierungDTO> getAll() throws DBException {
        ResultSet rs;
        List <ReservierungDTO> list = new ArrayList<>();

        try {
            rs = JDBCConnection.getInstance().getStatement().executeQuery("SELECT * FROM carlook.reservierung");

            while (rs != null && rs.next()){
                ReservierungDTO reservierung = new ReservierungDTO();
                reservierung.setKundeEmail(rs.getString(1));
                reservierung.setInseratID(rs.getInt(2));
                list.add(reservierung);
            }
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getAll", throwables);
            throw new DBException("Bei dem laden der Vertriblerdaten ist ein Fehler aufgetreten.");
        }
        return list;
    }

    public List<ReservierungDTO> getReservierungDTOS (String email) throws DBException {
        List<ReservierungDTO> list = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet rs;
        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement("SELECT * FROM carlook.reservierung WHERE email = ?");
            preparedStatement.setString(1, email);

            preparedStatement.execute();
            rs = preparedStatement.getResultSet();

            while (rs != null && rs.next()){
                ReservierungDTO reservierung = new ReservierungDTO();
                reservierung.setKundeEmail(rs.getString(1));
                reservierung.setInseratID(rs.getInt(2));
                list.add(reservierung);
            }
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getAll", throwables);
            throw new DBException("Bei dem laden der Vertriblerdaten ist ein Fehler aufgetreten.");
        }
        return list;
    }
}
