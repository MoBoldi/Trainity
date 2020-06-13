/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trainity;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author moritz
 */
public class ImagePickable extends HBox{
    ImageView iv; 
    public ImagePickable(String name){
        iv = new ImageView();
        try {
            Image img = new Image("pictures/"+name,56,56,true, false);
            iv.setImage(img);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        iv.setId(name);
        this.setPrefHeight(100);
        this.setPrefWidth(100);
        this.setStyle("        -fx-border-color: rgb(64, 194, 17);"
                +     "-fx-background-color:rgb(64, 194, 17);"
        );
        
        EventHandler<MouseEvent> mouseEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(getName());

            }
        };
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandler);
        System.out.println(this.onMouseClickedProperty());
        
        this.getChildren().add(iv);
    }
    public String getName(){
        return this.getId();
    }
}
