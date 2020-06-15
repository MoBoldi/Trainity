package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.BoxDynamischGruen;
import com.trainity.EinheitSession;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_VIEW;
import static com.trainity.Uebung.printSQLException;
import com.trainity.UserSession;
import static com.trainity.UserSession.instance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

    
    private String oldSearchString ="";
    
    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static final String SELECT_QUERY = "SELECT trainingsuebung_id, name, rep, beschreibung FROM trainingsuebung WHERE  name = ?";

    public void initialize() {
        uebungAuswaehlen.setShowTransitionFactory(BounceInRightTransition::new);

        /*  FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info"));
        fab.showOn(uebungAuswaehlen);
         */
        uebungAuswaehlen.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e
                        -> MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Übung auswählen");
               
            }

        });
    }

    
     @FXML
    private void searchingTheDatabase(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) ){
        
        searchForUebungInDB();
           
        
        }
              
        if(event.getCode().equals(KeyCode.BACK_SPACE)){
            oldSearchString = null;

            AllExerciseVBox.getChildren().clear();
        }
        
        
    }
    
    
    
    

    
 

    @FXML
    private void CreateNewUebung(ActionEvent event) {

        System.out.println("Creating New Uebung mit Editable Fields");

        MobileApplication.getInstance().switchView(UEBUNG_BEARBEITEN_VIEW);
        //Link to UebungBearbeiten.fxml Felder aber editable haben keine Werte
    }

    private void searchForUebungInDB() {
        
        
                    
        String searchString = getInputSearchBar().textProperty().get();
       
        if(oldSearchString == null ? searchString != null : !oldSearchString.equals(searchString)){
            
           AllExerciseVBox.getChildren().clear();
           searching(searchString);
 
        }else{
         oldSearchString = null;
        }
     

    }

    
    
    
    
    private void searching(String searchString) {

        // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT trainingsuebung_id, trainingsname, wiederholung, beschreibung FROM trainingsuebung WHERE  trainingsname = '" + searchString + "' and benutzer_id = " + instance.getUserID())) {
            //preparedStatement.setString(1, searchString);

            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<String> resultList = new ArrayList<String>();
            if(rs!=null){
                oldSearchString = searchString;
            
            System.out.println("-------------------");

            
              int trainingsuebung_id;
              String trainingsuebung_idS;
               String trainingsname;
               int rep;
               String repS;
             String beschreibung;
             
             
            while (rs.next()) {

                System.out.println("-------------------");

                 trainingsuebung_id = rs.getInt("trainingsuebung_id");
                trainingsuebung_idS = String.valueOf(trainingsuebung_id);

                trainingsname = rs.getString("trainingsname");

                rep = rs.getInt("wiederholung");
                repS = String.valueOf(rep);

                beschreibung = rs.getString("beschreibung");

                ///___________------------------
                
               // Sout Ausgabe später löschen 
             //   System.out.println(trainingsuebung_id);
               // System.out.println(trainingsname);
                //System.out.println(rep);
             //   System.out.println(beschreibung);
               

                
                // Daten in Array pushen
                resultList.add(trainingsuebung_idS);
                resultList.add(trainingsname);
                resultList.add(repS);
                resultList.add(beschreibung);
                
                createNewUebungBox(trainingsname,rep,beschreibung,trainingsuebung_id);
                

            }
        
            }
            
        } catch (SQLException e) {
            printSQLException(e);
        }

    }
    
    
    
    
    
   

    public void createNewUebungBox(String name, int rep, String beschreibung, int id) {

        boolean includeTrash = false;

        BoxDynamischGruen bx = new BoxDynamischGruen(name, rep, beschreibung, includeTrash, id);
        AllExerciseVBox.getChildren().add(bx);

    }
    
    

    public View getUebungAuswaehlen() {
        return uebungAuswaehlen;
    }

    public void setUebungAuswaehlen(View uebungAuswaehlen) {
        this.uebungAuswaehlen = uebungAuswaehlen;
    }

    public VBox getOuterBox() {
        return outerBox;
    }

    public void setOuterBox(VBox outerBox) {
        this.outerBox = outerBox;
    }

    public VBox getUpperBox() {
        return UpperBox;
    }

    public void setUpperBox(VBox UpperBox) {
        this.UpperBox = UpperBox;
    }

    public HBox getSearchHBox() {
        return searchHBox;
    }

    public void setSearchHBox(HBox searchHBox) {
        this.searchHBox = searchHBox;
    }

    public Label getLabelSearch() {
        return labelSearch;
    }

    public void setLabelSearch(Label labelSearch) {
        this.labelSearch = labelSearch;
    }

    public HBox getInputHBox() {
        return inputHBox;
    }

    public void setInputHBox(HBox inputHBox) {
        this.inputHBox = inputHBox;
    }

    public TextField getInputSearchBar() {
        return inputSearchBar;
    }

    public void setInputSearchBar(TextField inputSearchBar) {
        this.inputSearchBar = inputSearchBar;
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

    public HBox getScrollPaneHBox() {
        return scrollPaneHBox;
    }

    public void setScrollPaneHBox(HBox scrollPaneHBox) {
        this.scrollPaneHBox = scrollPaneHBox;
    }

    public ScrollPane getAllExercisePane() {
        return AllExercisePane;
    }

    public void setAllExercisePane(ScrollPane AllExercisePane) {
        this.AllExercisePane = AllExercisePane;
    }

    public VBox getAllExerciseVBox() {
        return AllExerciseVBox;
    }

    public void setAllExerciseVBox(VBox AllExerciseVBox) {
        this.AllExerciseVBox = AllExerciseVBox;
    }

   

}
