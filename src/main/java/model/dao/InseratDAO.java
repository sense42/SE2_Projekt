package model.dao;

import jdk.nashorn.internal.scripts.JD;
import model.dto.InseratDTO;
import model.dto.UserDTO;
import services.db.JDBCConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InseratDAO {

    private static InseratDAO inseratDAO;

    public static InseratDAO getInstance() {
        if (inseratDAO == null) {
            inseratDAO = new InseratDAO();
        }
        return inseratDAO;
    }

    public void saveInserat(InseratDTO inserat) throws DBException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement(
                    "INSERT INTO carlook.inserat( marke, baujahr, beschreibung, email) VALUES(?,?,?,?)");

            preparedStatement.setString(1, inserat.getMarke() );
            preparedStatement.setInt(2, inserat.getBaujahr());
            preparedStatement.setString(3, inserat.getBeschreibung());
            preparedStatement.setString(4, inserat.getEmail());

            preparedStatement.executeUpdate();

        } catch (Exception throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "saveInserat", throwables);
            throw new DBException("Bei dem speichern des Nutzers ist ein Fehler aufgetreten.");
        }
    }

    public List<InseratDTO> getAll() throws DBException {
        ResultSet rs;
        List <InseratDTO> list = new ArrayList<>();

        try {
            rs = JDBCConnection.getInstance().getStatement().executeQuery("SELECT * FROM carlook.inserat");

            while (rs != null && rs.next()){
                InseratDTO inserat = new InseratDTO();
                inserat.setId(rs.getInt("inseratID"));
                inserat.setMarke(rs.getString("marke"));
                inserat.setBaujahr(rs.getInt("baujahr"));
                inserat.setBeschreibung(rs.getString("beschreibung"));
                inserat.setEmail(rs.getString("email"));

                list.add(inserat);
            }
        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getAll", throwables);
            throw new DBException("Bei dem laden der Nutzerdaten ist ein Fehler aufgetreten.");
        }
        return list;
    }

    public List<InseratDTO> getSuchInserate(String suchfeld, int baujahr ) throws DBException {
        String query;
        String queryBJ;

        PreparedStatement preparedStatement;

        ResultSet resultSet;
        List <InseratDTO> list = new ArrayList<>();

        try {
            query ="SELECT * FROM carlook.inserat WHERE " +
                    "LOWER(marke) LIKE LOWER(?) OR " +
                    "LOWER(beschreibung) LIKE LOWER(?)";
            queryBJ = "SELECT * FROM carlook.inserat WHERE " +
                    "baujahr = ?";

            if (baujahr == -1){
                preparedStatement = JDBCConnection.getInstance().getPreparedStatement(query);
            }
            else {
                preparedStatement = JDBCConnection.getInstance().getPreparedStatement(query + " INTERSECT " + queryBJ);
                preparedStatement.setInt(3,baujahr);
            }

            if (suchfeld .equals("")) {
                preparedStatement.setString(1, "%");
                preparedStatement.setString(2, "%");
            } else {
                preparedStatement.setString(1, suchfeld);
                preparedStatement.setString(2, "%" + suchfeld + "%");
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet != null && resultSet.next()){
                InseratDTO inserat = new InseratDTO();
                inserat.setId(resultSet.getInt("inseratID"));
                inserat.setMarke(resultSet.getString("marke"));
                inserat.setBaujahr(resultSet.getInt("baujahr"));
                inserat.setBeschreibung(resultSet.getString("beschreibung"));
                inserat.setEmail(resultSet.getString("email"));
                list.add(inserat);
            }

        } catch (SQLException | DBException throwables) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, "getSuchInserate", throwables);
            throw new DBException("Bei der suche nach Inseraten ist ein Fehler aufgetreten.");
        }
            return list;

    }

    public static InseratDTO getInseratDTObyID(int id) {
        ResultSet rs;
        List<InseratDTO> list = new ArrayList<>();
        PreparedStatement preparedStatement;
        InseratDTO inserat = null;

        try {
            preparedStatement = JDBCConnection.getInstance().getPreparedStatement("SELECT * FROM carlook.inserat WHERE carlook.inserat.inseratid = ?");

            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            rs = preparedStatement.getResultSet();

            if (rs != null && rs.next()) {
                inserat = new InseratDTO();
                inserat.setId(rs.getInt("inseratid"));
                inserat.setMarke(rs.getString("marke"));
                inserat.setBaujahr(rs.getInt("baujahr"));
                inserat.setBeschreibung(rs.getString("beschreibung"));
                inserat.setEmail(rs.getString("email"));

            }

        } catch (DBException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return inserat;
    }
}
