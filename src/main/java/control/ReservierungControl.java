package control;

import gui.ui.MyUI;
import model.dao.DBException;
import model.dao.InseratDAO;
import model.dao.ReservierungDAO;
import model.dto.InseratDTO;
import model.dto.ReservierungDTO;
import model.dto.UserDTO;
import services.util.ReservierungsResult;
import services.util.Roles;

import java.util.ArrayList;
import java.util.List;

public class ReservierungControl {

    public static ReservierungsResult reserviere(InseratDTO inseratDTO) {
        UserDTO currentuser = (UserDTO) MyUI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
        List<InseratDTO> inseratDTOList = getReservierteInserate(currentuser.getEmail());

        for (int i = 0; i < inseratDTOList.size(); i++) {
            if (inseratDTOList.get(i).getId() == inseratDTO.getId()) {
                return ReservierungsResult.INSERAT_ALREADY_RESERVED;
            }
        }
        if (inseratDTOList.contains(inseratDTO)) {
            return ReservierungsResult.INSERAT_ALREADY_RESERVED;
        } else {

            try {
                ReservierungDAO.getInstance().saveReservierung(new ReservierungDTO(currentuser.getEmail(), inseratDTO.getId()));
                return ReservierungsResult.RESERVATION_SUCCEEDED;
            } catch (DBException e) {
                e.printStackTrace();
                return ReservierungsResult.UNEXPECTED_ERROR;
            }
        }
    }

    public static List<InseratDTO> getReservierteInserate(String email) {
        List<ReservierungDTO> reservierungDTOList;
        List<InseratDTO> inseratDTOList = new ArrayList<>();

        try {
            reservierungDTOList = ReservierungDAO.getInstance().getReservierungDTOS(email);

            for (ReservierungDTO reservierungDTO : reservierungDTOList) {
                inseratDTOList.add(InseratDAO.getInseratDTObyID(reservierungDTO.getInseratID()));
            }
        } catch (DBException dbException) {
            dbException.printStackTrace();
        }
        return inseratDTOList;
    }


}
