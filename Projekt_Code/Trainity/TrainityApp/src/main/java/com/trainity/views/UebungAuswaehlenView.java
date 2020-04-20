package com.trainity.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class UebungAuswaehlenView {
    
    public View getView() {
        try {
            View view = FXMLLoader.load(UebungAuswaehlenView.class.getResource("uebungAuswaehlen.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
