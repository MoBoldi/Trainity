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
import static com.trainity.Trainity.UEBUNG_AUSWAEHLEN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_NotEditable_VIEW;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Window;

public class UebungBearbeitenPresenter {

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
    @FXML
    private HBox labelIconHBox;
    @FXML
    private Label labelIcon;
    @FXML
    private HBox buttonHBox;
    @FXML
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
    
   private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?zeroDateTimeBehavior=convertToNull";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
      private Connection connection;
      private Statement statement;
      
      private boolean forward= true;

    // Helper
    private static final NumberFormat DF;

    static {
        DF = NumberFormat.getNumberInstance();
        DF.setMaximumFractionDigits(2);
        DF.setMinimumFractionDigits(2);
    }
    @FXML
    private Button ButtonSave1;
    @FXML
    private ImageView imageViewSafes1;

    public UebungBearbeitenPresenter() {
       
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
        });
        
        bindEverything();

    }
    
    public void bindEverything(){
    
    Uebung uebung = new Uebung();
        
    
       //  uebungBearbeiten.getInputNameExercise().textProperty().bindBidirectional(uebungBearbeiten.model.nameProperty());
       
            getInputNameExercise().textProperty().bindBidirectional(uebung.nameProperty());
            getInputLabelRep().textProperty().bindBidirectional(uebung.wiederholungenProperty(), new NullableNumberStringConverter(DF));            
            getInputLabelInfo().textProperty().bindBidirectional(uebung.beschreibungProperty());
        
            
            uebung.setName("");
            uebung.setWiederholungen(null);
            uebung.setBeschreibung("");
    }
    
   
 
    
      
    @FXML
    private void OnActionBack(ActionEvent event) {

        MobileApplication.getInstance().switchView(UEBUNG_AUSWAEHLEN_VIEW);

    }

    
          
    @FXML   
    private void onActionSaveToDB(ActionEvent event) {
        
         
        
      
    try {
        
  

  
  System.out.println("Safe to DB ...");
      String name = getInputNameExercise().textProperty().get();
      int rep = Integer.parseInt(getInputLabelRep().getText());
      String beschreibung = getInputLabelInfo().textProperty().get();
      
        System.out.println(name + rep + beschreibung);
       killAndFill(name, rep, beschreibung);
       
       if(forward){ 
           Uebung u = new Uebung(name,rep, beschreibung);
      u.saveuebung();
      u.clear();
       }
       
       
     

    }
    catch (Exception ex) {
       System.out.println(ex.getMessage());
        System.out.println("Fehler beim Speichern der neuen Uebung!");
    }  
    
    
    
        System.out.println("NOW Adding to List");
        
        
        
        
        
    }
    
     private void killAndFill(String name, int rep, String beschreibung) {

   forward= true;
   Window owner = ButtonSave.getScene().getWindow();
   String errorString = "";
   Number nullValue = 0.00;
        
        
        if (!checkName(name) || name == null || name.trim().length() == 0 ) {
           
            errorString = errorString + "Bitte geben Sie einen Namen an! (mind. 2 Zeichen)\n";
                              forward= false;

        }

        if (!checkWiederholung(rep)|| (Number)rep == null || rep < 1) {
                errorString = errorString + "Bitte geben Sie die Wiederholungen an! (nicht 0)\n";

                 forward= false;
        }

        if (!checkBeschreibung(beschreibung)||  beschreibung == null|| beschreibung.trim().length() == 0) {
              
            errorString = errorString + "Bitte geben Sie eine Beschreibung an! (mind. 2 Zeichen)\n";
                 forward= false;
        }
        
        
        
        
        if(!forward){
            System.out.println("Fehler");
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                 errorString);
        
        }

    }
     
      private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
   
   
     private boolean checkName(String name) {

          String regexName = "[a-zA-Z]{2,30}";

        Pattern p = Pattern.compile(regexName);
        Matcher m = p.matcher(name);
        return m.matches();
 

    }

    private boolean checkBeschreibung(String beschreibung) {
       
           String regexName = "[a-zA-Z]{2,30}";

        Pattern p = Pattern.compile(regexName);
        Matcher m = p.matcher(beschreibung);
        return m.matches();
    }

    private boolean checkWiederholung(int nr) {

        boolean test;

        if (nr == 0) {
            test = false;
        } else {
            test = true;
        }

        return test;
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

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

   
   
  
    
    
    
    
    
    
}
