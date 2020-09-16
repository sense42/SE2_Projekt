package control;

import gui.ui.MyUI;
import model.dao.DBException;
import model.dao.UserDAO;
import model.dto.EndkundeDTO;
import model.dto.UserDTO;
import model.dto.VertrieblerDTO;
import services.db.JDBCConnection;
import services.util.LoginResult;
import services.util.Roles;
import services.util.UserTypes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class LoginControl {

    public static LoginResult Loginuser(String email, String password){

        if (email.length() < 1){
            return LoginResult.EMAIL_EMPTY;
        }
        if (password.length()<1){
            return LoginResult.PASSWORD_EMPTY;
        }
        UserDTO user = UserDAO.getInstance().getUser(email);
        if (!user.getPassword().equals(password)){
            return LoginResult.EMAIL_PASSWORD_WRONG;
        }
        else{
            MyUI.getCurrent().getSession().setAttribute(Roles.CURRENT_USER, user);
        }
        // hier suche nach erkennungsmerkmal des Vertrieblers mit !CaseInsensitive!
        // TODO: besser suche in DB spalten nach dem user statt nach emailstring.
        if (Pattern.compile("@carlook.de", Pattern.CASE_INSENSITIVE).matcher(user.getEmail()).find()) {
            MyUI.getCurrent().getSession().setAttribute(Roles.CURRENT_USERTYPE, UserTypes.Vertriebler);
            return LoginResult.LOGIN_VERTRIBLER_SUCCEEDED;
        }
        else{
            MyUI.getCurrent().getSession().setAttribute(Roles.CURRENT_USERTYPE, UserTypes.Endkunde);
            return LoginResult.LOGIN_ENDKUNDE_SUCCEEDED;
        }
    }
}
