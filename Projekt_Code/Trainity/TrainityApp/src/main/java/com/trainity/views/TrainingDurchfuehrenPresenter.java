package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.BoxDynamischGruen3;
import static com.trainity.EinheitSession.instanceE;
import com.trainity.Trainingseinheit;
import static com.trainity.Trainity.STARTSEITE_VIEW;
import com.trainity.Uebung;
import static com.trainity.Uebung.printSQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    @FXML
    private TextField timerAnzeige;

    private static final Image pauseImage = new Image("pictures/pause.jpg");
    private static final Image workOutImage = new Image("pictures/workOut.jpg");
    @FXML
    private VBox outterVBox;
    @FXML
    private VBox innerVBox;
    @FXML
    private HBox labelImageViewHBox;
    @FXML
    private HBox InputHBox1;
    @FXML
    private TextField titelUebung;
    @FXML
    private HBox InputHBox11;
    @FXML
    private TextField wiederholungen;
    @FXML
    private VBox upperBox;
    @FXML
    private HBox labelH1Box;
    @FXML
    private Label labelEinteilung;
    @FXML
    private HBox ScrollPaneHBox;
    @FXML
    private ScrollPane AllExercisePane;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private String[][] lNames;
    private int j; 
    private int trainingseinheit_id = instanceE.getUserID();

    public void initialize() throws SQLException {
        trainingDurchfuehren.setShowTransitionFactory(BounceInRightTransition::new);
        
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.PLAY_ARROW.text,
                e -> removeTopChild());

        fab.showOn(trainingDurchfuehren);
        ImageView.setImage(workOutImage);

        //Geht leider nicht, hat wer eine Lösung?
        if (innerVBox.getChildren().isEmpty()) {
            fab.hide();
        }

        trainingDurchfuehren.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Training durchführen");
            }
            clearChildren();
            try {
                getUebungenVonTrainingsEinheit();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            setLabels();
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
                        now = Integer.toString(sekunden--) + "s";
                        u.setName(now);
                        timerAnzeige.textProperty().bindBidirectional(u.nameProperty());
                        if (sekunden <= 0) {
                            timer.cancel();
                            u.clear();
                            ImageView.setImage(workOutImage);
                        }
                        ImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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

    public void clearChildren() {
        innerVBox.getChildren().clear();
    }

    public void getUebungenVonTrainingsEinheit() throws SQLException{
        trainingseinheit_id = instanceE.getUserID();
        Uebung u = new Uebung();
           titelUebung.textProperty().bindBidirectional(u.nameProperty());
                    wiederholungen.textProperty().bindBidirectional(u.beschreibungProperty());

                    
        
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT trainingsuebung_id, trainingsname, beschreibung, wiederholung FROM trainingsliste tl join trainingsuebung using (trainingsuebung_id) WHERE trainingseinheit_id = ? ");
                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT COUNT(*)Length FROM trainingsListe WHERE trainingseinheit_id = '" + instanceE.getUserID() + "'")
            ) {
            preparedStatement.setInt(1, trainingseinheit_id);
            ResultSet rs1 = preparedStatement.executeQuery();
            
            ResultSet rs3 = preparedStatement2.executeQuery();
            int einheitLength = 0;
            if (rs3.next()) {
                einheitLength = rs3.getInt("Length");
            }
            
            String trainingsname;
            int rep;
            String beschreibung;
            lNames = new String[einheitLength][2];
            int i = 0; 
            while (rs1.next()) {
               
                //preparedStatement.setString(1, searchString);
                    createNewUebungBox(rs1.getString("trainingsname"), rs1.getInt("wiederholung"), rs1.getString("beschreibung"), rs1.getInt("trainingsuebung_id"));
                    /* for (int i = 0; i <= traininguebung_id; i++) {
                        for (int j = 0; j>=2; j++) {
                            String[i][j] lNames = {trainingsname}{rep};
                        }
                    }
                        
                    SELECT COUNT(*) FROM trainingsListe WHERE trainingseinheit_id = 1
                        
                    for (int j = 0; j < einheitLength; j++) {
                               
                        }
                    */
                    
                    lNames[i][0] = rs1.getString("trainingsname");
                    lNames[i][1] = rs1.getInt("wiederholung") + "x";
                    i++;
                }
            titelUebung.textProperty().bindBidirectional(u.nameProperty());
            wiederholungen.textProperty().bindBidirectional(u.beschreibungProperty());
            j=0;
            u.setName(lNames[j][0]);
            u.setBeschreibung(lNames[j][1]);
            j++;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void createNewUebungBox(String name, int rep, String beschreibung, int id) {
        boolean includeTrash = false;
        BoxDynamischGruen3 bx = new BoxDynamischGruen3(name, rep, beschreibung, includeTrash, id);
        innerVBox.getChildren().add(bx);
        innerVBox.setSpacing(10);
    }

    public void removeTopChild() {
        Uebung u = new Uebung();
        titelUebung.textProperty().bindBidirectional(u.nameProperty());
        wiederholungen.textProperty().bindBidirectional(u.beschreibungProperty());
        if (j < lNames.length){
        u.setName(lNames[j][0]);
        u.setBeschreibung(lNames[j][1]);
        j++;
        }
        if (innerVBox.getChildren().size() == 1){ 
            MobileApplication.getInstance().switchView(STARTSEITE_VIEW);
        }
        innerVBox.getChildren().remove(0);
    }

    public void setLabels() {
        String lName = innerVBox.getChildren().get(0).toString();
        System.out.println(lName);
    }

    private Object getInfoFromDB(int trainingseinheit_id) {
        String nameTE = "";
        int dauer = 0;
        try (Connection connection2 = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection2.prepareStatement("SELECT   name , dauer FROM trainingseinheit WHERE  trainingseinheit_id = '" + trainingseinheit_id + "'")) {
            //preparedStatement.setString(1, searchString);
            ResultSet rs2 = preparedStatement.executeQuery();
            while (rs2.next()) {
                nameTE = rs2.getString("name");
                dauer = rs2.getInt("dauer");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        Trainingseinheit te = new Trainingseinheit(nameTE, dauer);
        return te;
    }

}
