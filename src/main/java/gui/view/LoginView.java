package gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import control.LoginControl;
import gui.components.CarLookPageHeader;
import gui.components.OpticalFrame;
import gui.ui.MyUI;
import services.util.LoginResult;
import services.util.Roles;
import services.util.Views;


public class LoginView extends VerticalLayout implements View{


    public void enter(ViewChangeListener.ViewChangeEvent event){
        this.setup();
    }

    public void setup() {
        CarLookPageHeader pageHeader = new CarLookPageHeader("Log-In");
        OpticalFrame frame = new OpticalFrame();

        TextField emailField = new TextField();
        emailField.setPlaceholder("E-Mail");
        PasswordField passwordField = new PasswordField();
        passwordField.setPlaceholder("Passwort");


        Button registerButton = new Button("zur Registrierung");
        Button loginButton = new Button("Log-in");

        loginButton.addClickListener(event -> {
            // Navigate to Suche or inserieren
            LoginResult login = LoginControl.Loginuser(emailField.getValue(), passwordField.getValue());
            Notification.show(login.getMessage());
            if (login == LoginResult.LOGIN_ENDKUNDE_SUCCEEDED){

                MyUI.getCurrent().getNavigator().navigateTo(Views.FAHRZEUGSUCHE);
            } else {
                MyUI.getCurrent().getNavigator().navigateTo(Views.INSERIEREN);
            }
        });

        registerButton.addClickListener(event -> {
            // Navigate back to registration
            MyUI.getCurrent().getNavigator().navigateTo(Views.MAIN);
        });

        HorizontalLayout buttonHolder = new HorizontalLayout();
        buttonHolder.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        buttonHolder.addComponents(registerButton, loginButton);

        frame.addComponents(emailField, passwordField,buttonHolder);

        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        this.setSizeFull();
        this.addComponents(pageHeader, frame);

    }
}
