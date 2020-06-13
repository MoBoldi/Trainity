package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.trainity.JdbcDao;
import com.trainity.UserSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class LoginPresenter {

    @FXML
    private View login;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button submitButton;

    public void initialize() {
        login.setShowTransitionFactory(BounceInRightTransition::new);

        final Tooltip tp = new Tooltip();
        tp.setText("Passwortanforderungen:\n"
                + "- mindestens 8 Zeichen\n"
                + "- mindestens 1 Groß- & 1 Kleinbuchstabe\n"
                + "- mindestens 1 Ziffer");
        passwordField.setTooltip(tp);

        login.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setTitleText("Login");;
            }
        });
    }
    
    @FXML
    public void switchRegister(ActionEvent event) {
        System.out.println("You clicked me!");
        MobileApplication.getInstance().switchView("Registrieren View");
    }

    @FXML
    public void login(ActionEvent event) throws SQLException, NoSuchAlgorithmException {

        Window owner = submitButton.getScene().getWindow();

        System.out.println(emailField.getText());
        System.out.println(passwordField.getText());

        if (emailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Bitte geben Sie Ihre E-Mail Adresse an!");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Bitte geben Sie ein Passwort ein!");
            return;
        }
        if (!isValidPassword(passwordField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Das Passwort erfüllt nicht den Anferderungen!");
            return;
        }
        if (!isValidEmail(emailField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Bitte geben Sie eine gültige E-Mail Adresse an!");
            return;
        }

        String email = emailField.getText();
        //String to encrypt
        String password = passwordField.getText();

        //encrypt
        String algorithm = "MD5";
        String hashedPassword = generateHash(password, algorithm);
        System.out.println("MD5 Hash: " + hashedPassword);

        JdbcDao jdbcDao = new JdbcDao();
        boolean flag = jdbcDao.validateLogin(email, hashedPassword);

        if (!flag) {
            infoBox("Email oder Passwort ungültig!", null, "Failed");
        } else {
            infoBox("Login erfolgreich!", null, "Success");
            UserSession.getInstace(jdbcDao.getUserId(email));
            MobileApplication.getInstance().switchView("Startseite View");
        }

    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    private static String generateHash(String password, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();
        byte[] hash = digest.digest(password.getBytes());
        return bytesToStringHex(hash);
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToStringHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + ".{8,}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isValidEmail(String email) {
        String regexMail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern p = Pattern.compile(regexMail);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
