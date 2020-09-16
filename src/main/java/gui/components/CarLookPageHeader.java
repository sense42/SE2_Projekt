package gui.components;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.io.File;

public class CarLookPageHeader extends VerticalLayout {
    Label smallHeader;
    public CarLookPageHeader(String s) {
        Label headerLabel = new Label("<font size =\"7\" style = \"font-family\": \"Courier\">CarLook", ContentMode.HTML);

//        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
//        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/logo.png"));
//        Image image = new Image(null, resource);

        smallHeader = new Label ("<font size =\"5\" style = \"font-family\": \"Courier\">" + s +"", ContentMode.HTML);
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponents(headerLabel,smallHeader);
        this.setSizeUndefined();
    }

    public void setSmallHeaderString(String s){
        smallHeader.setValue("<font size =\"5\" style = \"font-family\": \"Courier\">" + s +"");
    }
}
