package test;

import model.dao.DBException;
import model.dao.EndkundeDAO;
import model.dao.UserDAO;
import model.dto.EndkundeDTO;
import model.dto.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndkundeDAOTest {
    UserDTO user;
    EndkundeDTO endk;
    UserDTO user2;
    EndkundeDTO endk2;

    @BeforeEach
    void setUp() {
        user = new UserDTO();
        user.setEmail("JUnitTest@test.de");
        user.setVorname("JUnitVorname");
        user.setNachname("JUnitNachname");
        user.setPassword("JUnitPw123");

        endk = new EndkundeDTO();
        endk.setEmail(user.getEmail());
        endk.setVorname(user.getVorname());
        endk.setNachname(user.getNachname());
        endk.setPassword(user.getPassword());

        user2 = new UserDTO();
        user2.setEmail("JUnitTest2@test.de");
        user2.setVorname("JUnitVorname2");
        user2.setNachname("JUnitNachname2");
        user2.setPassword("JUnitPw123123");

        endk2 = new EndkundeDTO();
        endk2.setEmail(user2.getEmail());
        endk2.setVorname(user2.getVorname());
        endk2.setNachname(user2.getNachname());
        endk2.setPassword(user2.getPassword());

//        assertThrows(DBException.class, () -> UserDAO.getInstance().getUser(user.getEmail()));
//        assertThrows(DBException.class, () -> UserDAO.getInstance().getUser(user2.getEmail()));

        assertDoesNotThrow(() -> UserDAO.getInstance().saveUser(user));
        assertDoesNotThrow(() -> EndkundeDAO.getInstance().saveEndkunde(endk));
    }


    @Test
    void getInstance() {
        EndkundeDAO endkundeDAO = EndkundeDAO.getInstance();
        assertNotNull(endkundeDAO);
        assertEquals(endkundeDAO, EndkundeDAO.getInstance());
    }

    @Test
    void saveUser() throws DBException {
        assertDoesNotThrow(() ->UserDAO.getInstance().saveUser(user2));
        assertDoesNotThrow(() ->EndkundeDAO.getInstance().saveEndkunde(endk2));

        assertEquals(endk2.getEmail(), EndkundeDAO.getInstance().getEndkunde(endk2.getEmail()).getEmail());

        assertDoesNotThrow(() ->EndkundeDAO.getInstance().deleteEnkunde(endk2.getEmail()));
        assertDoesNotThrow(() ->UserDAO.getInstance().deleteUser(user2.getEmail()));

//        assertThrows(DBException.class ,() -> UserDAO.getInstance().getUser(user2.getEmail()));
    }

    @Test
    void getAll() throws DBException {
        assertDoesNotThrow(() ->EndkundeDAO.getInstance().getAll());
        int i = EndkundeDAO.getInstance().getAll().size();

        assertDoesNotThrow(() ->UserDAO.getInstance().saveUser(user2));
        assertDoesNotThrow(() ->EndkundeDAO.getInstance().saveEndkunde(endk2));

        assertEquals(i+1, EndkundeDAO.getInstance().getAll().size());

        assertDoesNotThrow(() ->EndkundeDAO.getInstance().deleteEnkunde(endk2.getEmail()));
        assertDoesNotThrow(() ->UserDAO.getInstance().deleteUser(user2.getEmail()));

        assertEquals(i, EndkundeDAO.getInstance().getAll().size());
    }

    @Test
    void getUser() throws DBException {
        EndkundeDTO temp = EndkundeDAO.getInstance().getEndkunde(endk.getEmail());
        assertEquals(endk.getEmail(), temp.getEmail());
    }

    @Test
    void deleteUser() throws DBException {
        int i = EndkundeDAO.getInstance().getAll().size();

        assertDoesNotThrow(() -> UserDAO.getInstance().saveUser(user2));
        assertDoesNotThrow(() -> EndkundeDAO.getInstance().saveEndkunde(endk2));

        assertEquals(++i, EndkundeDAO.getInstance().getAll().size());

        assertDoesNotThrow(() -> EndkundeDAO.getInstance().deleteEnkunde(endk.getEmail()));
        assertDoesNotThrow(() -> UserDAO.getInstance().deleteUser(user.getEmail()));

        assertEquals(--i, EndkundeDAO.getInstance().getAll().size());

        assertDoesNotThrow(() -> UserDAO.getInstance().saveUser(user));
        assertDoesNotThrow(() -> EndkundeDAO.getInstance().saveEndkunde(endk));

        assertEquals(++i, EndkundeDAO.getInstance().getAll().size());

        assertDoesNotThrow(() -> EndkundeDAO.getInstance().deleteEnkunde(endk2.getEmail()));
        assertDoesNotThrow(() -> UserDAO.getInstance().deleteUser(user2.getEmail()));

        assertEquals(--i, EndkundeDAO.getInstance().getAll().size());
    }

    @AfterEach
    void tearDown() throws DBException {
        int i = EndkundeDAO.getInstance().getAll().size();;

        assertDoesNotThrow(() -> EndkundeDAO.getInstance().deleteEnkunde(endk.getEmail()));
        assertDoesNotThrow(() -> UserDAO.getInstance().deleteUser(user.getEmail()));

        assertEquals(i - 1, EndkundeDAO.getInstance().getAll().size());
    }

    @Test
    void contains() throws DBException {
        assertTrue(EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(), user.getEmail()));
        assertFalse(EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(), user2.getEmail()));

        assertDoesNotThrow(() -> UserDAO.getInstance().saveUser(user2));
        assertDoesNotThrow(() -> EndkundeDAO.getInstance().saveEndkunde(endk2));

        assertTrue(EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(), user.getEmail()));
        assertTrue(EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(), user2.getEmail()));

        assertDoesNotThrow(() -> EndkundeDAO.getInstance().deleteEnkunde(endk2.getEmail()));
        assertDoesNotThrow(() -> UserDAO.getInstance().deleteUser(user2.getEmail()));

        assertTrue(EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(), user.getEmail()));
        assertFalse(EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(), user2.getEmail()));
    }
}