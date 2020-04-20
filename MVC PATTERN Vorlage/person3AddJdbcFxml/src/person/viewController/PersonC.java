package person.viewController;


import person.model.*;
import java.io.IOException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import util.*;


public class PersonC {
  @FXML
  private TextField tfSvnr;
  @FXML
  private TextField tfNname;
  @FXML
  private TextField tfVname;
  @FXML
  private DatePicker dpGebDat;
  @FXML
  private TextField tfGroesse;
  @FXML
  private ChoiceBox<?> cbGeschlecht;
  @FXML
  private Button btSave;
  @FXML
  private Button btCancel;
  @FXML
  private TextField tfMsg;

  // Model
  private Person model;

  // Database
  private Statement statement;

  // Helper
  private static final NumberFormat DF;


  static {
    DF = NumberFormat.getNumberInstance();
    DF.setMaximumFractionDigits(2);
    DF.setMinimumFractionDigits(2);
  }


  public PersonC() {
  }


  public static void show(Stage stage, Statement statement) {
    try {
      // View
      //  - Root
      FXMLLoader loader = new FXMLLoader(PersonC.class.getResource("PersonV.fxml"));
      Parent root = (Parent) loader.load();

      // - Scene
      Scene scene = new Scene(root);

      // - Stage
      if (stage == null) {
        stage = new Stage();
      }
      stage.setScene(scene);
      stage.setTitle("Personenwartung");

      // Controller
      PersonC personC = (PersonC) loader.getController();
      personC.statement = statement;

      // Model
      personC.model = new Person();

      // Controller-Inits
      personC.getCbGeschlecht().setItems(Geschlecht.valuesAsObservableList());
      personC.getCbGeschlecht().setValue(personC.getCbGeschlecht().getItems().get(0));

      // Controls mit Model verbinden
      personC.getTfSvnr().textProperty().bindBidirectional(personC.model.svnrProperty());
      personC.getTfNname().textProperty().bindBidirectional(personC.model.nnameProperty());
      personC.getTfVname().textProperty().bindBidirectional(personC.model.vnameProperty());
      personC.getDpGebDat().valueProperty().bindBidirectional(personC.model.gebDatProperty());
      personC.getTfGroesse().textProperty().bindBidirectional(personC.model.groesseProperty(), 
              new NullableNumberStringConverter(DF));
      personC.getCbGeschlecht().valueProperty().bindBidirectional(personC.model.geschlechtProperty());

      // Anzeigen
      stage.show();
    }
    catch (IOException ex) {
      Logger.getLogger(PersonC.class.getName()).log(Level.SEVERE, null, ex);
      System.err.println("Something wrong with PersonV.fxml!");
      ex.printStackTrace(System.err);
      System.exit(1);
    }
  }


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


  @FXML
  private void btCancelOnAction(ActionEvent event) {
    Alert alConfirm = new Alert(Alert.AlertType.CONFIRMATION);
    alConfirm.setHeaderText("Sicher?");
    alConfirm.setContentText("Wirklich Canceln?");
    Optional<ButtonType> result = alConfirm.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      model.clear();
      getTfMsg().setText("Ok, Wartung abgebrochen!");
      getTfMsg().setStyle("-fx-text-inner-color: green;");
    }
    else {
      getTfMsg().setText("Ok, kein Abbruch der Wartung vorgenommen!");
      getTfMsg().setStyle("-fx-text-inner-color: green;");
    }
  }


  public TextField getTfSvnr() {
    return tfSvnr;
  }


  public TextField getTfNname() {
    return tfNname;
  }


  public TextField getTfVname() {
    return tfVname;
  }


  public DatePicker getDpGebDat() {
    return dpGebDat;
  }


  public TextField getTfGroesse() {
    return tfGroesse;
  }


  public ChoiceBox<Geschlecht> getCbGeschlecht() {
    return (ChoiceBox<Geschlecht>) cbGeschlecht;
  }


  public Button getBtSave() {
    return btSave;
  }


  public Button getBtCancel() {
    return btCancel;
  }


  public TextField getTfMsg() {
    return tfMsg;
  }


  public static NumberFormat getDf() {
    return DF;
  }
}
