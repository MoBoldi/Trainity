package person.model;


import java.util.Arrays;
import javafx.collections.*;


public enum Geschlecht {
  M, W;


  public static ObservableList<Geschlecht> valuesAsObservableList() {
    return FXCollections.observableList(Arrays.asList(values()));
  }
}
