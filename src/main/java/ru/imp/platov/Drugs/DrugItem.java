package ru.imp.platov.Drugs;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DrugItem {
    private Integer drug_id;
    private String drug_name;
    private String drug_description;
    private Integer drug_quantity;
    private BigDecimal drug_price;
    private String drug_manufacturer;
    private LocalDate drug_prod_date;
    private LocalDate drug_expr_date;
    private boolean drug_recipe;

    public DrugItem(Integer drug_id, String drug_name, String drug_description, Integer drug_quantity, BigDecimal drug_price, String drug_manufacturer, LocalDate drug_prod_date, LocalDate drug_expr_date, Boolean drug_recipe) {
        this.drug_id = drug_id;
        this.drug_name = drug_name;
        this.drug_description = drug_description;
        this.drug_quantity = drug_quantity;
        this.drug_price = drug_price;
        this.drug_manufacturer = drug_manufacturer;
        this.drug_prod_date = drug_prod_date;
        this.drug_expr_date = drug_expr_date;
        this.drug_recipe = drug_recipe;
    }

    public Integer getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(Integer drug_id) {
        this.drug_id = drug_id;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getDrug_description() {
        return drug_description;
    }

    public void setDrug_description(String drug_description) {
        this.drug_description = drug_description;
    }

    public Integer getDrug_quantity() {
        return drug_quantity;
    }

    public void setDrug_quantity(Integer drug_quantity) {
        this.drug_quantity = drug_quantity;
    }

    public BigDecimal getDrug_price() {
        return drug_price;
    }

    public void setDrug_price(BigDecimal drug_price) {
        this.drug_price = drug_price;
    }

    public String getDrug_manufacturer() {
        return drug_manufacturer;
    }

    public void setDrug_manufacturer(String drug_manufacturer) {
        this.drug_manufacturer = drug_manufacturer;
    }

    public LocalDate getDrug_prod_date() {
        return drug_prod_date;
    }

    public void setDrug_prod_date(LocalDate drug_prod_date) {
        this.drug_prod_date = drug_prod_date;
    }

    public LocalDate getDrug_expr_date() {
        return drug_expr_date;
    }

    public void setDrug_expr_date(LocalDate drug_expr_date) {
        this.drug_expr_date = drug_expr_date;
    }

    public boolean getDrug_recipe() {
        return drug_recipe;
    }

    public void setDrug_recipe(boolean drug_recipe) {
        this.drug_recipe = drug_recipe;
    }
}
