package gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import control.ReservierungControl;
import gui.components.CarLookMenu;
import gui.components.CarLookPageHeader;
import gui.ui.MyUI;
import model.dao.DBException;
import model.dao.EndkundeDAO;
import model.dto.EndkundeDTO;
import model.dto.InseratDTO;
import model.dto.UserDTO;
import services.util.Roles;
import services.util.Views;

import java.util.List;

public class ReservierungenView extends VerticalLayout implements View {

    public void enter(ViewChangeListener.ViewChangeEvent event) {

        UserDTO user = ((MyUI) UI.getCurrent()).getUser();
        if (user != null){
            try {
                if (!EndkundeDAO.getInstance().contains(EndkundeDAO.getInstance().getAll(), user.getEmail())) {
                    UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
                }
                else {
                    this.setup();
                }
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    public void setup() {
        UserDTO user = (UserDTO) MyUI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);

        CarLookPageHeader pageHeader = new CarLookPageHeader("Diese Fahrzeuge haben Sie bereits erfolgreich Registriert");
        CarLookMenu menu = new CarLookMenu();
//        OpticalFrame frame = new OpticalFrame();

        Grid<InseratDTO> inserate = new Grid<>();
        inserate.setSizeFull();
        inserate.addColumn(InseratDTO::getMarke).setCaption("Marke");
        inserate.addColumn(InseratDTO::getBaujahr).setCaption("Baujahr");
        inserate.addColumn(InseratDTO::getBeschreibung).setCaption("Beschreibung");

        inserate.setSelectionMode(Grid.SelectionMode.SINGLE);

        List<InseratDTO> liste = ReservierungControl.getReservierteInserate(user.getEmail());

        //add listitems to the grid
        inserate.setItems(liste);
        addComponent(inserate);

        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        this.addComponents(menu, pageHeader, inserate);
    }
}
