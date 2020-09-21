package test;

import control.LoginControl;
import model.dao.DBException;
import model.dao.UserDAO;
import model.dto.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.util.LoginResult;

import static org.junit.jupiter.api.Assertions.*;

class LoginControlTest {

    UserDTO user;

    @BeforeEach
    void setUp() throws DBException {
        user = new UserDTO();
        user.setEmail("JUnitTest@test.de");
        user.setVorname("JUnitVorname");
        user.setNachname("JUnitNachname");
        user.setPassword("JUnitPw123");
        UserDAO.getInstance().saveUser(user);
    }

    @AfterEach
    void tearDown() throws DBException {
        UserDAO.getInstance().deleteUser(user.getEmail());
    }

    @Test
    void loginuser() {
        assertEquals(LoginResult.EMAIL_EMPTY,LoginControl.Loginuser("", user.getPassword()));
        assertEquals(LoginResult.EMAIL_PASSWORD_WRONG,LoginControl.Loginuser("asdasdasdda@test.de", user.getPassword()));
        assertEquals(LoginResult.EMAIL_PASSWORD_WRONG,LoginControl.Loginuser(user.getEmail(),"asdasdasdasd"));
//        assertEquals(LoginResult.LOGIN_ENDKUNDE_SUCCEEDED,LoginControl.Loginuser(user.getEmail(), user.getPassword()));
        assertEquals(LoginResult.PASSWORD_EMPTY,LoginControl.Loginuser(user.getEmail(), ""));
    }
}