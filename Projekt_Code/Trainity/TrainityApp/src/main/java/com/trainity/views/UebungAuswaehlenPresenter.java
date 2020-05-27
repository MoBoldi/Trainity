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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class UebungAuswaehlenPresenter {

    @FXML
    private View uebungAuswaehlen;
    @FXML
    private VBox outerBox;
    @FXML
    private VBox UpperBox;
    @FXML
    private HBox searchHBox;
    @FXML
    private Label labelSearch;
    @FXML
    private HBox inputHBox;
    @FXML
    private TextField inputSearchBar;
    @FXML
    private HBox buttonHBox;
    @FXML
    private Button NewExerciseButton;
    @FXML
    private HBox scrollPaneHBox;
    @FXML
    private ScrollPane AllExercisePane;
    @FXML
    private VBox AllExerciseVBox;
    @FXML
    private HBox ExerciseBox1;
    @FXML
    private Label label12;
    @FXML
    private Label rep12;
    @FXML
    private HBox ExerciseBox2;
    @FXML
    private Label label121;
    @FXML
    private Label rep121;
    @FXML
    private HBox ExerciseBox3;
    @FXML
    private Label label1211;
    @FXML
    private Label rep1211;
    @FXML
    private HBox ExerciseBox4;
    @FXML
    private Label label1212;
    @FXML
    private Label rep1212;
    @FXML
    private HBox ExerciseBox1213;
    @FXML
    private Label label1213;
    @FXML
    private Label rep1213;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void initialize() {
        uebungAuswaehlen.setShowTransitionFactory(BounceInRightTransition::new);
        
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info"));
        fab.showOn(uebungAuswaehlen);
        
        uebungAuswaehlen.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Übung auswählen");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });
    
}


}
