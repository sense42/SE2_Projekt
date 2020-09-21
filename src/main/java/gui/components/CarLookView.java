package gui.components;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class CarLookView implements View {
    CarLookPageHeader pageHeader = null;
    OpticalFrame frame = null;
    VerticalLayout vLayout = null;

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.init();
    }

    public void init() {
        pageHeader = new CarLookPageHeader("Willkommen auf unserem Fahrzeugportal");

        frame = new OpticalFrame();

        vLayout.addComponents(pageHeader, frame);
        vLayout.setSizeUndefined();

        setup();
    }

    public void setup() {
    }

    public void setPageHeaderString(String s) {
        pageHeader.setSmallHeaderString(s);
    }

    public void addComponent(Component c) {
        frame.addComponent(c);
    }

    public void addComponents(Component... components) {
        frame.addComponents(components);
    }

}
