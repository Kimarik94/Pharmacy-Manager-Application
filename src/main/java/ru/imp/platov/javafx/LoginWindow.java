package ru.imp.platov.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.imp.platov.appUsers.Role;
import ru.imp.platov.appUsers.UserDAO;
import ru.imp.platov.database.DatabaseManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class LoginWindow extends Stage {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;

    public Parent getRoot() {
        return this.root;
    }

    private Parent root;
    private Stage stage;

    public LoginWindow(Stage stage) throws SQLException {
        this.stage = stage;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginWindowStyle.fxml"));
            loader.setController(this);
            this.root = loader.load();
            stage.setTitle("Login");
            InputStream iconStream = getClass().getResourceAsStream("/FM_icon.png");
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
        }catch(IOException ex){
            ex.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }
        initialize();
    }

    public Stage getStage() {
        return this.stage;
    }

    public void initialize() {
        loginButton.setOnAction((ActionEvent event) -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            String role = null;
            try {
                UserDAO userDAO = new UserDAO();
                role = userDAO.checkPassword(username, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (role != null) {
                WindowEvent closeEvent = new WindowEvent(loginButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST);
                getStage().fireEvent(closeEvent);
                if (role.equalsIgnoreCase(String.valueOf(Role.admin))) {
                    try {
                        WindowController.openAdminWindow();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (role.equalsIgnoreCase(String.valueOf(Role.pharmacist))) {
                    try {
                        WindowController.openPharmacistWindow();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Invalid username or password. Or there is no such user");
                alert.setContentText("Please try again.");
                alert.showAndWait();
            }
        });
        cancelButton.setOnAction((ActionEvent event) -> {
            WindowEvent closeEvent = new WindowEvent(loginButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST);
            cancelButton.getScene().getWindow().fireEvent(closeEvent);
        });
    }
}
