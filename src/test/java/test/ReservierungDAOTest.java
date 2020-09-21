package test;

import model.dao.DBException;
import model.dao.ReservierungDAO;
import model.dao.UserDAO;
import model.dto.ReservierungDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservierungDAOTest {
    ReservierungDTO reservierungDTO;
    ReservierungDTO reservierungDTO2;

    @BeforeEach
    void setUp() throws DBException {
        reservierungDTO = new ReservierungDTO();
        reservierungDTO.setInseratID(4);
        reservierungDTO.setKundeEmail("testuser@test.de");

        reservierungDTO2 = new ReservierungDTO();
        reservierungDTO2.setInseratID(6);
        reservierungDTO2.setKundeEmail("testuser@test.de");

        ReservierungDAO.getInstance().saveReservierung(reservierungDTO);
    }

    @AfterEach
    void tearDown() throws DBException {
        ReservierungDAO.getInstance().deleteReservierung(reservierungDTO.getKundeEmail(),reservierungDTO.getInseratID());
    }

    @Test
    void getInstance() {

        ReservierungDAO reservierungDAO = ReservierungDAO.getInstance();
        assertNotNull(reservierungDAO);
        assertEquals(reservierungDAO, ReservierungDAO.getInstance());
    }

    @Test
    void saveReservierung() throws DBException {
        int i = ReservierungDAO.getInstance().getReservierungDTOS(reservierungDTO.getKundeEmail()).size();
        assertDoesNotThrow(() ->ReservierungDAO.getInstance().saveReservierung(reservierungDTO2));

        assertEquals(i+1, ReservierungDAO.getInstance().getReservierungDTOS(reservierungDTO.getKundeEmail()).size());
        assertTrue(ReservierungDAO.getInstance().contains(ReservierungDAO.getInstance().getAll(), reservierungDTO2.getKundeEmail(), reservierungDTO2.getInseratID()));

        assertDoesNotThrow(() ->ReservierungDAO.getInstance().deleteReservierung(reservierungDTO2.getKundeEmail(),reservierungDTO2.getInseratID()));
        assertEquals(i, ReservierungDAO.getInstance().getReservierungDTOS(reservierungDTO.getKundeEmail()).size());
    }

    @Test
    void getAll() throws DBException {
        int i = ReservierungDAO.getInstance().getAll().size();
        assertDoesNotThrow(() ->ReservierungDAO.getInstance().saveReservierung(reservierungDTO2));
        assertEquals(i+1, ReservierungDAO.getInstance().getAll().size());
        assertDoesNotThrow(() ->ReservierungDAO.getInstance().deleteReservierung(reservierungDTO2.getKundeEmail(),reservierungDTO2.getInseratID()));
        assertEquals(i, ReservierungDAO.getInstance().getAll().size());
    }

    @Test
    void getReservierungDTOS() throws DBException {
        int i = ReservierungDAO.getInstance().getReservierungDTOS(reservierungDTO.getKundeEmail()).size();
        assertDoesNotThrow(() ->ReservierungDAO.getInstance().saveReservierung(reservierungDTO2));

        assertEquals(i+1, ReservierungDAO.getInstance().getReservierungDTOS(reservierungDTO.getKundeEmail()).size());

        assertDoesNotThrow(() ->ReservierungDAO.getInstance().deleteReservierung(reservierungDTO2.getKundeEmail(),reservierungDTO2.getInseratID()));
        assertEquals(i, ReservierungDAO.getInstance().getReservierungDTOS(reservierungDTO.getKundeEmail()).size());
    }

    @Test
    void deleteReservierung() throws DBException {
        int i = ReservierungDAO.getInstance().getAll().size();
        assertDoesNotThrow(() ->ReservierungDAO.getInstance().saveReservierung(reservierungDTO2));

        assertEquals(i+1, ReservierungDAO.getInstance().getAll().size());
        assertTrue(ReservierungDAO.getInstance().contains(ReservierungDAO.getInstance().getAll(), reservierungDTO2.getKundeEmail(), reservierungDTO2.getInseratID()));

        assertDoesNotThrow(() ->ReservierungDAO.getInstance().deleteReservierung(reservierungDTO2.getKundeEmail(),reservierungDTO2.getInseratID()));
        assertEquals(i, ReservierungDAO.getInstance().getAll().size());

    }
}