package com.trainity.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import static com.gluonhq.charm.glisten.application.MobileApplication.HOME_VIEW;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import com.trainity.JdbcDao;
import static com.trainity.JdbcDao.printSQLException;
import static com.trainity.Trainity.LOGIN_VIEW;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.TimeZone;
import java.util.regex.*;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RegistrierenPresenter {

    @FXML
    private View registrieren;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private Button submitButton;
    @FXML
    private TextField vornameField;
    @FXML
    private TextField nachnameField;
    @FXML
    private ImageView ImageView;

    private static final Image avocadoImage = new Image("pictures/avocado.png");

    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String SELECT_QUERY = "SELECT * from benutzer where email=?";

    public void initialize() {
        registrieren.setShowTransitionFactory(BounceInRightTransition::new);

        final Tooltip tp = new Tooltip();
        tp.setText("Passwortanforderungen:\n"
                + "- mindestens 8 Zeichen\n"
                + "- mindestens 1 Groß- & 1 Kleinbuchstabe\n"
                + "- mindestens 1 Ziffer");
        passwordField.setTooltip(tp);
        confirmPasswordField.setTooltip(tp);

        registrieren.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setTitleText("Registrieren");
            }
        });

        ImageView.setImage(avocadoImage);

    }

    @FXML
    public void switchLogin(ActionEvent event) {
        MobileApplication.getInstance().switchView(LOGIN_VIEW);
    }

    @FXML
    public void register(ActionEvent event) throws SQLException, Exception {

        Window owner = submitButton.getScene().getWindow();

        if (vornameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Bitte geben Sie ihren Vornamen an!");
            return;
        }
        if (vornameField.getText().length() < 2) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Vorname muss mindestens 2 Zeichen haben!");
            return;
        }
        if (nachnameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Bitte geben Sie ihren Nachnamen an!");
            return;
        }
        if (nachnameField.getText().length() < 2) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Nachname muss mindestens 2 Zeichen haben!");
            return;
        }
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
        if (confirmPasswordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Bitte geben Sie ein Passwort ein!");
            return;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Das Passwort stimmt nicht überein!");
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
        if (!isValidName(vornameField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Bitte geben Sie einen gültigen Vornamen an!");
            return;
        }
        if (!isValidName(nachnameField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Bitte geben Sie einen gültigen Nachnamen an!");
            return;
        }
        if (checkEmail(emailField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Die Email Adresse ist schon einem Account zugeordnet!");
            return;
        }

        String vorname = vornameField.getText().trim();
        String nachname = nachnameField.getText().trim();
        String email = emailField.getText().trim();
        //String to encrypt
        String password = passwordField.getText().trim();

        //encrypt
        String algorithm = "MD5";
        String hashedPassword = generateHash(password, algorithm);

        JdbcDao jdbcDao = new JdbcDao();
        jdbcDao.insertRecord(vorname, nachname, email, hashedPassword);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Erfolgreich registriert!",
                "Willkommen " + vornameField.getText() + "!");
        MobileApplication.getInstance().switchView(LOGIN_VIEW);
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

    public static boolean isValidName(String name) {
        String regexName = "^[A-ZÄÖÜ][a-zäöüß]*$";

        Pattern p = Pattern.compile(regexName);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean checkEmail(String email) throws SQLException {
        boolean usernameExists = false;
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            preparedStatement.setString(1, email);
            ResultSet r1 = preparedStatement.executeQuery();
            if (r1.next()) {
                usernameExists = true;
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return usernameExists;
    }
}
