package ru.imp.platov.javafx.controllersForWindows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.imp.platov.appUsers.Role;
import ru.imp.platov.appUsers.User;
import ru.imp.platov.appUsers.UserDAO;
import ru.imp.platov.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserInfoWindow {
    UserDAO userDAO = new UserDAO();
    User user;
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passTextField;

    @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private ComboBox<Integer> idComboBox;

    @FXML
    private Button doneButton;

    @FXML
    private Button cancelButton;

    @FXML
    void initialize() throws SQLException {
        roleComboBox.getItems().setAll(Role.values());
        idComboBox.getItems().setAll(allId());
        idComboBox.setOnAction(event -> {
            Integer userId = idComboBox.getValue();
            try {
                this.user = userDAO.getUserById(userId);
                nameTextField.setText(user.getUser_name());
                surnameTextField.setText(user.getUser_surname());
                loginTextField.setText(user.getUser_login());
                passTextField.setText(user.getUser_password());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        idComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Integer userId = Integer.parseInt(newVal.toString());
                Role userRole = null;
                try {
                    userRole = getRoleForUser(userId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                roleComboBox.setValue(userRole);
            }
        });
    }

    private Role getRoleForUser(Integer userId) throws SQLException {
        User user;
        user = userDAO.getUserById(userId);
        if (user.getUser_role().equals("admin")) return Role.admin;
        else if (user.getUser_role().equals("pharmacist")) return Role.pharmacist;
        else return null;
    }

    public List<Integer> allId() {
        String sql = "SELECT user_id FROM users";
        List<Integer> allUsersId;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            allUsersId = new ArrayList<>();
            while (rs.next()) {
                Integer tempUserId = rs.getInt("user_id");
                allUsersId.add(tempUserId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsersId;
    }

    public void handleDoneButton(ActionEvent event) throws SQLException {
        try {
            Integer selected_Id = idComboBox.getValue();
            User newUser = new User(selected_Id,
                    nameTextField.getText(),
                    surnameTextField.getText(),
                    loginTextField.getText(),
                    passTextField.getText(),
                    roleComboBox.getSelectionModel().getSelectedItem().toString());
            userDAO.updateUser(newUser);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "User updated successfully");
            successAlert.showAndWait();
        } catch (NumberFormatException ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid user id");
            errorAlert.showAndWait();
        } catch (SQLException ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error updating user: " + ex.getMessage());
            errorAlert.showAndWait();
        }
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}