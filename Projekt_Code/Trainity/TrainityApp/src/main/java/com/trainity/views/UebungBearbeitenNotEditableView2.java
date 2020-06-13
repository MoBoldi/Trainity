package com.trainity.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class UebungBearbeitenNotEditableView2 {
    
    public View getView() {
        try {
            View view = FXMLLoader.load(UebungBearbeitenNotEditableView2.class.getResource("uebungBearbeitenNotEditable2.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
