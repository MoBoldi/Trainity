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
import static com.trainity.Trainity.TRAINING_DURCHFUEHREN_VIEW;
import static com.trainity.Trainity.UEBUNG_AUSWAEHLEN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_NotEditable_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_VIEW;
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
        final Item wochenplan = new ViewItem("Wochenplan", MaterialDesignIcon.DASHBOARD.graphic(), WOCHENPLAN_VIEW);
        final Item bildAuswaehlen = new ViewItem("Bild auswählen", MaterialDesignIcon.DASHBOARD.graphic(), BILD_AUSWAEHLEN_VIEW);
        final Item einheitBearbeiten = new ViewItem("Einheit bearbeiten", MaterialDesignIcon.DASHBOARD.graphic(), EINHEIT_BEARBEITEN_VIEW);
        final Item einheitErstellen = new ViewItem("Einheit erstellen", MaterialDesignIcon.DASHBOARD.graphic(), EINHEIT_ERSTELLEN_VIEW);

        final Item trainingDurchfuehren = new ViewItem("Training durchführen", MaterialDesignIcon.DASHBOARD.graphic(), TRAINING_DURCHFUEHREN_VIEW);
        final Item uebungAuswaehlen = new ViewItem("Übung auswählen", MaterialDesignIcon.DASHBOARD.graphic(), UEBUNG_AUSWAEHLEN_VIEW);
        final Item uebungBearbeiten = new ViewItem("Übung bearbeiten", MaterialDesignIcon.DASHBOARD.graphic(), UEBUNG_BEARBEITEN_VIEW);
        final Item uebungNotBearbeiten = new ViewItem("Übung Notbearbeiten", MaterialDesignIcon.DASHBOARD.graphic(), UEBUNG_BEARBEITEN_NotEditable_VIEW);

        drawer.getItems().addAll(startseiteItem, eigeneTrainings, wochenplan, bildAuswaehlen, einheitBearbeiten, trainingDurchfuehren, uebungAuswaehlen, uebungBearbeiten,uebungNotBearbeiten, einheitErstellen );
        
        if (Platform.isDesktop()) {
            final Item quitItem = new Item("Logout", MaterialDesignIcon.EXIT_TO_APP.graphic());
            quitItem.selectedProperty().addListener((obs, ov, nv) -> {
                //us.cleanUserSession()); //Do müsste die Session gecleared werden aber iwie funktionierts ned und i was nd warum
                if (nv) {
                    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
                }
            });
            drawer.getItems().add(quitItem);
        }
    }
}