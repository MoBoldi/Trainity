package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.trainity.BoxDynamischBlauGroß;
import com.trainity.BoxDynamischBlauKlein;
import static com.trainity.Trainity.EINHEIT_ERSTELLEN_VIEW;
import static com.trainity.Trainity.WOCHENPLAN_VIEW;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import static com.trainity.UserSession.instance;
import java.sql.ResultSet;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;




public class EigeneTrainingsPresenter {

        private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static final String SELECT_QUERY = "select name, dauer, trainingseinheit_id from trainingseinheit where user_id = ?";

    @FXML
    private View eigeneTrainings;
    @FXML
    private VBox uebersicht;
    @FXML
    private VBox einheiten;
    @FXML
    private Button ButtonAdd;
    
    public void initialize() {
        eigeneTrainings.setShowTransitionFactory(BounceInRightTransition::new);
        
        eigeneTrainings.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Eigene Trainings");
            }
            einheiten.getChildren().clear();
            //einheiten.getChildren().add(new BoxDynamischBlauKlein("Bizeps Workout", 20, "Bizeps mit Kurzhanteln"));
            //einheiten.getChildren().add(new BoxDynamischBlauKlein("Bein Workout", 30, "Oberschenkel mit Langhantel"));
            //einheiten.getChildren().add(new BoxDynamischBlauKlein("Sprint", 10, "4x20m Sprint"));
            //einheiten.getChildren().add(new BoxDynamischBlauKlein("Abs Extreme", 25, "etremes Abs Workout"));
            
            try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD); 
                // Step 2:Create a statement using connection object
                PreparedStatement selectStmt = connection.prepareStatement(SELECT_QUERY);
            ) {
            int id = instance.getUserID();

            selectStmt.setInt(1, id);
            
            ResultSet result = selectStmt.executeQuery();
            
            while (result.next()){
                einheiten.getChildren().add(new BoxDynamischBlauGroß(result.getString("name"), result.getInt("dauer"), "", result.getInt("trainingseinheit_id")));
            }
            
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        });
        
        
    
}
public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    @FXML
    private void neueEinheit(ActionEvent event) {
        MobileApplication.getInstance().switchView(EINHEIT_ERSTELLEN_VIEW);
    }

}
