package test;

import control.RegisterControl;
import model.dao.DBException;
import model.dao.EndkundeDAO;
import model.dao.UserDAO;
import model.dto.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.util.RegistrationResult;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControlTest {
    UserDTO user;
    UserDTO user2;

    @BeforeEach
    void setUp() {
        user = new UserDTO();
        user.setEmail("JUnitTest@test.de");
        user.setVorname("JUnitVorname");
        user.setNachname("JUnitNachname");
        user.setPassword("JUnitPw123");

        user2 = new UserDTO();
        user2.setEmail("JUnitTest@test.de");
        user2.setVorname("JUnitVorname");
        user2.setNachname("JUnitNachname");
        user2.setPassword("JUnitPw123");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void registerUser() throws DBException {
//        EMAIL_NOT_VALID("Dies ist keine gültige E-Mail Adresse."),
        user.setEmail("testestsa.de");
        assertEquals(RegistrationResult.EMAIL_NOT_VALID, RegisterControl.registerUser(user, user.getPassword()));
        user.setEmail("");
//        EMAIL_EMPTY("Bitte geben Sie eine E-Mail Adresse an."),
        assertEquals(RegistrationResult.EMAIL_EMPTY, RegisterControl.registerUser(user, user.getPassword()));
        user.setEmail(user2.getEmail());
        user.setPassword("123456");
//        PASSWORD_SHORT("Ihr Passwort muss mindestens aus 7 Zeichen bestehen."),
        assertEquals(RegistrationResult.PASSWORD_SHORT, RegisterControl.registerUser(user, "123456"));
        user.setPassword("");
//        PASSWORD_EMPTY("Bitte geben Sie ein Passwort ein."),
        assertEquals(RegistrationResult.PASSWORD_EMPTY, RegisterControl.registerUser(user, ""));
        user.setPassword("GutesPW123");
//        PASSWORD_DIFFERENT("Die eingegebenen Passwörter stimmen nicht überein."),
        assertEquals(RegistrationResult.PASSWORD_DIFFERENT, RegisterControl.registerUser(user, "FalschesPW123"));
        user.setPassword(user2.getPassword());
        user.setVorname("");
//        FIRSTNAME_EMPTY("Bitte geben Sie Ihren Vornamen ein."),
        assertEquals(RegistrationResult.FIRSTNAME_EMPTY, RegisterControl.registerUser(user, user.getPassword()));
        user.setVorname("a");
//        FIRSTNAME_SHORT("Dieser Vorname ist zu kurz."),
        assertEquals(RegistrationResult.FIRSTNAME_SHORT, RegisterControl.registerUser(user, user.getPassword()));
        user.setVorname(user2.getVorname());
        user.setNachname("");
//        SURNAME_EMPTY("Bitte geben Sie Ihren Nachnamen ein."),
        assertEquals(RegistrationResult.SURNAME_EMPTY, RegisterControl.registerUser(user, user.getPassword()));
        user.setNachname("a");
//        SURNAME_SHORT("Dieser Nachname ist zu kurz."),
        assertEquals(RegistrationResult.SURNAME_SHORT, RegisterControl.registerUser(user, user.getPassword()));
        user.setNachname(user2.getNachname());
//        ENDKUNDE_REGISTERED("Registrierung als Endkunde erfolgreich! Sie können sich nun einloggen."),
        assertEquals(RegistrationResult.ENDKUNDE_REGISTERED, RegisterControl.registerUser(user, user.getPassword()));
//        EMAIL_ALREADY_EXISTS("Diese E-Mail Adresse ist bereits vergeben."),
        assertEquals(RegistrationResult.EMAIL_ALREADY_EXISTS, RegisterControl.registerUser(user, user.getPassword()));
        EndkundeDAO.getInstance().deleteEnkunde(user.getEmail());
        UserDAO.getInstance().deleteUser(user.getEmail());

    }
}