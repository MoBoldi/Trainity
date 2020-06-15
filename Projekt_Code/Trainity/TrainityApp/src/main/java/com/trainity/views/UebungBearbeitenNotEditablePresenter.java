package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.Uebung;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.NullableNumberStringConverter;
import com.trainity.*;
import static com.trainity.EinheitSession.instanceE;
import static com.trainity.Trainity.EINHEIT_BEARBEITEN_VIEW;
import static com.trainity.Trainity.UEBUNG_AUSWAEHLEN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_NotEditable_VIEW;
import static com.trainity.Uebung.printSQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimeZone;
import javafx.event.ActionEvent;

public class UebungBearbeitenNotEditablePresenter {

    @FXML
    private View uebungBearbeiten;
    @FXML
    private VBox outerBox;
    @FXML
    private VBox upperBox;
    @FXML
    private HBox labelNameHBox;
    @FXML
    private Label labelNameExercise;
    @FXML
    private HBox inputNameHBox;
    @FXML
    private TextField inputNameExercise;
    @FXML
    private HBox labelRepHBox;
    @FXML
    private Label labelRep;
    @FXML
    private TextField inputLabelRep;
    @FXML
    private HBox labelInfoHBox;
    @FXML
    private Label labelInfo;
    @FXML
    private TextField inputLabelInfo;
    private HBox labelIconHBox;
    private Label labelIcon;
    @FXML
    private HBox buttonHBox;
    private Button NewExerciseButton;
    @FXML
    private HBox saveButtonHBox;
    @FXML
    private Button ButtonSave;
    @FXML
    private ImageView imageViewSafes;

    // Model
    private Uebung model;

    // Database
    private Statement statement;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
        private static final String INSERT_QUERY = "INSERT INTO trainingsListe (trainingseinheit_id, trainingsuebung_id) VALUES (?, ?)";

    // Helper
    private static final NumberFormat DF;

    
    public static int USERID;
    
    static {
        DF = NumberFormat.getNumberInstance();
        DF.setMaximumFractionDigits(2);
        DF.setMinimumFractionDigits(2);
    }
    @FXML
    private Button ButtonSave1;
    @FXML
    private ImageView imageViewSafes1;

    public UebungBearbeitenNotEditablePresenter() {
    }



