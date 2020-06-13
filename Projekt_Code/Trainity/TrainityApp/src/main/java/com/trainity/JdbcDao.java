package com.trainity;

/**
 *
 * @author benjamingolic
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

public class JdbcDao {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:8889/Trainity?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String INSERT_QUERY = "INSERT INTO benutzer (vorname, nachname, email, passwort) VALUES (?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM benutzer WHERE email = ? and passwort = ?";
    private static final String SELECT_USERID_QUERY = "SELECT ID FROM benutzer WHERE email = ?";

    public void insertRecord(String vorname, String nachname, String email, String password) throws SQLException {
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, vorname);
            preparedStatement.setString(2, nachname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public boolean validateLogin(String email, String password) throws SQLException {
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public int getUserId(String email) throws SQLException {
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERID_QUERY)) {
            preparedStatement.setString(1, email);

            System.out.println(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return -1;
    }
}
