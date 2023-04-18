package ru.imp.platov.javafx.controllersForWindows;

import com.sun.javafx.scene.control.IntegerField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.imp.platov.Drugs.DrugDAO;
import ru.imp.platov.Drugs.DrugItem;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddDrugFormController {
    private ObservableList<DrugItem> drugList;
    private DrugDAO drugDAO;
    public ObservableList<DrugItem> getDrugList() {
        return drugList;
    }
    public void setDrugList(ObservableList<DrugItem> userList) {
        this.drugList = userList;
    }

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
    private Button finishButton;
    @FXML
    void initialize() throws SQLException {
        recipeComboBox.getItems().setAll(true,false);
    }

    @FXML
    private void handleFinishButton(ActionEvent event) throws SQLException {
        try {
            drugDAO = new DrugDAO();
            DrugItem newDrug = new DrugItem(null,
                    drug_nameTextField.getText(),
                    drug_descriptionTextField.getText(),
                    Integer.parseInt(drug_quantityTextField.getText()),
                    BigDecimal.valueOf(Long.parseLong(drug_priceTextField.getText())),
                    drug_manufacturerTextField.getText(),
                    drug_prod_dateTextField.getValue(),
                    drug_expr_dateTextField.getValue(),
                    recipeComboBox.getValue()
            );
            drugDAO.addDrug(newDrug);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Drug Success!");
            alert.setHeaderText("Success!");
            alert.setContentText("Drug Added Successfully!");
            alert.show();
            Stage stage = (Stage) finishButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении препарата: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

