package com.trainity;

import com.gluonhq.charm.glisten.application.MobileApplication;
import static com.trainity.Trainity.UEBUNG_AUSWAEHLEN_VIEW;
import static com.trainity.Uebung.printSQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.TimeZone;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Trainingseinheit {
    
    
     private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<Number> dauer = new SimpleObjectProperty<>();
    

    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static final String INSERT_QUERY = "INSERT INTO trainingseinheit (name, dauer, user_id,bildName) VALUES (?, ?, ?, ?)";

    
    
    
    
    public Trainingseinheit(String name, int dauer){
        
        this.setName(name);
        this.setDauer(dauer);
        

    }
    
    
      public Trainingseinheit() {
        clear();
    }

    public final void clear() {
        this.setName(null);
        this.setDauer(null);
      
    }
    
    
    public void saveTrainingsEinheit()  {
        

        //   int userID = getUserID();
        int userID = 5;
        String name = getName();
        int dauer = (int) getDauer();
     
        String bildName =getRandomImage();

        // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, dauer);
            preparedStatement.setInt(3, userID);
            preparedStatement.setString(4, bildName);


            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            
            
            
            
            //ADDING TO LIST
            
            
            
            
            MobileApplication.getInstance().switchView(UEBUNG_AUSWAEHLEN_VIEW);

        } catch (SQLException e) {
            printSQLException(e);
        }

    } 
    public String getRandomImage(){
    
    
        Random zufall = new Random(); // neues Random Objekt, namens zufall
        int zufallsZahl = zufall.nextInt(5);
        String name ="";
        switch(zufallsZahl){
        
            case 0: 
                name =  "051-athlete-3.png";
                break;
             
            case 1:
                name = "051-athlete-4.png";
                break;
            case 2: 
                name =  "051-athlete-5.png";
                break;
             
            case 3:
                name = "051-athlete-7.png";
                break;
        
            case 4: 
                name =  "051-athlete-8.png";
                break;
             
            case 5:
                name = "051-athlete.png";
                break;
        }
        
     
     return name;   
    }
    

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final Number getDauer() {
        return dauer.get();
    }

    public final void setDauer(Number value) {
        dauer.set(value);
    }

    public ObjectProperty<Number> dauerProperty() {
        return dauer;
    }
      
}
