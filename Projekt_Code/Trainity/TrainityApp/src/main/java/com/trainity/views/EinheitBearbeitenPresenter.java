package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.BoxDynamischGruen;
import com.trainity.BoxDynamischGruen2;
import static com.trainity.EinheitSession.instanceE;
import com.trainity.Trainingseinheit;
import static com.trainity.Trainity.EIGENE_TRAININGS_VIEW;
import static com.trainity.Trainity.UEBUNG_AUSWAEHLEN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_VIEW;
import static com.trainity.Uebung.printSQLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class EinheitBearbeitenPresenter {

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
    private HBox labelIconHBox;
    @FXML
    private Label labelIcon;
    @FXML
    private HBox buttonOpenHBox;
    @FXML
    private Button ButtonOpenImagePicker;
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
    private HBox buttonHBox;
    @FXML
    private Button ButtonAdd;
    @FXML
    private HBox saveButtonHBox;
    @FXML
    private Button ButtonSave;
    @FXML
    private ImageView imageViewSafes;
    
      private Connection connection;
      private Statement statement;

        private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";


    private final StringProperty name = new SimpleStringProperty();
    

    public void initialize() {
        einheitBearbeiten.setShowTransitionFactory(BounceInRightTransition::new);
       
        /*  FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.DO_NOT_DISTURB.text,
                e -> System.out.println("Test"));
        fab.showOn(einheitBearbeiten);
         */
        einheitBearbeiten.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
             
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Einheit bearbeiten");

            }
                 clearChildren();
                 
          


            //Wenn Trainingseinheit vorhande
                        
                            
                         
                        
                        getUebungenVonTrainingsEinheit();
                        
                      
               
                        
        });
     

    }
    
    
    
   

    @FXML
    private void createNewUebung(ActionEvent event) {
        
        clearChildren();
        MobileApplication.getInstance().switchView(UEBUNG_AUSWAEHLEN_VIEW);

    }

    
    
    public void clearChildren(){
    
    
    innerVBox.getChildren().clear();
    
    
    }
    @FXML
    private void onActionSaveTrainingsPlan(ActionEvent event) {

        System.out.println("Saving");
        
        
        instanceE.cleanUserSession();
        MobileApplication.getInstance().switchView(EIGENE_TRAININGS_VIEW);


    }

    
    
    
    //Wird automatisch aufgerufen wenn Traininsgeinheit schon vorhanden
    public void getUebungenVonTrainingsEinheit() {

        //ÄNDERN !!!!!!
       int trainingseinheit_id = instanceE.getUserID();
        System.out.println(trainingseinheit_id);
       
       
         Trainingseinheit te = (Trainingseinheit) getInfoFromDB( trainingseinheit_id);
        
        getInputName().textProperty().bindBidirectional(te.nameProperty());

   
       
    
       
       
        
  // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT trainingsuebung_id FROM trainingsliste WHERE trainingseinheit_id = '" + trainingseinheit_id + "'")) {

            ResultSet rs1 = preparedStatement.executeQuery();

            
            
            System.out.println("-------------------");

            

               String trainingsname;
               int rep;
               String beschreibung;
     
            while(rs1.next()){

                int trainingsuebung_id= rs1.getInt("trainingsuebung_id");

         try (Connection connection2 = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement2 = connection2.prepareStatement("SELECT   trainingsname , wiederholung, beschreibung FROM trainingsuebung WHERE  trainingsuebung_id = '" + trainingsuebung_id + "'")) {
            //preparedStatement.setString(1, searchString);

            ResultSet rs2 = preparedStatement2.executeQuery();
   
            
           
             
            while (rs2.next()) {

                System.out.println("-------------------");

                

                trainingsname = rs2.getString("trainingsname");

                rep = rs2.getInt("wiederholung");

                beschreibung = rs2.getString("beschreibung");

          
           
                createNewUebungBox(trainingsname,rep,beschreibung,trainingsuebung_id);
            

            }
            
             } catch (SQLException e) {
            printSQLException(e);
        }
            
        
           }   
            
        } catch (SQLException e) {
            printSQLException(e);
        }
            

          //  }
     

    }

    
  
    
    public void createNewUebungBox(String name, int rep, String beschreibung, int id) {

        boolean includeTrash = true;

        BoxDynamischGruen2 bx = new BoxDynamischGruen2(name, rep, beschreibung, includeTrash, id);
        innerVBox.getChildren().add(bx);

    }
    
    
    
    
    
    
    
    
    
    // Wenn man einen neuen Trainingsplan erstellt, wird das Array aus Namen Wiederholungen... einen Null Eintrag geaddet 
    // und das Input Field leer gemacht.
    private void newTrainingsplan() {
    Trainingseinheit te = new Trainingseinheit();
       getInputName().textProperty().bindBidirectional(te.nameProperty());
      
    }

    public TextField getInputName() {
        return inputName;
    }

    public void setInputName(TextField inputName) {
        this.inputName = inputName;
    }

    private Object getInfoFromDB(int trainingseinheit_id) {
        
          String nameTE="";
           int dauer=0;
             
         try (Connection connection2 = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection2.prepareStatement("SELECT   name , dauer FROM trainingseinheit WHERE  trainingseinheit_id = '" + trainingseinheit_id+ "'")) {
            //preparedStatement.setString(1, searchString);

            ResultSet rs2 = preparedStatement.executeQuery();
   
       
             
            while (rs2.next()) {

               

                nameTE = rs2.getString("name");

                dauer = rs2.getInt("dauer");

            
            }
            
             } catch (SQLException e) {
            printSQLException(e);
        }
        
                   Trainingseinheit te = new Trainingseinheit(nameTE,dauer);

           
           return te;
    }

  
   

    
}

