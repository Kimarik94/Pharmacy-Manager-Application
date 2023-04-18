package ru.imp.platov.javafx;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WindowController {

    private Stage primaryStage;
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    private static final String ADMIN_FXML_PATH = "/adminWindowStyle.fxml";
    private static final String PHARMACIST_FXML_PATH = "/pharmacistWindowStyle.fxml";

    public static void openAdminWindow() throws SQLException, IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(WindowController.class.getResource(ADMIN_FXML_PATH));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin Window");
        InputStream iconStream = WindowController.class.getResourceAsStream("/FM_icon.png");
        Image icon = new Image(iconStream);
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();
    }

    public static void openPharmacistWindow() throws SQLException, IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(WindowController.class.getResource(PHARMACIST_FXML_PATH));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pharmacist Window");
        InputStream iconStream = WindowController.class.getResourceAsStream("/FM_icon.png");
        Image icon = new Image(iconStream);
        stage.getIcons().add(icon);
        stage.show();
    }
}