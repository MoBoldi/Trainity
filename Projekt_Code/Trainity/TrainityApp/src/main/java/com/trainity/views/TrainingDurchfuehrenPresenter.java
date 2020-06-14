package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.Uebung;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TrainingDurchfuehrenPresenter {

    @FXML
    private View trainingDurchfuehren;
    @FXML
    private ImageView ImageView;
    @FXML
    private TextField timerAnzeige;

    private static final Image pauseImage = new Image("pictures/pause.jpg");
    private static final Image workOutImage = new Image("pictures/workOut.jpg");

    public void initialize() {
        trainingDurchfuehren.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.PLAY_ARROW.text,
                e -> System.out.println("Play"));
        fab.showOn(trainingDurchfuehren); 
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
            if (ImageView.getImage().equals(workOutImage)) {
                ImageView.setImage(pauseImage);
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    int sekunden = 30;
                    String now = "";
                    Uebung u = new Uebung();
                    public void run() {
                        now = Integer.toString(sekunden--)+"s";
                        u.setName(now);
                        timerAnzeige.textProperty().bindBidirectional(u.nameProperty());
                        if (sekunden <= 0) {
                            timer.cancel();
                            u.clear();
                            ImageView.setImage(workOutImage);
                        }
                        ImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> {
                            timer.cancel();
                            u.clear();
                            event.consume();
                        });
                    }
                }, 1000, 1000);
            } else {
                ImageView.setImage(workOutImage);
            }
            event.consume();
        });

    }

}
