package gui.components;


import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import gui.ui.MyUI;
import model.dto.EndkundeDTO;
import model.dto.VertrieblerDTO;
import services.util.Roles;
import services.util.UserTypes;
import services.util.Views;

public class CarLookMenu extends HorizontalLayout {
    MenuBar bar;
    public CarLookMenu(){
        bar = new MenuBar();
        bar.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
        this.setSizeFull();

        if (MyUI.getCurrent().getSession().getAttribute(Roles.CURRENT_USERTYPE) == UserTypes.Endkunde) {
            System.out.println("Endkunde");
            buildEndkundeMenu();
        }
        else if (MyUI.getCurrent().getSession().getAttribute(Roles.CURRENT_USERTYPE) == UserTypes.Vertriebler){
            System.out.println("Vertriebler");
            buildVertrieblerMenu();
        }
        else {
            System.out.println("unbestimmt!!!");
        }

        this.addComponent(bar);
        this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
    }

    private void  buildEndkundeMenu(){
        MenuBar.MenuItem suche = bar.addItem("Fahrzeugsuche", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                UI.getCurrent().getNavigator().navigateTo(Views.FAHRZEUGSUCHE);
            }
        });
        MenuBar.MenuItem reservierungen = bar.addItem("Reservierungen", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                UI.getCurrent().getNavigator().navigateTo(Views.RESERVIERUNGEN);
            }
        });
    }

    private void buildVertrieblerMenu(){
        MenuBar.MenuItem inserieren = bar.addItem("Inserieren", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                UI.getCurrent().getNavigator().navigateTo(Views.INSERIEREN);
            }
        });
        MenuBar.MenuItem inserate = bar.addItem("Inserate", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                UI.getCurrent().getNavigator().navigateTo(Views.INSERATE);
            }
        });


    }

}
