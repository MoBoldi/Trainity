package com.trainity;

import com.gluonhq.charm.glisten.application.MobileApplication;
import static com.trainity.Trainity.LOGIN_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_NotEditable_VIEW;
import static com.trainity.Trainity.UEBUNG_BEARBEITEN_VIEW;
import static com.trainity.Uebung.printSQLException;
import com.trainity.views.UebungBearbeitenNotEditablePresenter;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author julian
 */
public class BoxDynamischGruen extends HBox {

    private String name;
    private int rep;
    private String beschreibung;
    private Statement stmt;
    private int id;
      private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static final String SELECT_QUERY = "SELECT bildName FROM trainingsuebung WHERE  name = ?";

    

    public BoxDynamischGruen(String name, int rep, String beschreibung, boolean includeTrash, int id) {

        this.setName(name);
        this.setRep(rep);
        this.setBeschreibung(beschreibung);
        this.setId(id);
        addContent(includeTrash);
      

    }

    
    
    public void addContent(boolean includeTrash) {

//-------------------------------------------------------------------------------------
//HBOX AUSSEN
        HBox hbox = new HBox();

        hbox.setPrefWidth(275);
        hbox.setPrefHeight(50);
        //Cursor

        //Background-Color
        //hbox.setBackground(new Background(new BackgroundFill(Color.rgb(64, 194, 17), CornerRadii.EMPTY, Insets.EMPTY)));
        hbox.setStyle("        -fx-border-color: rgb(64, 194, 17);"
                + "        -fx-border-radius: 5;" + "       -fx-background-radius: 5;" + "-fx-background-color:rgb(64, 194, 17);"
        );

        /*
        EventHandler<MouseEvent> mouseEventFilter = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              //  System.out.println("Mouse event filter has been called");
            }
        };
         */
     
//-------------------------------------------------------------------------------------
//Image View erstellen und als Children in HBox hinzufügen






        int ID = getID();

        String imgName ="";
 // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT bildName FROM trainingsuebung WHERE  trainingsuebung_id = '" + ID + "'")) {
            //preparedStatement.setString(1, searchString);

            ResultSet rs = preparedStatement.executeQuery();

             
             
            while (rs.next()) {

          

                imgName = rs.getString("bildName");

                ///___________------------------
                
               // Sout Ausgabe später löschen 
             //   System.out.println(trainingsuebung_id);
               // System.out.println(trainingsname);
                //System.out.println(rep);
             //   System.out.println(beschreibung);
               
if(imgName == null){

imgName = "051-athlete.png";
        
        }
                
            
                

            }
        
            
            
        } catch (SQLException e) {
            printSQLException(e);
        }


String wholeName = "icon/" + imgName;

        Image image = new Image(""+wholeName+"");


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
        
        
                hb2.setMargin(iv2, new Insets(0, 0, 4, 12));

        
              vbox.getChildren().add(hb2);   
              
              //-------------------------------------------------------------------------------------
//LABEL 2 erstellen    (Rep)
        Label label2 = new Label(getRep() + " x");

        label2.setTextFill(Color.WHITE);
        label2.setOpacity(0.80);

        hb2.getChildren().add(label2);

        hb2.setMargin(label2, new Insets(0, 0, 2, 2));

        hb2.setMargin(iv2, new Insets(1.2, 0, 4, 12));

        
        
        
        if(includeTrash){
       
                   
        //-------------------------------------------------------------------------------------
//Trash Can IMG     
      Image image3 = new Image("pictures/trashcanWhite.png");

        ImageView iv3 = new ImageView();

        iv3.setFitWidth(16);
        iv3.setFitHeight(19);

        iv3.setImage(image3);

        hbox.setCursor(Cursor.HAND);

        hbox.getChildren().add(iv3);
        hbox.setMargin(iv3, new Insets(15.2, 0, 0, 53));
   


        
           EventHandler<MouseEvent> mouseEventHandler2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Deleting this ....");

            }
        };
           

        iv3.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandler2);
        

        
        
        }
        
  
        EventHandler<MouseEvent> mouseEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Mouse event handler has been called");
                System.out.println("Julian Part nicht ändern !!!");
                
                int idSave = getID();
                
                
                System.out.println(idSave);
          
                UebungBearbeitenNotEditablePresenter.USERID = idSave;
                
             /*   
                 UebungBearbeitenNotEditablePresenter p = new UebungBearbeitenNotEditablePresenter();
                 System.out.println("Calling");
                 p.fillValues();
                */
                
        
                
               MobileApplication.getInstance().switchView(UEBUNG_BEARBEITEN_NotEditable_VIEW);

            }
     
        };

        hbox.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandler);

//-------------------------------------------------------------------------------------
//Margin       
        // Oben Rechts Unten Links
        hbox.setMargin(iv1, new Insets(10, 0, 0, 6));

        hbox.setMargin(pane, new Insets(7,0,7, 6));

        hbox.setMargin(vbox, new Insets(0, 0, 0, 1));

        this.getChildren().add(hbox);

    }

    
    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
