package gui.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.ItemClickListener;
import control.ReservierungControl;
import gui.components.CarLookMenu;
import gui.components.CarLookPageHeader;
import gui.ui.MyUI;
import model.dao.InseratDAO;
import model.dao.ReservierungDAO;
import model.dto.InseratDTO;
import model.dto.ReservierungDTO;
import model.dto.UserDTO;
import services.util.ReservierungsResult;
import services.util.Roles;
import java.time.LocalDate;
import java.util.List;

public class SuchView extends VerticalLayout implements View {
    private InseratDTO selektiertInserat = null;
    public void enter(ViewChangeListener.ViewChangeEvent event){
        this.setup();
    }

    public void setup(){

        UserDTO user = (UserDTO) MyUI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);

        CarLookPageHeader pageHeader = new CarLookPageHeader("Willkommen bei CarLook Herr/Frau " + user.getNachname());
//        OpticalFrame frame = new OpticalFrame();
        CarLookMenu lookMenu = new CarLookMenu();

        TextField suchFeld = new TextField();
        suchFeld.setPlaceholder("Wonach suchen Sie ?");
        Button suchButton = new Button("Suchen", VaadinIcons.SEARCH);


        DateField date = new DateField();
        date.setResolution(DateResolution.YEAR);
        date.setValue(LocalDate.now());

        CheckBox bjFilter = new CheckBox("nach Baujahr Filtern");


        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponents(suchFeld,date, suchButton);

        Grid <InseratDTO> inserate = new Grid<>();
        inserate.setSizeFull();
        inserate.addColumn(InseratDTO::getMarke).setCaption("Marke");
        inserate.addColumn(InseratDTO::getBaujahr).setCaption("Baujahr");
        inserate.addColumn(InseratDTO::getBeschreibung).setCaption("Beschreibung");

        inserate.setSelectionMode(Grid.SelectionMode.SINGLE);
        SingleSelect<InseratDTO> selection = inserate.asSingleSelect();

        Button reserviereButton = new Button("Reservieren");
        reserviereButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (inserate.getSelectedItems() == null){
                    return;
                }
                else{
                    ReservierungsResult result = ReservierungControl.reserviere(selektiertInserat);
                    Notification.show(result.getMessage());
                }
            }
        });

        suchButton.addClickListener(new Button.ClickListener(){
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                List<InseratDTO> liste = null;
                try {
                    // TODO: die Suche komplett machen
                    // TODO: on the fly suche!
                    if (bjFilter.getValue() == true) {
                        liste = InseratDAO.getInstance().getSuchInserate(suchFeld.getValue(), date.getValue().getYear());
                    }
                    else {
                        liste = InseratDAO.getInstance().getSuchInserate(suchFeld.getValue(), -1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //clear previous inputs
                inserate.setItems();
                //add listitems to the grid
                inserate.setItems(liste);
                addComponent(inserate);

                //Buche Button under the Grid
                    addComponent(reserviereButton);
                    setComponentAlignment(reserviereButton , Alignment.MIDDLE_CENTER);
            }
        });

        inserate.addItemClickListener(new ItemClickListener<InseratDTO>() {
            @Override
            public void itemClick(Grid.ItemClick<InseratDTO> itemClick) {
                selektiertInserat = itemClick.getItem();
            }
        });

        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        this.setSizeFull();
        this.addComponents(lookMenu, pageHeader, hLayout, bjFilter, inserate);

    }
}
