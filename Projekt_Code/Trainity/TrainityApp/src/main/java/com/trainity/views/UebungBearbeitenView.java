package com.trainity.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class UebungBearbeitenView {
    
    public View getView() {
        try {
            View view = FXMLLoader.load(UebungBearbeitenView.class.getResource("uebungBearbeiten.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
