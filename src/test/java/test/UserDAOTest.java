package test;

import javafx.scene.chart.ScatterChart;
import model.dao.DBException;
import model.dao.UserDAO;
import model.dto.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
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
        user2.setEmail("JUnitTest2@test.de");
        user2.setVorname("JUnitVorname2");
        user2.setNachname("JUnitNachname2");
        user2.setPassword("JUnitPw123123");

//        assertThrows(DBException.class, () -> UserDAO.getInstance().getUser(user.getEmail()));
//        assertThrows(DBException.class, () -> UserDAO.getInstance().getUser(user2.getEmail()));

        assertDoesNotThrow(() ->UserDAO.getInstance().saveUser(user));
    }


    @Test
    void getInstance() {
        UserDAO userDAO = UserDAO.getInstance();
        assertNotNull(userDAO);
        assertEquals(userDAO, UserDAO.getInstance());
    }

    @Test
    void saveUser() throws DBException {
        assertDoesNotThrow(() ->UserDAO.getInstance().saveUser(user2));
        assertEquals(user2.getEmail(), UserDAO.getInstance().getUser(user2.getEmail()).getEmail());
        assertEquals(user2.getNachname(), UserDAO.getInstance().getUser(user2.getEmail()).getNachname());
        assertEquals(user2.getVorname(), UserDAO.getInstance().getUser(user2.getEmail()).getVorname());
        assertEquals(user2.getPassword(), UserDAO.getInstance().getUser(user2.getEmail()).getPassword());
        assertDoesNotThrow(() ->UserDAO.getInstance().deleteUser(user2.getEmail()));
//        assertThrows(DBException.class ,() -> UserDAO.getInstance().getUser(user2.getEmail()));
    }

    @Test
    void getAll() throws DBException {
        assertDoesNotThrow(() ->UserDAO.getInstance().getAll());
        int i = UserDAO.getInstance().getAll().size();

        assertDoesNotThrow(() ->UserDAO.getInstance().saveUser(user2));
        assertEquals(i+1, UserDAO.getInstance().getAll().size());
        assertDoesNotThrow(() ->UserDAO.getInstance().deleteUser(user2.getEmail()));
        assertEquals(i, UserDAO.getInstance().getAll().size());
    }

    @Test
    void getUser() throws DBException {
        UserDTO temp = UserDAO.getInstance().getUser(user.getEmail());
        assertEquals(user.getEmail(), temp.getEmail());
        assertEquals(user.getNachname(), temp.getNachname());
        assertEquals(user.getVorname(), temp.getVorname());
        assertEquals(user.getPassword(), temp.getPassword());
    }

    @Test
    void deleteUser() throws DBException {
        int i = UserDAO.getInstance().getAll().size();
        assertDoesNotThrow(() -> UserDAO.getInstance().saveUser(user2));
        assertEquals(++i, UserDAO.getInstance().getAll().size());
        assertDoesNotThrow(() -> UserDAO.getInstance().deleteUser(user.getEmail()));
        assertEquals(--i, UserDAO.getInstance().getAll().size());
        assertDoesNotThrow(() -> UserDAO.getInstance().saveUser(user));
        assertEquals(++i, UserDAO.getInstance().getAll().size());
        assertDoesNotThrow(() -> UserDAO.getInstance().deleteUser(user2.getEmail()));
        assertEquals(--i, UserDAO.getInstance().getAll().size());
    }

    @AfterEach
    void tearDown() throws DBException {
        UserDAO.getInstance().deleteUser(user.getEmail());
    }
}