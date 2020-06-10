package com.trainity.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.BoxDynamischBlauKlein;
import com.trainity.Trainity;
import static com.trainity.Trainity.LOGIN_VIEW;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.util.Date;
import javafx.scene.text.Text;

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
    @FXML
    private VBox nextTraining;
    @FXML
    private VBox lastTraining;
    @FXML
    private Text moText;
    @FXML
    private Text diText;
    @FXML
    private Text miText;
    @FXML
    private Text doText;
    @FXML
    private Text frText;
    @FXML
    private Text saText;
    @FXML
    private Text soText;

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
        Date date=new Date();
        switch (date.getDay()){
            case (1): setDay(mo, moText);
                break;
            case (2): setDay(di, diText);
                break;
            case (3): setDay(mi, miText);
                break;
            case (4): setDay(don, doText);
                break;
            case (5): setDay(fr, frText);
                break;
            case (6): setDay(sa, saText);
                break;
            case (7): setDay(so, soText);
                break;
            default: setDay(mo, moText);
                break;
        }
        //Nächstes Training aus DB holen 
        nextTraining.getChildren().add(new BoxDynamischBlauKlein("Nächste Trainingseinheit", 20, "Plan"));
        
        //Letzte Trainings aus DB holen
        lastTraining.getChildren().add(new BoxDynamischBlauKlein("Letzte Trainingseinheit", 20, "Plan"));
        
        //Geplante Tage aus Tabelle Ziel anzeigen 
        
    }
    
    public void setDay(VBox box, Text text){
        box.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 0);");
        text.setFill(Color.web("#2196f3"));
    }

    
}
