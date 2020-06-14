/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trainity;

import com.gluonhq.charm.glisten.application.MobileApplication;
import static com.trainity.EinheitSession.instanceE;
import static com.trainity.Trainity.EINHEIT_BEARBEITEN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_VIEW;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static com.trainity.UserSession.instance;

/**
 *
 * @author julian
 */
public class BoxDynamischBlauKlein extends HBox {

    private String name;
    private int duration;
    private String beschreibung;
    private int id; 

    public BoxDynamischBlauKlein(String name, int duration, String beschreibung, int id) {

        this.setName(name);
        this.setDuration(duration);
        this.setBeschreibung(beschreibung);
        this.setId(id);
        addContent();

    }

    public void addContent() {

//-------------------------------------------------------------------------------------
//HBOX AUSSEN
        HBox hbox = new HBox();

        hbox.setPrefWidth(310);
        hbox.setPrefHeight(50);
        //Cursor
        hbox.setCursor(Cursor.HAND);

        //Background-Color
        //hbox.setBackground(new Background(new BackgroundFill(Color.rgb(64, 194, 17), CornerRadii.EMPTY, Insets.EMPTY)));
        hbox.setStyle("        -fx-border-color: rgb(33, 150, 243);"
                + "        -fx-border-radius: 5;" + "       -fx-background-radius: 5;" + "-fx-background-color: rgb(33, 150, 243);"
        );

        /*
        EventHandler<MouseEvent> mouseEventFilter = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              //  System.out.println("Mouse event filter has been called");
            }
        };
         */
        EventHandler<MouseEvent> mouseEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //  System.out.println("Mouse event handler has been called");
                instanceE = null;
                EinheitSession.getInstace(id);
                MobileApplication.getInstance().switchView(EINHEIT_BEARBEITEN_VIEW);
                

            }
        };

        hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandler);

//-------------------------------------------------------------------------------------
//Image View erstellen und als Children in HBox hinzufügen
        Image image = new Image("pictures/051-athlete-7.png");

        ImageView iv1 = new ImageView();

        iv1.setFitWidth(30);
        iv1.setFitHeight(30);

        iv1.setImage(image);

        hbox.getChildren().add(iv1);

//-------------------------------------------------------------------------------------
//Dünnes Pane erstellen    
        HBox pane = new HBox();

        pane.prefWidth(1);
        pane.prefHeight(30);

        //Nimmt die unten angeführten Methoden nicht an 
        //      pane.setStyle( "       -fx-background-color:white;");
        //  pane.setBorder(new Border(new BorderStroke(Color.WHITE, 
        //       BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        pane.setStyle("       -fx-background-color:white;" + "-fx-border-color:white;" + "  -fx-background-insets: 0 0 -1 0, 0, 1, 2;"
                + "  -fx-background-radius: 1px, 1px, 1px, 1px;");

        pane.setOpacity(0.67);

        hbox.getChildren().add(pane);

//-------------------------------------------------------------------------------------
//Dünne VBOX erstellen    
        VBox vbox = new VBox();

        vbox.setPrefWidth(140);
        vbox.setPrefHeight(32.5);

//-------------------------------------------------------------------------------------
//LABEL 1 erstellen     (Name)
        Label label = new Label(getName());

        label.setTextFill(Color.WHITE);

        // label.setFont(Font.font("Cambria", 32));
        vbox.getChildren().add(label);

        vbox.setMargin(label, new Insets(8, 0, 0, 10));

        hbox.getChildren().add(vbox);

//-------------------------------------------------------------------------------------      
        //HBOX
        HBox hb2 = new HBox();

        hb2.setPrefWidth(30);
        hb2.setPrefHeight(15);

//-------------------------------------------------------------------------------------
//Image View erstellen und als Children in HBox hinzufügen
        Image image2 = new Image("pictures/zukunft_icon_white.png");

        ImageView iv2 = new ImageView();

        iv2.setFitWidth(15);
        iv2.setFitHeight(15);

        iv2.setImage(image2);

        hb2.getChildren().add(iv2);

        vbox.getChildren().add(hb2);

//-------------------------------------------------------------------------------------
//LABEL 2 erstellen    (Rep)
        Label label2 = new Label(getDuration() + " min");

        label2.setTextFill(Color.WHITE);
        label2.setOpacity(0.80);

        hb2.getChildren().add(label2);

        hb2.setMargin(label2, new Insets(0, 0, 2, 2));

        hb2.setMargin(iv2, new Insets(1.2, 0, 4, 12));

//-------------------------------------------------------------------------------------
//Margin       
        // Oben Rechts Unten Links
        hbox.setMargin(iv1, new Insets(10, 0, 0, 6));

        hbox.setMargin(pane, new Insets(7, 0, 7, 6));

        hbox.setMargin(vbox, new Insets(0, 0, 0, 1));

        this.getChildren().add(hbox);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    private void setId(int id) {
        this.id = id;
    }

}