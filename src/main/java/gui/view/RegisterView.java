package gui.view;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import control.RegisterControl;
import gui.components.CarLookPageHeader;
import gui.components.OpticalFrame;
import gui.ui.MyUI;
import model.dao.DBException;
import model.dao.EndkundeDAO;
import model.dto.UserDTO;
import services.util.RegistrationResult;
import services.util.Views;
import services.validator.Validators;

import java.util.regex.Pattern;

public class RegisterView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UserDTO user = ((MyUI) UI.getCurrent()).getUser();
        if (user != null){
            try {
                if (EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(), user.getEmail())) {
                    UI.getCurrent().getNavigator().navigateTo(Views.FAHRZEUGSUCHE);
                } else
                    UI.getCurrent().getNavigator().navigateTo(Views.INSERIEREN);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } else {
            this.setup();
        }
    }

    public void setup() {

        CarLookPageHeader pageHeader = new CarLookPageHeader("Willkommen auf unserem Fahrzeugportal");
        OpticalFrame frame = new OpticalFrame();
        UserDTO user = new UserDTO();

        TextField vornameField = new TextField();
        vornameField.setPlaceholder("Vorname");
        TextField nachnameField = new TextField();
        nachnameField.setPlaceholder("Nachname");
        TextField emailField = new TextField();
        emailField.setPlaceholder("E-Mail");
        PasswordField passwordField = new PasswordField();
        passwordField.setPlaceholder("Passwort");

        PasswordField passwordConfirm = new PasswordField();
        passwordConfirm.setPlaceholder("Passwort bestätigen");

        Button registerButton = new Button("Registrieren");

        Button loginButton = new Button("zum Log-in");
        loginButton.addStyleName(ValoTheme.BUTTON_LINK);
        registerButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        Validator<String> nameValidator = new Validator<String>() {
            @Override
            public ValidationResult apply(String s, ValueContext valueContext) {
                if (s.equals(""))
                    return ValidationResult.error("Bitte Füllen Sie dieses Feld aus.");
                return ValidationResult.ok();
            }
        };

        Pattern PwPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
        Validator<String> pwValidator = new Validator<String>() {
            @Override
            public ValidationResult apply(String s, ValueContext valueContext) {
                if (s.length() < 8)
                    return ValidationResult.error("Das Passwort muss mindestens aus 8 Zeichen bestehen!");
                if (!PwPattern.matcher(s).matches())
                    return ValidationResult.error("Das Passwort muss mindestens aus einem kleinbuchstaben, einem Großbuchstaben und einer Ziffer bestehen!");
                else return ValidationResult.ok();
            }
        };

        Validator<String> pwConfirmValidator = new Validator<String>() {
            @Override
            public ValidationResult apply(String s, ValueContext valueContext) {
                if (!s.equals(passwordField.getValue()))
                    return ValidationResult.error("Die Passwörter stimmen nicht überein");
                return ValidationResult.ok();
            }
        };
        Binder<UserDTO> binder = new Binder(UserDTO.class);
        binder.forField(vornameField).withValidator(nameValidator).bind(UserDTO::getVorname, UserDTO::setVorname);
        binder.forField(nachnameField).withValidator(nameValidator).bind(UserDTO::getNachname, UserDTO::setNachname);
        binder.forField(emailField).withValidator(Validators.getemailValidator()).bind(UserDTO::getEmail, UserDTO::setEmail);
        binder.forField(passwordField).withValidator(pwValidator).bind(UserDTO::getPassword, UserDTO::setPassword);
        binder.forField(passwordConfirm).withValidator(pwConfirmValidator).bind(UserDTO::getPassword, UserDTO::setPassword);

        loginButton.addClickListener(event -> {
            MyUI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
        });

        registerButton.addClickListener(event -> {
            UserDTO uebergabeUser = new UserDTO(emailField.getValue(), passwordField.getValue(), vornameField.getValue(), nachnameField.getValue());
            RegistrationResult regResult = RegisterControl.registerUser(user, passwordConfirm.getValue());
            Notification.show(regResult.getMessage());

            if (regResult == RegistrationResult.ENDKUNDE_REGISTERED || regResult == RegistrationResult.VERTRIEBLER_REGISTERED) {
                // hier wenige sekunden warten
                UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
            } else {
                Notification.show(regResult.getMessage());
            }
        });

        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        buttonHolder.addComponents(loginButton, registerButton);


        frame.addComponents(emailField, vornameField, nachnameField, passwordField, passwordConfirm, buttonHolder);


        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        this.addComponents(pageHeader, frame);
    }
}
