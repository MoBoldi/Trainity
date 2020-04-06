package com.trainity;

import com.trainity.views.StartseiteView;
import com.trainity.views.LoginView;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Trainity extends MobileApplication {

    public static final String STARTSEITE_VIEW = HOME_VIEW;
    public static final String LOGIN_VIEW = "Login View";
    
    @Override
    public void init() {
        addViewFactory(STARTSEITE_VIEW, () -> new StartseiteView().getView());
        addViewFactory(LOGIN_VIEW, () -> new LoginView().getView());

        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(Trainity.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Trainity.class.getResourceAsStream("/icon.png")));
    }
}
