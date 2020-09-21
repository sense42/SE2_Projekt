package gui.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class OpticalFrame extends Panel {

    VerticalLayout layout;

    public OpticalFrame(VerticalLayout layout) {
        this.layout = layout;
        this.layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        this.setContent(this.layout);
        this.setSizeUndefined();
    }

    public OpticalFrame() {
        this(new VerticalLayout());
    }

    public void addComponent(Component c) {
        this.layout.addComponent(c);
    }

    public void addComponents(Component... components) {
        Component[] var2 = components;
        int var3 = components.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Component c = var2[var4];
            this.addComponent(c);
        }
    }
}
