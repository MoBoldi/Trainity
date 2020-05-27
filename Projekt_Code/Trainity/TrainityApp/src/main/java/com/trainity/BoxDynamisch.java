
package com.trainity;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;

/**
 *
 * @author julian
 */
public class BoxDynamisch {
    
  private String name;
  private int rep;
  private String beschreibung;
    


public BoxDynamisch(BoxDynamisch b){

        this.setName(b.getName());
        this.setRep(b.getRep());
        this.setBeschreibung(b.getBeschreibung());
     
        }





        public void addContent(){
        
        
        HBox hbox = new HBox();
        
        
        
        }






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }


    
}

