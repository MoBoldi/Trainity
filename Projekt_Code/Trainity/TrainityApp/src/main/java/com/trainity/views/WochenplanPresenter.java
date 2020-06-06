package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static java.awt.PageAttributes.ColorType.COLOR;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class WochenplanPresenter {

    @FXML
    private View wochenplan;
    @FXML
    private VBox outerBox;
    @FXML
    private VBox outerHeadingBox;
    @FXML
    private HBox innerHeadingBox;
    @FXML
    private Label labelHeading;
    @FXML
    private HBox DayHBox;
    @FXML
    private VBox DayInnerVBox;
    @FXML
    private Label label12;
    @FXML
    private Label label121;
    @FXML
    private Label label1211;
    @FXML
    private Label label1212;
    @FXML
    private Label label1214;
    @FXML
    private Label label1213;
    @FXML
    private Label label12141;
    @FXML
    private HBox saveButtonHBox;
    @FXML
    private Button ButtonSave;
    @FXML
    private ImageView imageViewSafes;
    
    private Boolean[] selected = {false, false, false, false, false, false};
    @FXML
    private HBox Monday;
    @FXML
    private HBox Tuesday;
    @FXML
    private HBox Wednesday;
    @FXML
    private HBox Thursday;
    @FXML
    private HBox Friday;
    @FXML
    private HBox Saturday;
    @FXML
    private HBox Sunday;
    
    public void initialize() {
        wochenplan.setShowTransitionFactory(BounceInRightTransition::new);
        
        wochenplan.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Wochenplan");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });
    
}

    @FXML
    private void changeColor(MouseEvent event) {
        //geklickte HBox speichern 
        HBox source = (HBox) event.getSource();
        //Farbe ändern und in selected speichern 
        switch(source.getId()){
            case "Monday": toggleSelected(source, 0);
            break;
            case "Tuesday": toggleSelected(source, 1);
            break;
            case "Wednesday": toggleSelected(source, 2);
            break;
            case "Thursday": toggleSelected(source, 3);
            break;
            case "Friday": toggleSelected(source, 4);
            break;
            case "Saturday": toggleSelected(source, 5);
            break;
            case "Sunday": toggleSelected(source, 6);
            break;
        }
        System.out.println(source.getId());
    }
    private void toggleSelected(HBox source, int id){
        //Wenn dieser Tag bereits ausgewählt wurde, wird er wieder verworfen
        if (selected[id]){
            selected[id] = false;
            source.setStyle("-fx-background-color:rgb(242, 110, 46); -fx-background-radius:10");
        //Ansonsten wird dieser ausgewählt
        }else {
            selected[id] = true;
            source.setStyle("-fx-background-color:rgb(64, 194, 17); -fx-background-radius:10");
        }
    }

    @FXML
    private void savePlan(ActionEvent event) {
        //Wochenplan hier in DB speichern 
        //Wochenplan ist in selected[] gespeichert
        
    }
}
