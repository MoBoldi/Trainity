package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.BoxDynamischBlauGroß;
import com.trainity.BoxDynamischBlauKlein;
import com.trainity.BoxDynamischGruen;
import static com.trainity.Trainity.LOGIN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_VIEW;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void initialize() {
        uebungAuswaehlen.setShowTransitionFactory(BounceInRightTransition::new);
        
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
                e -> System.out.println("Info"));
        fab.showOn(uebungAuswaehlen);
        
        uebungAuswaehlen.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Übung auswählen");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
            
            
            
            
        });
    
}
    
    
    
    
    

    @FXML
    private void openUebnungMenu(MouseEvent event) {
   
 
        System.out.println("Öffne Uebung mit Non Editable Fields");

        
        String name ="Jumpin Jacks";
        int rep = 10;
        String beschreibung = "Beschreibung Liegestütze .......";
        
        boolean includeTrash = true;
        
             
       BoxDynamischGruen bx = new  BoxDynamischGruen(name, rep, beschreibung, includeTrash);
        AllExerciseVBox.getChildren().add(bx);
     
        
   
    }

    @FXML
    private void CreateNewUebung(ActionEvent event) {
        
        
        System.out.println("Creating New Uebung mit Editable Fields");
        
                MobileApplication.getInstance().switchView(UEBUNG_BEARBEITEN_VIEW);
        //Link to UebungBearbeiten.fxml Felder aber editable haben keine Werte
    }

    

    @FXML
    private void searchForUebungInDB(KeyEvent event) {
                System.out.println("Searching for Uebung in DB");
                System.out.println("Searchstring: "+ getInputSearchBar().textProperty().get() );
                
                
                
                

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
