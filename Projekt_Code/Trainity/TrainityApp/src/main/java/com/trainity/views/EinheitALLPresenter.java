package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.BoxDynamischGruen3;
import com.trainity.Trainingseinheit;
import static com.trainity.Uebung.printSQLException;
import static com.trainity.UserSession.instance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EinheitALLPresenter {

    @FXML
    private View einheitBearbeiten;
    @FXML
    private VBox outerBox;
    @FXML
    private HBox labelH1HBox1;
    @FXML
    private Label labelName;
    @FXML
    private HBox inputHBoxName;
    @FXML
    private TextField inputName;
    @FXML
    private VBox upperBox;
    @FXML
    private HBox labelH1HBox;
    @FXML
    private Label labelEinteilung;
    @FXML
    private HBox ScrollPaneHBox;
    @FXML
    private ScrollPane AllExercisePane;
    @FXML
    private VBox innerVBox;
    @FXML
    private HBox saveButtonHBox;
    @FXML
    private Button ButtonSave;

    private Connection connection;
    private Statement statement;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String UPDATE_STATEMENT = "UPDATE benutzer set nextWorkoutId = ? where id = ?";
    
    //ÄNDERN !!!!!!
    // private static int trainingseinheit_id = instanceE.getUserID();
    private static int trainingseinheit_id = 1;

    private final StringProperty name = new SimpleStringProperty();

    public void initialize() {
        einheitBearbeiten.setShowTransitionFactory(BounceInRightTransition::new);

        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.PLAY_ARROW.text,
                e -> MobileApplication.getInstance().switchView("Training durchführen View")
        );

        fab.showOn(einheitBearbeiten);

        einheitBearbeiten.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(
                        e -> MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Übersicht Trainingseinheiten");
                appBar.getActionItems().add(MaterialDesignIcon.TODAY.button(
                        e -> setNewWorkoutId(trainingseinheit_id)));
            }
            clearChildren();
            //Wenn Trainingseinheit vorhande
            getUebungenVonTrainingsEinheit();
        });
    }
    
    public void clearChildren() {
        innerVBox.getChildren().clear();
    }
    public void getUebungenVonTrainingsEinheit() {
        System.out.println(trainingseinheit_id);
        Trainingseinheit te = (Trainingseinheit) getInfoFromDB(trainingseinheit_id);
        getInputName().textProperty().bindBidirectional(te.nameProperty());

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT trainingsuebung_id FROM trainingsliste WHERE trainingseinheit_id = '" + trainingseinheit_id + "'")) {
            ResultSet rs1 = preparedStatement.executeQuery();
            String trainingsname;
            int rep;
            String beschreibung;
            while (rs1.next()) {
                int trainingsuebung_id = rs1.getInt("trainingsuebung_id");
                try (Connection connection2 = DriverManager
                        .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                        PreparedStatement preparedStatement2 = connection2.prepareStatement("SELECT   trainingsname , wiederholung, beschreibung FROM trainingsuebung WHERE  trainingsuebung_id = '" + trainingsuebung_id + "'")) {
                    //preparedStatement.setString(1, searchString);
                    ResultSet rs2 = preparedStatement2.executeQuery();
                    while (rs2.next()) {
                        trainingsname = rs2.getString("trainingsname");
                        rep = rs2.getInt("wiederholung");
                        beschreibung = rs2.getString("beschreibung");
                        createNewUebungBox(trainingsname, rep, beschreibung, trainingsuebung_id);
                    }
                } catch (SQLException e) {
                    printSQLException(e);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    
    public void createNewUebungBox(String name, int rep, String beschreibung, int id) {
        boolean includeTrash = false;
        BoxDynamischGruen3 bx = new BoxDynamischGruen3(name, rep, beschreibung, includeTrash, id);
        innerVBox.getChildren().add(bx);
    }
    
    public TextField getInputName() {
        return inputName;
    }

    public void setInputName(TextField inputName) {
        this.inputName = inputName;
    }

    private Object getInfoFromDB(int trainingseinheit_id) {
        String nameTE = "";
        int dauer = 0;
        try (Connection connection2 = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
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
    
    public void setNewWorkoutId(int trainingseinheit_id) {
        int userId = instance.getUserID();
        //trainingseinheit_id
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATEMENT)) {
            preparedStatement.setInt(1, trainingseinheit_id);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
}
