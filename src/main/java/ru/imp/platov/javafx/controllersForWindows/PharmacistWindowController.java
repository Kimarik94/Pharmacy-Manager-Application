package ru.imp.platov.javafx.controllersForWindows;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import ru.imp.platov.Drugs.DrugDAO;
import ru.imp.platov.Drugs.DrugItem;

public class PharmacistWindowController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField manufacturerField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField quantityField;

    @FXML
    private TableView<DrugItem> tableView;

    @FXML
    private TableColumn<DrugItem, String> nameColumn;

    @FXML
    private TableColumn<DrugItem, String> manufacturerColumn;

    @FXML
    private TableColumn<DrugItem, String> descriptionColumn;

    @FXML
    private TableColumn<DrugItem, Double> priceColumn;

    @FXML
    private TableColumn<DrugItem, Integer> quantityColumn;

    @FXML
    private TableColumn<DrugItem, Boolean> recipeColumn;

    @FXML
    private Button sellButton;

    @FXML
    private Button searchButton;

    @FXML
    private CheckBox RecipeCheck;

    @FXML
    private Button exitButton;

    private DrugDAO drugDAO;

    public void initialize() {
        drugDAO = new DrugDAO();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("drug_name"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("drug_manufacturer"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("drug_description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("drug_price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("drug_quantity"));
        recipeColumn.setCellValueFactory(new PropertyValueFactory<>("drug_recipe"));

        exitButton.setOnAction(event -> exit());
        searchButton.setOnAction(event -> search());
        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        manufacturerField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        descriptionField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        sellButton.setOnAction(event -> {
            try {
                sell();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Selling Proceed");
                alert.setHeaderText("Success!");
                alert.setContentText("Medicene Sold Successfully!");
                alert.show();
                tableView.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(PharmacistWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !quantityField.getText().trim().isEmpty() && Integer.parseInt(quantityField.getText().trim()) > 0 && RecipeCheck.isSelected()) {
                sellButton.setDisable(false);
            } else {
                sellButton.setDisable(true);
            }
        });

        quantityField.textProperty().addListener((obs, oldText, newText) -> {
            if (tableView.getSelectionModel().getSelectedItem() != null && !newText.trim().isEmpty() && Integer.parseInt(newText.trim()) > 0 && RecipeCheck.isSelected()) {
                sellButton.setDisable(false);
            } else {
                sellButton.setDisable(true);
            }
        });

        RecipeCheck.setOnAction(event -> {
            if (tableView.getSelectionModel().getSelectedItem() != null && !quantityField.getText().trim().isEmpty() && Integer.parseInt(quantityField.getText().trim()) > 0 && RecipeCheck.isSelected()) {
                sellButton.setDisable(false);
            } else {
                sellButton.setDisable(true);
            }
        });
    }

    @FXML
    public void search() {
        String name = nameField.getText().trim().toLowerCase();
        String manufacturer = manufacturerField.getText().trim().toLowerCase();
        String description = descriptionField.getText().trim().toLowerCase();

        List<DrugItem> drugs = drugDAO.searchDrugs(name, manufacturer, description);
        tableView.getItems().setAll(drugs);
    }

    @FXML
    public void sell() throws SQLException {
        DrugItem item = tableView.getSelectionModel().getSelectedItem();
        Integer forSale = Integer.valueOf(quantityField.getText());
        DrugDAO drugDAO = new DrugDAO();
        item.setDrug_quantity(item.getDrug_quantity() - forSale);
        drugDAO.updateDrug(item);
    }

    private boolean isNumeric(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void exit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}
