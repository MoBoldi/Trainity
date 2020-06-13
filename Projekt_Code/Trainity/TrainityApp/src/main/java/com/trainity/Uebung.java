package com.trainity;

import com.gluonhq.charm.glisten.application.MobileApplication;
import static com.trainity.Trainity.EINHEIT_BEARBEITEN_VIEW;
import static com.trainity.Trainity.UEBUNG_AUSWAEHLEN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_NotEditable_VIEW;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 *
 * @author Julian Haas
 */
public class Uebung {

    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<Number> wiederholungen = new SimpleObjectProperty<>();
    private final StringProperty beschreibung = new SimpleStringProperty();

    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static final String INSERT_QUERY = "INSERT INTO trainingsuebung (trainingsname, wiederholung, beschreibung, benutzer_id, bild_id) VALUES (?, ?, ?, ?, ?)";
   // private static final String UPDATE_QUERY = "INSERT INTO registration (full_name, email_id, password) VALUES (?, ?, ?)";

    // private final StringProperty imgPfad = new SimpleStringProperty();
    /* public Uebung(Uebung u) {

        this.setName(u.getName());
        this.setWiederholungen(u.getWiederholungen());
        this.setBeschreibung(u.getBeschreibung());
       // this.setImgPfad(u.getImgPfad());

    }*/
    public Uebung(String name, int rep, String beschreibung) {

        this.setName(name);

        this.setWiederholungen(rep);
        this.setBeschreibung(beschreibung);
        // this.setImgPfad(u.getImgPfad());
        System.out.println(this.getName() + "  " + this.getWiederholungen() + "   " + this.getBeschreibung());

    }

    public Uebung() {
        clear();
    }

    public final void clear() {
        this.setName(null);
        this.setWiederholungen(null);
        this.setBeschreibung(null);
    }

    public void saveuebung()  {
        

        //   int userID = getUserID();
        int userID = 5;
        String name = getName();
        int wiederholungen = (int) getWiederholungen();
        String beschreibung = getBeschreibung();
        int bildID = 5;

        // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, wiederholungen);
            preparedStatement.setString(3, beschreibung);
            preparedStatement.setInt(4, userID);
            preparedStatement.setInt(5, bildID);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            
            
            
            
            //ADDING TO LIST
            
            
            
            
            MobileApplication.getInstance().switchView(UEBUNG_AUSWAEHLEN_VIEW);

        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    public int getUserID() {
        System.out.println("Returning the User ID");
        return 1;
    }

    public void updateUebung() {
        //Überprüfung beinhalted

        
        System.out.println(getName());

        String sql = "UPDATE DBNAME SET("
                + "   name=  '" + getName() + "' "
                + "  , rep = '" + getWiederholungen() + "' "
                + "  , beschreibung '" + getBeschreibung() + "' "
                + ")";

      

    }

  

    
    //-----------------------------------------
    //Set-er Get-er IMGPFAD
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
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

    private void FehlerBox(String fehlermeldung) {
        System.out.println(fehlermeldung);
    }

  
    
    
    

    //-----------------------------------------
    //Set-er Get-er Name
    public final void setName(String value) {
        name.set(value);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

//-----------------------------------------
    //Set-er Get-er Wiederholungen
    public final void setWiederholungen(Number value) {
        wiederholungen.set(value);
    }

    public Number getWiederholungen() {
        return wiederholungen.get();
    }

    public ObjectProperty<Number> wiederholungenProperty() {
        return wiederholungen;
    }

//-----------------------------------------
    //Set-er Get-er Beschreibung
    public final void setBeschreibung(String value) {
        beschreibung.set(value);
    }

    public String getBeschreibung() {
        return beschreibung.get();
    }

    public StringProperty beschreibungProperty() {
        return beschreibung;
    }

}
