package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.ImagePickable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class BildAuswaehlenPresenter {

    @FXML
    private View bildAuswaehlen;
    @FXML
    private VBox outerBox;
    @FXML
    private HBox labelTitleHbox;
    @FXML
    private Label labelTitle;
    @FXML
    private HBox ImageHBox;
    @FXML
    private GridPane imageGridPane;
    @FXML
    private HBox saveButtonHBox;
    @FXML
    private Button ButtonSave;
    @FXML
    private ImageView imageViewSafes;
    
    public void initialize() {
        bildAuswaehlen.setShowTransitionFactory(BounceInRightTransition::new);
        
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info"));
        fab.showOn(bildAuswaehlen);
        
        bildAuswaehlen.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Bild auswählen");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });
        ImagePickable img = new ImagePickable("051-athlete-7.png");
        /*EventHandler clicked = new EventHandler() {
            @Override
            public void handle(Event event) {
                System.out.println(img.getId());
            }
        };
        img.setOnMouseClicked(clicked);*/
        
        imageGridPane.add(img, 0, 1);
    
}


}
