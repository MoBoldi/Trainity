package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrainingDurchfuehrenPresenter {

    @FXML
    private View trainingDurchfuehren;
    @FXML
    private ImageView ImageView;

    private static final Image pauseImage = new Image("pictures/pause.jpg"); //get.Image = javafx.scene.image.Image@41578d44
    private static final Image workOutImage = new Image("pictures/workOut.jpg"); //get.Image = javafx.scene.image.Image@6d9e477d
    @FXML
    private VBox outterVBox;
    @FXML
    private VBox innerVBox;
    @FXML
    private HBox labelImageViewHBox;
    @FXML
    private HBox InputHBox1;
    @FXML
    private HBox InputHBox11;
    @FXML
    private HBox saveButtonHBox;
    @FXML
    private HBox ExerciseBox1111;
    @FXML
    private Label label12111;
    @FXML
    private Label rep12111;
    @FXML
    private HBox ExerciseBox11111;
    @FXML
    private Label label121111;
    @FXML
    private Label rep121111;
    @FXML
    private HBox ExerciseBox11112;
    @FXML
    private Label label121112;
    @FXML
    private Label rep121112;
    @FXML
    private Label titelUebung;
    @FXML
    private Label wiederholungen;


    public void initialize() {
        trainingDurchfuehren.setShowTransitionFactory(BounceInRightTransition::new);
        
        /*FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.PLAY_ARROW.text,
                e -> System.out.println("Play"));
        fab.showOn(trainingDurchfuehren); */
        ImageView.setImage(workOutImage);

        trainingDurchfuehren.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Training durchführen");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e
                        -> System.out.println("Favorite")));
            }
        });

        ImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
          //  System.out.println(UserSession.instance); //USER ID ABRUFEN
            if (ImageView.getImage().equals(workOutImage)) {
                ImageView.setImage(pauseImage);
            } else {
                ImageView.setImage(workOutImage);
            }
            event.consume();
        });

    }

}
