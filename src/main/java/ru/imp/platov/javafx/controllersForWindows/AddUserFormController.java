package ru.imp.platov.javafx.controllersForWindows;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.imp.platov.appUsers.User;
import ru.imp.platov.appUsers.UserDAO;

import java.sql.SQLException;

public class AddUserFormController{
    private ObservableList<User> userList;
    private UserDAO userDAO;

    public ObservableList<User> getUserList() {
        return userList;
    }
    public void setUserList(ObservableList<User> userList) {
        this.userList = userList;
    }

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField roleTextField;
    @FXML
    private Button finishButton;

    @FXML
    private void handleFinishButton(ActionEvent event) throws SQLException {
        try {
            userDAO = new UserDAO();
            User newUser = new User(null,
                    nameTextField.getText(),
                    surnameTextField.getText(),
                    loginTextField.getText(),
                    passwordField.getText(),
                    roleTextField.getText());
            userDAO.addUser(newUser);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add User Success!");
            alert.setHeaderText("Success!");
            alert.setContentText("User Added Successfully!");
            alert.show();
            Stage stage = (Stage) finishButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
            e.printStackTrace();
        }
    }


}

