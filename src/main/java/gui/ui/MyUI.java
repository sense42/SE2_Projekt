package gui.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import gui.view.*;
import model.dto.UserDTO;
import services.util.Views;

import javax.servlet.annotation.WebServlet;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Title("CarLook")
@PreserveOnRefresh
public class MyUI extends UI {

    private UserDTO user = null;

    public UserDTO getUser(){
        return user;
    }

    public void setUser(UserDTO user){
        this.user = user;
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        System.out.println("LOG: neues UI-Objekt erzeugt. Session-ID =" + VaadinSession.getCurrent().toString());

        Navigator navigator = new Navigator(this, this);

        navigator.addView(Views.MAIN, RegisterView.class);
        navigator.addView(Views.LOGIN, LoginView.class);
        navigator.addView(Views.FAHRZEUGSUCHE, SuchView.class);
        navigator.addView(Views.RESERVIERUNGEN, ReservierungenView.class);
        navigator.addView(Views.INSERIEREN, InserierenView.class);
        navigator.addView(Views.INSERATE, InserateView.class);

        UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}
