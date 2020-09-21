package gui.view;

import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.datefield.DateResolution;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import control.ReservierungControl;
import gui.components.CarLookMenu;
import gui.components.CarLookPageHeader;
import gui.ui.MyUI;
import model.dao.DBException;
import model.dao.EndkundeDAO;
import model.dao.InseratDAO;
import model.dto.InseratDTO;
import model.dto.InseratService;
import model.dto.UserDTO;
import services.util.ReservierungsResult;
import services.util.Roles;
import services.util.Views;

import java.time.LocalDate;
import java.util.List;

public class SuchView extends VerticalLayout implements View {

    private InseratDTO selektiertInserat = null;

    private List<InseratDTO> alleInserate;

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

        CarLookPageHeader pageHeader = new CarLookPageHeader("Willkommen bei CarLook Herr/Frau " + user.getNachname());
//        OpticalFrame frame = new OpticalFrame();
        CarLookMenu lookMenu = new CarLookMenu();

        try {
            alleInserate = InseratDAO.getInstance().getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }

        TextField suchFeld = new TextField();
        suchFeld.setPlaceholder("bestimmte Merkmale?");

//        suchfeld.
        ComboBox<String> markeSuchFeld = new ComboBox<>();
        markeSuchFeld.setPlaceholder("welche Marke?");

        InseratService inseratService = InseratService.getInstance();
        markeSuchFeld.setDataProvider(inseratService::fetch, inseratService::count);

        Button suchButton = new Button("Suchen", VaadinIcons.SEARCH);
        suchButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        DateField date = new DateField();
        date.setResolution(DateResolution.YEAR);
        date.setValue(LocalDate.now());

        CheckBox bjFilter = new CheckBox("nach Baujahr Filtern");

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponents(markeSuchFeld, suchFeld, date, suchButton);

        Grid<InseratDTO> inserate = new Grid<>();
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
                if (inserate.getSelectedItems() == null) {
                    return;
                } else {
                    ReservierungsResult result = ReservierungControl.reserviere(selektiertInserat);
                    Notification.show(result.getMessage());
                }
            }
        });
        reserviereButton.setVisible(false);
        reserviereButton.setEnabled(false);

        suchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                List<InseratDTO> liste = null;
                try {
                    if (bjFilter.getValue() == true) {
                        liste = InseratDAO.getInstance().getSuchInserate(markeSuchFeld.getValue(), suchFeld.getValue(), date.getValue().getYear());
                    } else {
                        liste = InseratDAO.getInstance().getSuchInserate(markeSuchFeld.getValue(), suchFeld.getValue(), -1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //clear previous inputs
                inserate.setItems();
                //add listitems to the grid
                inserate.setItems(liste);
//                addComponent(inserate);

                if (liste.size() > 0) {
                    //Buche Button under the Grid
                    reserviereButton.setVisible(true);
                    reserviereButton.setEnabled(true);
                } else{
                    reserviereButton.setVisible(false);
                    reserviereButton.setEnabled(false);
                }
            }
        });

        inserate.addItemClickListener(new ItemClickListener<InseratDTO>() {
            @Override
            public void itemClick(Grid.ItemClick<InseratDTO> itemClick) {
                selektiertInserat = itemClick.getItem();
            }
        });

        this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        this.addComponents(lookMenu, pageHeader, hLayout, bjFilter, inserate, reserviereButton);

    }
}
