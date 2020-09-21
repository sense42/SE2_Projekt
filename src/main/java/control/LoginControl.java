package control;

import com.vaadin.ui.UI;
import gui.ui.MyUI;
import model.dao.DBException;
import model.dao.EndkundeDAO;
import model.dao.UserDAO;
import model.dao.VertrieblerDAO;
import model.dto.UserDTO;
import services.util.LoginResult;
import services.util.LogoutResult;
import services.util.Roles;
import services.util.UserTypes;

import java.util.regex.Pattern;

public class LoginControl {

    public static LoginResult Loginuser(String email, String password) {

        if (email.length() < 1) {
            return LoginResult.EMAIL_EMPTY;
        }
        if (password.length() < 1) {
            return LoginResult.PASSWORD_EMPTY;
        }
        try {
            UserDTO user = UserDAO.getInstance().getUser(email);

            if (user == null || !user.getPassword().equals(password)) {
                return LoginResult.EMAIL_PASSWORD_WRONG;
            } else {
                ((MyUI) UI.getCurrent()).setUser(UserDAO.getInstance().getUser(email));
                UI.getCurrent().getSession().setAttribute(Roles.CURRENT_USER, user);
            }
            if (EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(),email)){
                ((MyUI) UI.getCurrent()).setUser(UserDAO.getInstance().getUser(email));
                UI.getCurrent().getSession().setAttribute(Roles.CURRENT_USERTYPE, UserTypes.Endkunde);
                return LoginResult.LOGIN_ENDKUNDE_SUCCEEDED;
            } else  if (VertrieblerDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(),email)){
                UI.getCurrent().getSession().setAttribute(Roles.CURRENT_USERTYPE, UserTypes.Vertriebler);
                return LoginResult.LOGIN_VERTRIBLER_SUCCEEDED;
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
        return LoginResult.LOGIN_UNEXPECTED_ERROR;
    }

    public static void logoutUser(){
        MyUI.getCurrent().close();
        MyUI.getCurrent().getPage().setLocation("/");
    }
}
