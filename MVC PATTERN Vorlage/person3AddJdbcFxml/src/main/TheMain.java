package main;


import java.sql.*;
import java.util.logging.*;
import javafx.application.Application;
import javafx.stage.Stage;
import person.viewController.*;


public class TheMain extends Application {

  @Override
  public void start(Stage stage) {
    try {
      String url = "jdbc:derby://localhost:1527/personDb";
      String user = "app";
      String pwd = "app";
      Connection connection = DriverManager.getConnection(url, user, pwd);
      Statement statement = connection.createStatement();

      PersonC.show(stage, statement);
    }
    catch (SQLException ex) {
      Logger.getLogger(TheMain.class.getName()).log(Level.SEVERE, null, ex);
    }
  }


  public static void main(String[] args) {
    launch(args);
  }
}
