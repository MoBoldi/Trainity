package com.trainity;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static com.trainity.Trainity.EIGENE_TRAININGS_VIEW;
import static com.trainity.Trainity.STARTSEITE_VIEW;
import static com.trainity.Trainity.WOCHENPLAN_VIEW;
import static com.trainity.Trainity.BILD_AUSWAEHLEN_VIEW;
import static com.trainity.Trainity.EINHEIT_BEARBEITEN_VIEW;
import static com.trainity.Trainity.EINHEIT_ERSTELLEN_VIEW;
import static com.trainity.Trainity.UEBUNG_ALL;
import static com.trainity.Trainity.UEBUNG_AUSWAEHLEN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_NotEditable_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_VIEW;
import static com.trainity.UserSession.instance;
import javafx.scene.image.Image;

public class DrawerManager {

    public static void buildDrawer(MobileApplication app) {
        NavigationDrawer drawer = app.getDrawer();
        
        NavigationDrawer.Header header = new NavigationDrawer.Header("Trainity",
                "Create your own Trainings",
                new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
        drawer.setHeader(header);
        
        final Item startseiteItem = new ViewItem("Startseite", MaterialDesignIcon.HOME.graphic(), STARTSEITE_VIEW, ViewStackPolicy.SKIP);
        final Item eigeneTrainings = new ViewItem("Eigene Trainings", MaterialDesignIcon.DASHBOARD.graphic(), EIGENE_TRAININGS_VIEW);

        ///final Item trainingDurchfuehren = new ViewItem("Training durchführen", MaterialDesignIcon.DASHBOARD.graphic(), TRAINING_DURCHFUEHREN_VIEW);

        drawer.getItems().addAll(startseiteItem, eigeneTrainings );
        
       if (Platform.isDesktop()) {
            final Item quitItem = new Item("Logout", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                instance.cleanUserSession();
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}