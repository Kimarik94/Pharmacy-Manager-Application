package ru.imp.platov.javafx.controllersForWindows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.imp.platov.appUsers.User;
import ru.imp.platov.appUsers.UserDAO;
import ru.imp.platov.Drugs.DrugItem;
import ru.imp.platov.Drugs.DrugDAO;
import ru.imp.platov.javafx.WindowController;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

public class AdminWindowController {
    private UserDAO userDAO;
    private DrugDAO drugDAO;
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private ObservableList<DrugItem> drugList = FXCollections.observableArrayList();

    public ObservableList<User> getUserList() {
        return userList;
    }
    public void setUserList(ObservableList<User> userList) {
        this.userList = userList;
    }
    public TableView<User> getUsersTable() {
        return usersTable;
    }
    public void setUsersTable(TableView<User> usersTable) {
        this.usersTable = usersTable;
    }
    public ObservableList<DrugItem> getDrugList() {
        return drugList;
    }
    public void setDrugList(ObservableList<DrugItem> drugList) {
        this.drugList = drugList;
    }

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Integer> user_id;
    @FXML
    private TableColumn<User, String> user_name;
    @FXML
    private TableColumn<User, String> user_surname;
    @FXML
    private TableColumn<User, String> user_login;
    @FXML
    private TableColumn<User, String> user_password;
    @FXML
    private TableColumn<User, String> user_role;
    @FXML
    private TableView<DrugItem> drugsTable;
    @FXML
    private TableColumn<DrugItem, Integer> drug_id;
    @FXML
    private TableColumn<DrugItem, String> drug_name;
    @FXML
    private TableColumn<DrugItem, String> drug_manufacturer;
    @FXML
    private TableColumn<DrugItem, String> drug_prod_date;
    @FXML
    private TableColumn<DrugItem, String> drug_expr_date;
    @FXML
    private TableColumn<DrugItem, String> drug_description;
    @FXML
    private TableColumn<DrugItem, String> drug_quantity;
    @FXML
    private TableColumn<DrugItem, BigDecimal> drug_price;
    @FXML
    private TableColumn<DrugItem, Boolean> drug_recipe;
    @FXML
    private Button addUserButton;
    @FXML
    private Button updateUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button exitUserButton;
    @FXML
    private Button addDrugButton;
    @FXML
    private Button updateDrugButton;
    @FXML
    private Button deleteDrugButton;
    @FXML
    private Button exitDrugButton;

    public void initialize() {
        try {
            addUserButton.setOnAction(event -> showAddUserForm());
            addDrugButton.setOnAction(event -> showAddDrugForm());
            exitUserButton.setOnAction(event -> {
                try {
                    exit();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            exitDrugButton.setOnAction(event -> {
                try {
                    exit();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            deleteUserButton.setOnAction(event -> {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Удаление пользователя");
                dialog.setHeaderText(null);
                dialog.setContentText("Введите id пользователя:");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(id -> {
                    try {
                        deleteUser(Integer.parseInt(id));
                        refreshUserTable();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
            deleteDrugButton.setOnAction(event ->{
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Удаление препарата");
                dialog.setHeaderText(null);
                dialog.setContentText("Введите id пользователя:");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(id -> {
                    try {
                        deleteDrug(Integer.parseInt(id));
                        refreshDrugTable();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            });

            updateUserButton.setOnAction(event -> updateUser());
            updateDrugButton.setOnAction(event -> updateDrug());
            userDAO = new UserDAO();
            user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
            user_name.setCellValueFactory(new PropertyValueFactory<>("user_name"));
            user_surname.setCellValueFactory(new PropertyValueFactory<>("user_surname"));
            user_password.setCellValueFactory(new PropertyValueFactory<>("user_password"));
            user_login.setCellValueFactory(new PropertyValueFactory<>("user_login"));
            user_role.setCellValueFactory(new PropertyValueFactory<>("user_role"));
            ObservableList<User> usersList = FXCollections.observableArrayList(userDAO.getAllUsers());
            usersTable.setItems(usersList);

            drugDAO = new DrugDAO();
            drug_id.setCellValueFactory(new PropertyValueFactory<>("drug_id"));
            drug_name.setCellValueFactory(new PropertyValueFactory<>("drug_name"));
            drug_manufacturer.setCellValueFactory(new PropertyValueFactory<>("drug_manufacturer"));
            drug_prod_date.setCellValueFactory(new PropertyValueFactory<>("drug_prod_date"));
            drug_expr_date.setCellValueFactory(new PropertyValueFactory<>("drug_expr_date"));
            drug_description.setCellValueFactory(new PropertyValueFactory<>("drug_description"));
            drug_quantity.setCellValueFactory(new PropertyValueFactory<>("drug_quantity"));
            drug_price.setCellValueFactory(new PropertyValueFactory<>("drug_price"));
            drug_recipe.setCellValueFactory(new PropertyValueFactory<>("drug_recipe"));
            ObservableList<DrugItem> drugsList = FXCollections.observableArrayList(drugDAO.getAllDrugs());
            drugsTable.setItems(drugsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void exit() throws IOException {
        Stage stage = (Stage) exitUserButton.getScene().getWindow();
        stage.close();
    }

    private void updateUser() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateUserInfoWindow.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update User");
            stage.setResizable(false);
            InputStream iconStream = WindowController.class.getResourceAsStream("/FM_icon.png");
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event -> refreshUserTable());
            stage.show();

        } catch (IOException e) {
            System.err.println("Ошибка загрузки fxml-файла: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void updateDrug() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateDrugInfoWindow.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update User");
            stage.setResizable(false);
            InputStream iconStream = WindowController.class.getResourceAsStream("/FM_icon.png");
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event -> refreshDrugTable());
            stage.show();
        } catch (IOException e) {
            System.err.println("Ошибка загрузки fxml-файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showAddUserForm() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userAddForm.fxml"));
        try {
            Parent root = loader.load();
            AddUserFormController controller = loader.getController();
            controller.setUserList(userList);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.setResizable(false);
            InputStream iconStream = WindowController.class.getResourceAsStream("/FM_icon.png");
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event -> refreshUserTable());
            stage.show();

        } catch (IOException e) {
            System.err.println("Ошибка загрузки fxml-файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showAddDrugForm() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/drugAddForm.fxml"));
        try {
            Parent root = loader.load();
            AddDrugFormController controller = loader.getController();
            controller.setDrugList(drugList);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.setResizable(false);
            InputStream iconStream = WindowController.class.getResourceAsStream("/FM_icon.png");
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event -> refreshDrugTable());
            stage.show();

        } catch (IOException e) {
            System.err.println("Ошибка загрузки fxml-файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUser(Integer id) throws SQLException {
        userDAO.deleteUser(id);
    }
    public void deleteDrug(Integer id) throws SQLException {
        drugDAO.deleteDrug(id);
    }
    public void refreshUserTable() {
        try {
            userList.clear();
            userList.addAll(userDAO.getAllUsers());
            usersTable.setItems(userList);
            usersTable.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void refreshDrugTable() {
        drugList.clear();
        drugList.addAll(drugDAO.getAllDrugs());
        drugsTable.setItems(drugList);
        drugsTable.refresh();
    }
}