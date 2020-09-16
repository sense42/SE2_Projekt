package gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import control.RegisterControl;

import gui.components.CarLookPageHeader;
import gui.components.OpticalFrame;
import gui.ui.MyUI;
import model.dto.UserDTO;
import services.util.RegistrationResult;
import services.util.Views;

public class RegisterView extends VerticalLayout implements View {
    public void enter(ViewChangeListener.ViewChangeEvent event){
        this.setup();
    }

    public void setup() {
        CarLookPageHeader pageHeader = new CarLookPageHeader("Willkommen auf unserem Fahrzeugportal");
        OpticalFrame frame = new OpticalFrame();

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

        //TODO: hier den Link anpassen zu Login
        loginButton.addClickListener(event -> {
            MyUI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
        });

        registerButton.addClickListener(event -> {
            UserDTO user = new UserDTO(emailField.getValue(), passwordField.getValue(), vornameField.getValue(), nachnameField.getValue());
            RegistrationResult regResult = RegisterControl.registerUser(user, passwordConfirm.getValue());
            Notification.show(regResult.getMessage());

            if (regResult == RegistrationResult.ENDKUNDE_REGISTERED || regResult == RegistrationResult.VERTRIEBLER_REGISTERED){
                // hier wenige sekunden warten
                MyUI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
            } else {
                Notification.show(regResult.getMessage());
            }
        });

        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        buttonHolder.addComponents(loginButton, registerButton);


        frame.addComponents(emailField, vornameField, nachnameField, passwordField, passwordConfirm,buttonHolder);

        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        this.setSizeFull();
        this.addComponents(pageHeader, frame);
    }
}
