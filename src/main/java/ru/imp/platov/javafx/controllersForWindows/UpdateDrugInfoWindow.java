package ru.imp.platov.javafx.controllersForWindows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import ru.imp.platov.Drugs.DrugDAO;
import ru.imp.platov.Drugs.DrugItem;
import ru.imp.platov.database.DatabaseManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateDrugInfoWindow {
    DrugDAO drugDAO = new DrugDAO();
    DrugItem drugItem;
    @FXML
    private TextField drug_nameTextField;
    @FXML
    private TextField drug_descriptionTextField;
    @FXML
    private TextField drug_quantityTextField;
    @FXML
    private TextField drug_priceTextField;
    @FXML
    private TextField drug_manufacturerTextField;
    @FXML
    private DatePicker drug_prod_dateTextField;
    @FXML
    private DatePicker drug_expr_dateTextField;
    @FXML
    private ComboBox<Boolean> recipeComboBox;
    @FXML
    private ComboBox<Integer> idComboBox;
    @FXML
    private Button doneButton;
    @FXML
    private Button cancelButton;

    @FXML
    void initialize() throws SQLException {
        recipeComboBox.getItems().setAll(true, false);
        idComboBox.getItems().setAll(allId());
        idComboBox.setOnAction(event -> {
            Integer drugId = idComboBox.getValue();
            try {
                this.drugItem = drugDAO.getDrugById(drugId);
                drug_nameTextField.setText(drugItem.getDrug_name());
                drug_descriptionTextField.setText(drugItem.getDrug_description());
                drug_quantityTextField.setText(drugItem.getDrug_quantity().toString());
                drug_priceTextField.setText(drugItem.getDrug_price().toString());
                drug_manufacturerTextField.setText(drugItem.getDrug_manufacturer());
                drug_prod_dateTextField.setValue(drugItem.getDrug_prod_date());
                drug_expr_dateTextField.setValue(drugItem.getDrug_expr_date());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        idComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Integer drugId = Integer.parseInt(newVal.toString());
                Boolean drugRecipe;
                try {
                    drugRecipe = Boolean.parseBoolean(getRecipeForDrug(drugId));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                recipeComboBox.setValue(drugRecipe);
            }
        });
    }

    private String getRecipeForDrug(Integer drugId) throws SQLException {
        DrugItem drugItem;
        drugItem = drugDAO.getDrugById(drugId);
        if (drugItem.getDrug_recipe()) return "true";
        return "false";
    }

    public List<Integer> allId() {
        String sql = "SELECT drug_id FROM drug_table";
        List<Integer> allDrugsId;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            allDrugsId = new ArrayList<>();
            while (rs.next()) {
                Integer tempDrugId = rs.getInt("drug_id");
                allDrugsId.add(tempDrugId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allDrugsId;
    }

    public void handleDoneButton(ActionEvent event) throws SQLException {
        try {
            BigDecimal value = new BigDecimal(drug_priceTextField.getText());

            Integer selected_Id = idComboBox.getValue();
            DrugItem newDrugItem = new DrugItem(selected_Id,
                    drug_nameTextField.getText(),
                    drug_descriptionTextField.getText(),
                    Integer.parseInt(drug_quantityTextField.getText()),
                    value,
                    drug_manufacturerTextField.getText(),
                    drug_prod_dateTextField.getValue(),
                    drug_expr_dateTextField.getValue(),
                    recipeComboBox.getSelectionModel().getSelectedItem());
            drugDAO.updateDrug(newDrugItem);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Drug updated successfully");
            successAlert.showAndWait();
        } catch (NumberFormatException ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid drug id");
            errorAlert.showAndWait();
        }
        catch (SQLException ex) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error updating drug: " + ex.getMessage());
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