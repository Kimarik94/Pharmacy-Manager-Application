package ru.imp.platov;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.imp.platov.javafx.LoginWindow;

import java.io.IOException;
import java.sql.SQLException;

public class PharmacyApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        LoginWindow loginWindow = new LoginWindow(primaryStage);

        Scene scene = new Scene(loginWindow.getRoot());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}