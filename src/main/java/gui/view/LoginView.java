package gui.view;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import control.LoginControl;
import gui.components.CarLookPageHeader;
import gui.components.OpticalFrame;
import gui.ui.MyUI;
import model.dto.UserDTO;
import services.util.LoginResult;
import services.util.Views;
import services.validator.Validators;


public class LoginView extends VerticalLayout implements View {


    public void enter(ViewChangeListener.ViewChangeEvent event) {
        UserDTO user = ((MyUI) UI.getCurrent()).getUser();
        if (user != null){
            UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
        } else {
            this.setup();
        }
    }

    public void setup() {

        CarLookPageHeader pageHeader = new CarLookPageHeader("Log-In");
        OpticalFrame frame = new OpticalFrame();


        TextField emailField = new TextField();
        emailField.setPlaceholder("E-Mail");
        PasswordField passwordField = new PasswordField();
        passwordField.setPlaceholder("Passwort");

        Binder<UserDTO> binder = new Binder(UserDTO.class);
        binder.forField(emailField).withValidator(Validators.getemailValidator()).bind(UserDTO::getEmail, UserDTO::setEmail);

        Button registerButton = new Button("zur Registrierung");
        registerButton.addStyleName(ValoTheme.BUTTON_LINK);

        Button loginButton = new Button("Log-in");

        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.addClickListener(event -> {
            // Navigate to Suche or inserieren
            LoginResult loginresult = LoginControl.Loginuser(emailField.getValue(), passwordField.getValue());
            Notification.show(loginresult.getMessage());

            if (loginresult == LoginResult.LOGIN_ENDKUNDE_SUCCEEDED) {
                UI.getCurrent().getNavigator().navigateTo(Views.FAHRZEUGSUCHE);
            } else if  (loginresult == LoginResult.LOGIN_VERTRIBLER_SUCCEEDED){
                UI.getCurrent().getNavigator().navigateTo(Views.INSERIEREN);
            }
        });

        registerButton.addClickListener(event -> {
            // Navigate back to registration
            UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
        });

        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        buttonHolder.addComponents(registerButton, loginButton);

        frame.addComponents(emailField, passwordField, buttonHolder);

        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        this.addComponents(pageHeader, frame);
    }
}
