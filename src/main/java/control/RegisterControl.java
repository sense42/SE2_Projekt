package control;

import model.dao.DBException;
import model.dao.EndkundeDAO;
import model.dao.UserDAO;
import model.dao.VertrieblerDAO;
import model.dto.EndkundeDTO;
import model.dto.UserDTO;
import model.dto.VertrieblerDTO;
import services.util.RegistrationResult;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterControl {

    public static RegistrationResult registerUser (UserDTO user, String passwordConfirm){
        // Name check
        if (user.getVorname().length() < 1){
            return RegistrationResult.FIRSTNAME_EMPTY;
        }
        if (user.getVorname().length() < 2){
            return RegistrationResult.FIRSTNAME_SHORT;
        }
        if (user.getNachname().length() < 1){
            return RegistrationResult.SURNAME_EMPTY;
        }
        if (user.getNachname().length() < 2){
            return RegistrationResult.SURNAME_SHORT;
        }// eMail check
        if (user.getEmail().length() < 1){
            return RegistrationResult.EMAIL_EMPTY;
        }
        if (!user.getEmail().contains("@")){
            return  RegistrationResult.EMAIL_NOT_VALID;
        }
        if (!uniqueEMail(user.getEmail())){
            return  RegistrationResult.EMAIL_ALREADY_EXISTS;
        }// Password check
        if (user.getPassword().length()<1){
            return  RegistrationResult.PASSWORD_EMPTY;
        }
        if (user.getPassword().length()<7){
            return  RegistrationResult.PASSWORD_SHORT;
        }
        if (!user.getPassword().equals(passwordConfirm)){
            return  RegistrationResult.PASSWORD_DIFFERENT;
        }
       try {
            UserDAO.getInstance().saveUser(user);
            // hier suche nach erkennungsmerkmal des Vertrieblers mit !CaseInsensitive!
            if (Pattern.compile("@carlook.de", Pattern.CASE_INSENSITIVE).matcher(user.getEmail()).find()) {
                // Erkennung
                VertrieblerDTO vertriebler = new VertrieblerDTO(user.getEmail());
                return RegisterVertriebler(vertriebler);
            } else {
                // wenn kein Vertriebler dann Endkunde
                EndkundeDTO endkunde = new EndkundeDTO(user.getEmail());
                return RegisterEndkunde(endkunde);
            }
        } catch (DBException e) {
            e.printStackTrace();
            return RegistrationResult.UNEXPECTED_ERROR;
        }
    }



    private static boolean uniqueEMail(String email){
        boolean unique = true;
        List<UserDTO> list = null;
        // TODO: bessere Fehlerbehandlung
        try {
            list = UserDAO.getInstance().getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }

        for (UserDTO userDTO : list) {
            if (userDTO.getEmail().equals(email)) {
                unique = false;
                break;
            }
        }
        return unique;
    }

    private static RegistrationResult RegisterEndkunde(EndkundeDTO endkunde) throws DBException {
        // TODO: prüfen und fehlerhandling
        EndkundeDAO.getInstance().saveEndkunde(endkunde);
        return RegistrationResult.ENDKUNDE_REGISTERED;
    }

    private static RegistrationResult RegisterVertriebler(VertrieblerDTO vertriebler) throws DBException {
        // TODO: prüfen und Fehlerhandling
        VertrieblerDAO.getInstance().saveVertriebler(vertriebler);
        return RegistrationResult.VERTRIEBLER_REGISTERED;
    }

}
