package model.dao;

import model.dto.EndkundeDTO;
import model.dto.ReservierungDTO;
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

    public static ReservierungDAO getInstance() {
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
            preparedStatement.setString(1, reservierungDTO.getKundeEmail());
            preparedStatement.setInt(2, reservierungDTO.getInseratID());

            preparedStatement.executeUpdate();
            JDBCConnection.getInstance().closeConnection();
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "saveReservierung", throwables);
            throw new DBException("Ein unerwarteter Fehler ist beim Speichern der Reservierung aufgetreten!");
        }
    }

    public List<ReservierungDTO> getAll() throws DBException {
        ResultSet rs;
        List<ReservierungDTO> list = new ArrayList<>();

        try {
            rs = JDBCConnection.getInstance().getStatement().executeQuery("SELECT * FROM carlook.reservierung");

            while (rs != null && rs.next()) {
                ReservierungDTO reservierung = new ReservierungDTO();
                reservierung.setKundeEmail(rs.getString(1));
                reservierung.setInseratID(rs.getInt(2));
                list.add(reservierung);
            }
            JDBCConnection.getInstance().closeConnection();
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getAll", throwables);
            throw new DBException("Bei dem laden der Vertriblerdaten ist ein Fehler aufgetreten.");
        }
        return list;
    }

    public List<ReservierungDTO> getReservierungDTOS(String email) throws DBException {
        List<ReservierungDTO> list = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet rs;
        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement("SELECT * FROM carlook.reservierung WHERE email = ?");
            preparedStatement.setString(1, email);

            preparedStatement.execute();
            rs = preparedStatement.getResultSet();

            while (rs != null && rs.next()) {
                ReservierungDTO reservierung = new ReservierungDTO();
                reservierung.setKundeEmail(rs.getString(1));
                reservierung.setInseratID(rs.getInt(2));
                list.add(reservierung);
            }
            JDBCConnection.getInstance().closeConnection();
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getAll", throwables);
            throw new DBException("Bei dem laden der Vertriblerdaten ist ein Fehler aufgetreten.");
        }
        return list;
    }

    public void deleteReservierung(String email, int inseratid) throws DBException {
        try {
            PreparedStatement preparedStatement = JDBCConnection.getInstance().getPreparedStatement("DELETE FROM carlook.reservierung WHERE carlook.reservierung.email = ? AND carlook.reservierung.inseratid = ?");

            preparedStatement.setString(1,email);
            preparedStatement.setInt(2, inseratid);
            preparedStatement.execute();

            JDBCConnection.getInstance().closeConnection();
        } catch (SQLException | DBException throwables){
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "deleteReservierung", throwables);
            throw new DBException("Bei dem löschen der Reservierung ist ein Fehler aufgetreten.");
        }

    }

    public Boolean contains(List<ReservierungDTO> all, String endkundeMail, int inseratID){
        return all.stream().filter(u -> u.getKundeEmail().equals(endkundeMail) && u.getInseratID() == inseratID).findFirst().isPresent() ;
    }

}