    public void initialize() {
        uebungBearbeiten.setShowTransitionFactory(BounceInRightTransition::new);

        /* FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info"));
        fab.showOn(uebungBearbeiten);*/
        uebungBearbeiten.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Übung bearbeiten");

            }
                  fillValues();
            
        });

 
  
    }
    
    public void fillValues() {
        

             Uebung uebung = new Uebung();
        
        
        //  uebungBearbeiten.getInputNameExercise().textProperty().bindBidirectional(uebungBearbeiten.model.nameProperty());
        getInputNameExercise().textProperty().bindBidirectional(uebung.nameProperty());
        getInputLabelRep().textProperty().bindBidirectional(uebung.wiederholungenProperty(), new NullableNumberStringConverter(DF));
        getInputLabelInfo().textProperty().bindBidirectional(uebung.beschreibungProperty());

        System.out.println("FILLING VALUES");
  
  try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object

                PreparedStatement preparedStatement = connection.prepareStatement("SELECT  trainingsname, wiederholung, beschreibung FROM trainingsuebung WHERE  trainingsuebung_id = '" + USERID + "'")) {

                      System.out.println(USERID);

                      
      System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

     // System.out.println(USERID);
     
            if(rs!=null){
            
 

            
       
        
               String trainingsname="";
               int rep=0;
                 String beschreibung="";
             
             
            while (rs.next()) {

                System.out.println("-------------------");

              

                trainingsname = rs.getString("trainingsname");

                rep = rs.getInt("wiederholung");

                beschreibung = rs.getString("beschreibung");

                System.out.println(trainingsname);

            }   
            
            
            rs = null;
        uebung.setName(trainingsname);
        uebung.setWiederholungen(rep);
        uebung.setBeschreibung(beschreibung);
       
            }
            
        } catch (SQLException e) {
            printSQLException(e);
        }
   
    }

    
    
    
  

    
    
    
    
    @FXML
    private void OnActionBack(ActionEvent event) {

       //  setInputNameExercise("s");
       // uebung.setWiederholungen(rep);
       // uebung.setBeschreibung(beschreibung);
    //   Uebung ub = new Uebung("",0,"");
       
   
        MobileApplication.getInstance().switchView(UEBUNG_AUSWAEHLEN_VIEW);

    }
   @FXML
    private void onActionAddToTraining(ActionEvent event) {
        
  //SAFING
  
            
  
  
  
       System.out.println("UebungID ist " + USERID);
       
       
    int uebung_id = USERID;
    int trainingseinheit_id = instanceE.getUserID();
     
       // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, trainingseinheit_id);
            preparedStatement.setInt(2, uebung_id);
          

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            
            
            
            
            //ADDING TO LIST
            
            
            
            

        } catch (SQLException e) {
            printSQLException(e);
        }
       
       
       
       MobileApplication.getInstance().switchView(EINHEIT_BEARBEITEN_VIEW);

    }

    /* 
    
    Aufruf Save FKt
    
    @FXML
  private void btSaveOnAction(ActionEvent event) {
    try {
      new Person(model).save(statement);
      model.clear();
      getTfMsg().setText("Ok, gesichert!");
      getTfMsg().setStyle("-fx-text-inner-color: green;");
    }
    catch (Exception ex) {
      getTfMsg().setText(ex.getMessage());
      getTfMsg().setStyle("-fx-text-inner-color: red;");
    }
  }
     */
 /*Aktualisiern*/
    public View getUebungBearbeiten() {
        return uebungBearbeiten;
    }

    public void setUebungBearbeiten(View uebungBearbeiten) {
        this.uebungBearbeiten = uebungBearbeiten;
    }

    public VBox getOuterBox() {
        return outerBox;
    }

    public void setOuterBox(VBox outerBox) {
        this.outerBox = outerBox;
    }

    public VBox getUpperBox() {
        return upperBox;
    }

    public void setUpperBox(VBox upperBox) {
        this.upperBox = upperBox;
    }

    public HBox getLabelNameHBox() {
        return labelNameHBox;
    }

    public void setLabelNameHBox(HBox labelNameHBox) {
        this.labelNameHBox = labelNameHBox;
    }

    public Label getLabelNameExercise() {
        return labelNameExercise;
    }

    public void setLabelNameExercise(Label labelNameExercise) {
        this.labelNameExercise = labelNameExercise;
    }

    public HBox getInputNameHBox() {
        return inputNameHBox;
    }

    public void setInputNameHBox(HBox inputNameHBox) {
        this.inputNameHBox = inputNameHBox;
    }

    public TextField getInputNameExercise() {
        return inputNameExercise;
    }

    public void setInputNameExercise(TextField inputNameExercise) {
        this.inputNameExercise = inputNameExercise;
    }

    public HBox getLabelRepHBox() {
        return labelRepHBox;
    }

    public void setLabelRepHBox(HBox labelRepHBox) {
        this.labelRepHBox = labelRepHBox;
    }

    public Label getLabelRep() {
        return labelRep;
    }

    public void setLabelRep(Label labelRep) {
        this.labelRep = labelRep;
    }

    public TextField getInputLabelRep() {
        return inputLabelRep;
    }

    public void setInputLabelRep(TextField inputLabelRep) {
        this.inputLabelRep = inputLabelRep;
    }

    public HBox getLabelInfoHBox() {
        return labelInfoHBox;
    }

    public void setLabelInfoHBox(HBox labelInfoHBox) {
        this.labelInfoHBox = labelInfoHBox;
    }

    public Label getLabelInfo() {
        return labelInfo;
    }

    public void setLabelInfo(Label labelInfo) {
        this.labelInfo = labelInfo;
    }

    public TextField getInputLabelInfo() {
        return inputLabelInfo;
    }

    public void setInputLabelInfo(TextField inputLabelInfo) {
        this.inputLabelInfo = inputLabelInfo;
    }

    public HBox getLabelIconHBox() {
        return labelIconHBox;
    }

    public void setLabelIconHBox(HBox labelIconHBox) {
        this.labelIconHBox = labelIconHBox;
    }

    public Label getLabelIcon() {
        return labelIcon;
    }

    public void setLabelIcon(Label labelIcon) {
        this.labelIcon = labelIcon;
    }

    public HBox getButtonHBox() {
        return buttonHBox;
    }

    public void setButtonHBox(HBox buttonHBox) {
        this.buttonHBox = buttonHBox;
    }

    public Button getNewExerciseButton() {
        return NewExerciseButton;
    }

    public void setNewExerciseButton(Button NewExerciseButton) {
        this.NewExerciseButton = NewExerciseButton;
    }

    public HBox getSaveButtonHBox() {
        return saveButtonHBox;
    }

    public void setSaveButtonHBox(HBox saveButtonHBox) {
        this.saveButtonHBox = saveButtonHBox;
    }

    public Button getButtonSave() {
        return ButtonSave;
    }

    public void setButtonSave(Button ButtonSave) {
        this.ButtonSave = ButtonSave;
    }

    public ImageView getImageViewSafes() {
        return imageViewSafes;
    }

    public void setImageViewSafes(ImageView imageViewSafes) {
        this.imageViewSafes = imageViewSafes;
    }

    public Uebung getModel() {
        return model;
    }

    public void setModel(Uebung model) {
        this.model = model;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

 
}
