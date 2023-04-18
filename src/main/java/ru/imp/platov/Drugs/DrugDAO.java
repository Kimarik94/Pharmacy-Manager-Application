package ru.imp.platov.Drugs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.imp.platov.database.DatabaseManager;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DrugDAO {

    private final DataSource dataSource;

    public DrugDAO() {
        this.dataSource = DatabaseManager.getDataSource();
    }

    public void addDrug(DrugItem drug) throws SQLException {
        String query = "INSERT INTO pharmacydb.public.drug_table (drug_name, drug_description, drug_quantity, drug_price, drug_manufacturer, drug_prod_date, drug_expr_date, drug_recipe) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, drug.getDrug_name());
            statement.setString(2, drug.getDrug_description());
            statement.setInt(3, drug.getDrug_quantity());
            statement.setBigDecimal(4, drug.getDrug_price());
            statement.setString(5, drug.getDrug_manufacturer());
            statement.setDate(6, Date.valueOf(drug.getDrug_prod_date()));
            statement.setDate(7, Date.valueOf(drug.getDrug_expr_date()));
            statement.setBoolean(8, drug.getDrug_recipe());
            statement.executeUpdate();
        }
    }

    public void updateDrug(DrugItem updatedDrug) throws SQLException {
        String query = "UPDATE pharmacydb.public.drug_table SET drug_name = ?, "
                + "drug_description = ?, drug_quantity = ?, "
                + "drug_price = ?, drug_manufacturer = ?, drug_prod_date = ?, "
                + "drug_expr_date = ?, drug_recipe = ? WHERE drug_id = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, updatedDrug.getDrug_name());
            statement.setString(2, updatedDrug.getDrug_description());
            statement.setInt(3, updatedDrug.getDrug_quantity());
            statement.setBigDecimal(4, updatedDrug.getDrug_price());
            statement.setString(5, updatedDrug.getDrug_manufacturer());
            statement.setDate(6, Date.valueOf(updatedDrug.getDrug_prod_date()));
            statement.setDate(7, Date.valueOf(updatedDrug.getDrug_expr_date()));
            statement.setBoolean(8, updatedDrug.getDrug_recipe());
            statement.setInt(9, updatedDrug.getDrug_id());
            statement.executeUpdate();
        }
    }

    public void deleteDrug(Integer id) {
        String query = "DELETE FROM pharmacydb.public.drug_table WHERE drug_id=?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<DrugItem> getAllDrugs() {
        ObservableList<DrugItem> drugList = FXCollections.observableArrayList();
        String query = "SELECT * FROM pharmacydb.public.drug_table";
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("drug_id");
                String name = resultSet.getString("drug_name");
                String description = resultSet.getString("drug_description");
                int quantity = resultSet.getInt("drug_quantity");
                BigDecimal price = resultSet.getBigDecimal("drug_price");
                String manufacturer = resultSet.getString("drug_manufacturer");
                LocalDate prodDate = resultSet.getDate("drug_prod_date").toLocalDate();
                LocalDate exprDate = resultSet.getDate("drug_expr_date").toLocalDate();
                boolean recipe = resultSet.getBoolean("drug_recipe");
                DrugItem drug = new DrugItem(id, name, description, quantity, price, manufacturer, prodDate, exprDate, recipe);
                drugList.add(drug);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drugList;
    }

    public DrugItem getDrugById(Integer id) throws SQLException {
        String query = "SELECT * FROM pharmacydb.public.drug_table WHERE drug_id=?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("drug_name");
                String description = resultSet.getString("drug_description");
                int quantity = resultSet.getInt("drug_quantity");
                BigDecimal price = resultSet.getBigDecimal("drug_price");
                String manufacturer = resultSet.getString("drug_manufacturer");
                LocalDate prodDate = resultSet.getDate("drug_prod_date").toLocalDate();
                LocalDate exprDate = resultSet.getDate("drug_expr_date").toLocalDate();
                boolean recipe = resultSet.getBoolean("drug_recipe");
                return new DrugItem(id, name, description, quantity, price, manufacturer, prodDate, exprDate, recipe);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<DrugItem> getDrugsByExpirationDate(Date expirationDate) throws SQLException {
        String query = "SELECT * FROM pharmacydb.public.drug_table WHERE drug_expr_date < ?";
        ObservableList<DrugItem> drugList = FXCollections.observableArrayList();
        try (Connection connection = dataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, expirationDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("drug_id");
                String name = resultSet.getString("drug_name");
                String description = resultSet.getString("drug_description");
                int quantity = resultSet.getInt("drug_quantity");
                BigDecimal price = resultSet.getBigDecimal("drug_price");
                String manufacturer = resultSet.getString("drug_manufacturer");
                LocalDate prodDate = resultSet.getDate("drug_prod_date").toLocalDate();
                LocalDate exprDate = resultSet.getDate("drug_expr_date").toLocalDate();
                boolean recipe = resultSet.getBoolean("drug_recipe");
                drugList.add(new DrugItem(id, name, description, quantity, price, manufacturer, prodDate, exprDate, recipe));
            }
        }
        return drugList;
    }

    public List<DrugItem> searchDrugs(String name, String manufacturer, String description) {
       List<DrugItem> drugs = new ArrayList<>();
       String query = "SELECT * FROM pharmacydb.public.drug_table WHERE drug_name LIKE ? AND drug_manufacturer LIKE ? AND drug_description LIKE ?";
    try (Connection connection = dataSource.getConnection(); 
        PreparedStatement statement = connection.prepareStatement(query))
    {
        statement.setString(1, "%" + name + "%");
        statement.setString(2, "%" + manufacturer + "%");
        statement.setString(3, "%" + description + "%");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("drug_id");
                String drug_name = resultSet.getString("drug_name");
                String drug_description = resultSet.getString("drug_description");
                int quantity = resultSet.getInt("drug_quantity");
                BigDecimal price = resultSet.getBigDecimal("drug_price");
                String drug_manufacturer = resultSet.getString("drug_manufacturer");
                LocalDate prodDate = resultSet.getDate("drug_prod_date").toLocalDate();
                LocalDate exprDate = resultSet.getDate("drug_expr_date").toLocalDate();
                boolean recipe = resultSet.getBoolean("drug_recipe");
                drugs.add(new DrugItem(id, drug_name, drug_description, quantity, price, drug_manufacturer, prodDate, exprDate, recipe));
           
        }
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return drugs;
    }
}
