package com.trainity;

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
