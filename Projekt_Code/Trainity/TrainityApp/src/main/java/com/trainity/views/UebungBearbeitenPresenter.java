package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class UebungBearbeitenPresenter {

    @FXML
    private View uebungBearbeiten;
    
    public void initialize() {
        uebungBearbeiten.setShowTransitionFactory(BounceInRightTransition::new);
        
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info"));
        fab.showOn(uebungBearbeiten);
        
        uebungBearbeiten.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Übung bearbeiten");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });
    
}


}
