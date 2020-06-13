package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import static java.awt.PageAttributes.ColorType.COLOR;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class WochenplanPresenter {

    @FXML
    private View wochenplan;
    @FXML
    private VBox outerBox;
    @FXML
    private VBox outerHeadingBox;
    @FXML
    private HBox innerHeadingBox;
    @FXML
    private Label labelHeading;
    @FXML
    private HBox DayHBox;
    @FXML
    private VBox DayInnerVBox;
    @FXML
    private Label label12;
    @FXML
    private Label label121;
    @FXML
    private Label label1211;
    @FXML
    private Label label1212;
    @FXML
    private Label label1214;
    @FXML
    private Label label1213;
    @FXML
    private Label label12141;
    @FXML
    private HBox saveButtonHBox;
    @FXML
    private Button ButtonSave;
    @FXML
    private ImageView imageViewSafes;
    
    private Boolean[] selected = {false, false, false, false, false, false, false};
    @FXML
    private HBox Monday;
    @FXML
    private HBox Tuesday;
    @FXML
    private HBox Wednesday;
    @FXML
    private HBox Thursday;
    @FXML
    private HBox Friday;
    @FXML
    private HBox Saturday;
    @FXML
    private HBox Sunday;
    
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/trainity?zeroDateTimeBehavior=convertToNull";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";

    private static final String INSERT_QUERY = "insert into ziel (datum, benutzer_id, status) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "select * from ziel where datum = ? and benutzer_id = ?";
    private static final String ALTER_QUERY = "UPDATE ziel set status = ? where datum = ? and benutzer_id = ?";
    private static final String SELECT_TODAY_QUERY = "SELECT status from ziel where datum = ? and benutzer_id = ?";
    
    public void initialize() {
        wochenplan.setShowTransitionFactory(BounceInRightTransition::new);
        
        wochenplan.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> 
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Wochenplan");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
            }
        });
    
}

    @FXML
    private void changeColor(MouseEvent event) {
        //geklickte HBox speichern 
        HBox source = (HBox) event.getSource();
        //Farbe ändern und in selected speichern 
        switch(source.getId()){
            case "Monday": toggleSelected(source, 0);
            break;
            case "Tuesday": toggleSelected(source, 1);
            break;
            case "Wednesday": toggleSelected(source, 2);
            break;
            case "Thursday": toggleSelected(source, 3);
            break;
            case "Friday": toggleSelected(source, 4);
            break;
            case "Saturday": toggleSelected(source, 5);
            break;
            case "Sunday": toggleSelected(source, 6);
            break;
        }
    }
    private void toggleSelected(HBox source, int id){
        //Wenn dieser Tag bereits ausgewählt wurde, wird er wieder verworfen
        if (selected[id]){
            selected[id] = false;
            source.setStyle("-fx-background-color:rgb(242, 110, 46); -fx-background-radius:10");
        //Ansonsten wird dieser ausgewählt
        }else {
            selected[id] = true;
            source.setStyle("-fx-background-color:rgb(64, 194, 17); -fx-background-radius:10");
        }
    }

    @FXML
    private void savePlan(ActionEvent event) {
        //Wochenplan hier in DB speichern 
        //Wochenplan ist in selected[] gespeichert
        try (
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD); 
                // Step 2:Create a statement using connection object
                PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY);
                PreparedStatement selectStmt = connection.prepareStatement(SELECT_QUERY);
                PreparedStatement alterStmt = connection.prepareStatement(ALTER_QUERY);
                PreparedStatement selectTodayStmt = connection.prepareStatement(SELECT_TODAY_QUERY);
                
            ) {
            //Calendar c anlegen und ersten Tag der Woche setzen
            Calendar c = Calendar.getInstance(Locale.GERMAN);
            //Heutigen Tag speichern da überschreiben der vorherigen tage nicht möglich ist
            Date today = c.getTime();
            c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
         
            //nachschauen ob die Woche nur aktualisiert wird
            //Datum am Wochenanfang checken und überprüfen
            selectStmt.setString(1, c.get(Calendar.YEAR) + "-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));
            //Benutzer_ID setzen und überprüfen
            //Später ändern
            selectStmt.setInt(2, 1);
            ResultSet result = selectStmt.executeQuery();
            if (result.next()==false){
                for (int i = 0; i < selected.length; i++){
                    //Daten für die ganze Woche in Calendar gespeichert 
                    insertStmt.setString(1, c.get(Calendar.YEAR) + "-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));
                    c.add(Calendar.DAY_OF_WEEK, 1);
                    //Benutzer_ID setzen 
                    //Später ändern
                    insertStmt.setInt(2, 1);
                    //Status speichern
                    //Status 1 = Training geplant 
                    //Status 0 = Training erledigt
                    //Status null = kein Training an diesem Tag
                    if (selected[i]){
                        alterStmt.setBoolean(1, true);
                    } else {
                        alterStmt.setNull(1, java.sql.Types.NULL);
                    }
                    System.out.println(insertStmt);
                    // Step 3: Execute the query or update query
                    insertStmt.executeUpdate();
                }
            } else {
                c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
             
                
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                //Heutiger Tag 
                selectTodayStmt.setString(1,formatter.format(today));
                //Benutzer 
                //Später ändern
                selectTodayStmt.setInt(2, 1);
                //Nachschauen ob der User am heutigen Tag bereits ein Training absolviert hat 
                //Dieses darf nämlich nicht mehr überschrieben werden - changeToday Boolean
                ResultSet todayResult = selectTodayStmt.executeQuery();
                Boolean changeToday = true;
                if (todayResult.next()){
                    if (!(null == todayResult.getString(1)) && todayResult.getInt(1)==0){
                        changeToday = false;
                    }
                }
                for (int i = 0; i < 7; i++){
                    //nur die nächsten Tage einschließlich heute bearbeiten
                    
                    if (today.before(c.getTime()) || (formatter.format(today).equals(formatter.format(c.getTime())) && changeToday)){
                        //Daten für die ganze Woche in Calendar verändert 
                        //System.out.println(i);

                        //Status speichern
                        //Status 1 = Training geplant 
                        //Status 0 = Training erledigt
                        //Status null = kein Training an diesem Tag
                        if (selected[i]){
                            alterStmt.setBoolean(1, true);
                        } else {
                            alterStmt.setNull(1, java.sql.Types.NULL);
                        }

                        
                        //Datum 
                        alterStmt.setString(2, c.get(Calendar.YEAR) + "-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));
                        c.add(Calendar.DAY_OF_WEEK, 1);

                        //Benutzer_ID 
                        //Später ändern
                        alterStmt.setInt(3, 1);

                        // Step 3: Execute the query or update query
                        alterStmt.executeUpdate();
                    } else {
                        c.add(Calendar.DAY_OF_WEEK, 1);
                    }
                }
            }
            
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        
        
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
}
