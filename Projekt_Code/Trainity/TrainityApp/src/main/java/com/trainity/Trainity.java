package com.trainity;

import com.trainity.views.*;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Trainity extends MobileApplication {

    public static final String STARTSEITE_VIEW = HOME_VIEW;
    public static final String LOGIN_VIEW = "Login View";
    public static final String EIGENE_TRAININGS_VIEW = "Eigene Trainings View";
    public static final String WOCHENPLAN_VIEW = "Wochenplan View";
    public static final String BILD_AUSWAEHLEN_VIEW = "Bild auswählen View";
    public static final String EINHEIT_BEARBEITEN_VIEW = "Einheit bearbeiten View";
    public static final String REGISTRIEREN_VIEW = "Registrieren View";
    public static final String TRAINING_DURCHFUEHREN_VIEW = "Training durchführen View";
    public static final String TRAINING_PAUSE_VIEW = "Training Pause View";
    public static final String UEBUNG_AUSWAEHLEN_VIEW = "Übung auswählen View";
    public static final String UEBUNG_BEARBEITEN_VIEW = "Übung bearbeiten View";
    public static final String UEBUNG_BEARBEITEN_NotEditable_VIEW = "Übung bearb N E View";

    
    
    
    @Override
    public void init() {
        addViewFactory(STARTSEITE_VIEW, () -> new StartseiteView().getView());
        addViewFactory(LOGIN_VIEW, () -> new LoginView().getView());
        addViewFactory(EIGENE_TRAININGS_VIEW, () -> new EigeneTrainingsView().getView());
        addViewFactory(WOCHENPLAN_VIEW, () -> new WochenplanView().getView());
        addViewFactory(BILD_AUSWAEHLEN_VIEW, () -> new BildAuswaehlenView().getView());
        addViewFactory(EINHEIT_BEARBEITEN_VIEW, () -> new EinheitBearbeitenView().getView());
        addViewFactory(REGISTRIEREN_VIEW, () -> new RegistrierenView().getView());
        addViewFactory(TRAINING_DURCHFUEHREN_VIEW, () -> new TrainingDurchfuehrenView().getView());
        addViewFactory(TRAINING_PAUSE_VIEW, () -> new TrainingPauseView().getView());
        addViewFactory(UEBUNG_AUSWAEHLEN_VIEW, () -> new UebungAuswaehlenView().getView());
        addViewFactory(UEBUNG_BEARBEITEN_VIEW, () -> new UebungBearbeitenView().getView());
        addViewFactory(UEBUNG_BEARBEITEN_NotEditable_VIEW, () -> new UebungBearbeitenNotEditableView().getView());

        DrawerManager.buildDrawer(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(Trainity.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Trainity.class.getResourceAsStream("/icon.png")));
    }
}
