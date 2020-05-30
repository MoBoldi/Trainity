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
import static com.trainity.Trainity.EINHEIT_BEARBEITEN_VIEW;
import static com.trainity.Trainity.UEBUNG_AUSWAEHLEN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_NotEditable_VIEW;
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

    public UebungBearbeitenNotEditablePresenter() {
    }

    public static void show(Stage stage, Statement statement) {

        try {

            FXMLLoader loader = new FXMLLoader(UebungBearbeitenNotEditablePresenter.class.getResource("uebungBearbeiten.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            // - Stage
            if (stage == null) {
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.setTitle("UebungBearbeiten");

            UebungBearbeitenNotEditablePresenter uebungBearbeiten = (UebungBearbeitenNotEditablePresenter) loader.getController();
            uebungBearbeiten.statement = statement;

            // Model
            uebungBearbeiten.model = new Uebung();

            //BINDINGS 
            uebungBearbeiten.getInputNameExercise().textProperty().bindBidirectional(uebungBearbeiten.model.nameProperty());
            uebungBearbeiten.getInputLabelRep().textProperty().bindBidirectional(uebungBearbeiten.model.wiederholungenProperty(),
                    new NullableNumberStringConverter(DF));
            uebungBearbeiten.getInputLabelInfo().textProperty().bindBidirectional(uebungBearbeiten.model.beschreibungProperty());
            // uebungBearbeiten.getInputNameHBox().textProperty().bindBidirectional(uebungBearbeiten.model.nameProperty());

            System.out.println("Bindet");

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(UebungBearbeitenNotEditablePresenter.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error");

        }

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

        fillValues();
    }

    public void fillValues() {
        Uebung uebung = new Uebung();

        //  uebungBearbeiten.getInputNameExercise().textProperty().bindBidirectional(uebungBearbeiten.model.nameProperty());
        getInputNameExercise().textProperty().bindBidirectional(uebung.nameProperty());
        getInputLabelRep().textProperty().bindBidirectional(uebung.wiederholungenProperty(), new NullableNumberStringConverter(DF));
        getInputLabelInfo().textProperty().bindBidirectional(uebung.beschreibungProperty());

        //CALLING SQL STATEMENT
        String name = getInfosFromDB();
        int rep = 20;
        String beschreibung = "Naja die Liegestütze sind eine einfache Übung";
        
        
        uebung.setName(name);
        uebung.setWiederholungen(rep);
        uebung.setBeschreibung(beschreibung);

    }

    
    
    
    
    
    
    public static String getInfosFromDB() {

        //SQL STATEMENT
        String name = "Liegestütze";
        String rep = "20";

        String beschreibung = "Naja die Liegestütze sind eine einfache Übung.";

        
        
        
       return name;
          
    }

    
    
    
    
    @FXML
    private void OnActionBack(ActionEvent event) {

        MobileApplication.getInstance().switchView(UEBUNG_AUSWAEHLEN_VIEW);

    }
   @FXML
    private void onActionAddToTraining(ActionEvent event) {
        
        System.out.println("Add to Trainingsplan");
     
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
