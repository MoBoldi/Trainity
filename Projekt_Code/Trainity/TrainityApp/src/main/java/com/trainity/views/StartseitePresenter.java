package com.trainity.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.Trainity;
import static com.trainity.Trainity.LOGIN_VIEW;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class StartseitePresenter {

    @FXML
    private View startseite;

    @FXML
    private VBox mo;
    @FXML
    private VBox di;
    @FXML
    private VBox mi;
    @FXML
    private VBox don;
    @FXML
    private VBox fr;
    @FXML
    private VBox sa;
    @FXML
    private VBox so;
    @FXML
    private ImageView calendarMo;
    @FXML
    private ImageView checkMo;
    @FXML
    private ImageView calendarDi;
    @FXML
    private ImageView checkDi;
    @FXML
    private ImageView calendarMi;
    @FXML
    private ImageView checkMi;
    @FXML
    private ImageView calendarDo;
    @FXML
    private ImageView checkDo;
    @FXML
    private ImageView calendarFr;
    @FXML
    private ImageView checkFr;
    @FXML
    private ImageView calendarSa;
    @FXML
    private ImageView checkSa;
    @FXML
    private ImageView calendarSo;
    @FXML
    private ImageView checkSo;

    public void initialize() {
        startseite.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Startseite");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));
            }
        });
    }
}
