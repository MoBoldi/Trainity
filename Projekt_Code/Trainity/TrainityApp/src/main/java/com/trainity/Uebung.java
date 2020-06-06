package com.trainity;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Julian Haas
 */
public class Uebung {

    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<Number> wiederholungen = new SimpleObjectProperty<>();
    private final StringProperty beschreibung = new SimpleStringProperty();
    
   // private final StringProperty imgPfad = new SimpleStringProperty();


    public Uebung(Uebung u) {

        this.setName(u.getName());
        this.setWiederholungen(u.getWiederholungen());
        this.setBeschreibung(u.getBeschreibung());
       // this.setImgPfad(u.getImgPfad());

    }
    
   public Uebung() {
    clear();
  }

    public final void clear() {
        this.setName(null);
        this.setWiederholungen(null);
        this.setBeschreibung(null);
    }
    
    
    
  

    public void saveuebung(Statement stmt) {
        //Überprüfung beinhalted
        killAndFill();

        System.out.println(getName());
        
        String sql = "insert into DBNAME VALUES("
                + "         '" + getName() + "' "
                 + "        , '" + getWiederholungen() + "' "
                 + "        , '" + getBeschreibung() + "' "
                + ")";

        try {
            stmt.executeQuery(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Uebung.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

       public void updateUebung(Statement stmt) {
        //Überprüfung beinhalted
        killAndFill();

        System.out.println(getName());
        
        String sql = "UPDATE DBNAME SET("
                + "   name=  '" + getName() + "' "
                 + "  , rep = '" + getWiederholungen() + "' "
                 + "  , beschreibung '" + getBeschreibung() + "' "
                + ")";

        try {
            stmt.executeQuery(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Uebung.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void killAndFill() {

        if (checkName(name.get())) {
            FehlerBox("Fehlermeldung");
        }

        if (checkWiederholung(wiederholungen.get())) {
            FehlerBox("Fehlermeldung");
        }

        if (checkBeschreibung(getBeschreibung())) {
            FehlerBox("Fehlermeldung");
        }

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

    
    
    //-----------------------------------------
    //Set-er Get-er IMGPFAD
    
    
 

    
    
    
    
    
    private void FehlerBox(String fehlermeldung) {
        System.out.println(fehlermeldung);
    }

    private boolean checkName(String get) {
        return true;

    }

    private boolean checkBeschreibung(String get) {
        return true;
    }

    private boolean checkWiederholung(Number get) {
        return true;
    }

}
