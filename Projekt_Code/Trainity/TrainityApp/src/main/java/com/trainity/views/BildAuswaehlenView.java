package com.trainity.views;

import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class BildAuswaehlenView {
    
    public View getView() {
        try {
            View view = FXMLLoader.load(BildAuswaehlenView.class.getResource("bildAuswaehlen.fxml"));
            return view;
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}
