package com.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Date;
import java.io.IOException;
import java.sql.*;


public class HelloApplication extends Application {
    private static final String URL = "jdbc:postgresql://localhost:5432/services";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1402VLArsenium";

    private ComboBox<String> userComboBox;
    private ComboBox<String> serviceComboBox;
    private TextField amountField, userField, emailField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
        primaryStage.setTitle("Система ведення обліку комунальних послуг");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label userLabel = new Label("Користувач:");
        GridPane.setConstraints(userLabel, 0, 0);
        userComboBox = new ComboBox<>();
        GridPane.setConstraints(userComboBox, 1, 0);

        Label serviceLabel = new Label("Послуга:");
        GridPane.setConstraints(serviceLabel, 0, 1);
        serviceComboBox = new ComboBox<>();
        GridPane.setConstraints(serviceComboBox, 1, 1);

        Label amountLabel = new Label("Сума:");
        GridPane.setConstraints(amountLabel, 0, 2);
        amountField = new TextField();
        GridPane.setConstraints(amountField, 1, 2);

        Button saveButton = new Button("Зберегти");
        saveButton.setOnAction(e -> savePayment());
        GridPane.setConstraints(saveButton, 0, 4);

        gridPane.getChildren().addAll(
                userLabel, userComboBox,
                serviceLabel, serviceComboBox,
                amountLabel, amountField,
                saveButton
        );

        Scene scene = new Scene(gridPane, 430, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadUsers();
        loadServices();
    }

    private void loginScene(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label nameLabel = new Label("First name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        userField = new TextField();
        GridPane.setConstraints(userField, 1, 0);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 1, 0);
        emailField = new TextField();
        GridPane.setConstraints(emailField, 1, 1);

        Button loginButton = new Button("Login");
        //loginButton.setOnAction(e -> login());
        GridPane.setConstraints(loginButton, 0, 4);
    }

    private void loadUsers() {
        userComboBox.getItems().clear();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT first_name FROM \"Users\"";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                userComboBox.getItems().add(resultSet.getString("first_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadServices() {
        serviceComboBox.getItems().clear();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT name FROM \"Services\"";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                serviceComboBox.getItems().add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePayment() {
        String selectedUser = userComboBox.getValue();
        String selectedService = serviceComboBox.getValue();
        double amount = Double.parseDouble(amountField.getText());

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Отримуємо ID користувача за його ім'ям
            String getUserIdSql = "SELECT id FROM \"Users\" WHERE first_name = ?";
            PreparedStatement getUserIdStatement = connection.prepareStatement(getUserIdSql);
            getUserIdStatement.setString(1, selectedUser);
            ResultSet userResultSet = getUserIdStatement.executeQuery();
            if (!userResultSet.next()) {
                showAlert(Alert.AlertType.ERROR, "Помилка", "Користувача не знайдено.");
                return;
            }
            int userId = userResultSet.getInt("id");

            // Отримуємо ID послуги за її назвою
            String getServiceIdSql = "SELECT id FROM \"Services\" WHERE name = ?";
            PreparedStatement getServiceIdStatement = connection.prepareStatement(getServiceIdSql);
            getServiceIdStatement.setString(1, selectedService);
            ResultSet serviceResultSet = getServiceIdStatement.executeQuery();
            if (!serviceResultSet.next()) {
                showAlert(Alert.AlertType.ERROR, "Помилка", "Послугу не знайдено.");
                return;
            }
            int serviceId = serviceResultSet.getInt("id");

            // Вставляємо запис про платіж
            String insertPaymentSql = "INSERT INTO \"Payments\" (user_id, service_id, sum, date) VALUES (?, ?, ?, ?)";
            PreparedStatement insertPaymentStatement = connection.prepareStatement(insertPaymentSql);
            insertPaymentStatement.setInt(1, userId);
            insertPaymentStatement.setInt(2, serviceId);
            insertPaymentStatement.setDouble(3, amount);
            insertPaymentStatement.setDate(4, sqlDate);
            insertPaymentStatement.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Успіх", "Платіж збережено успішно.");
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Помилка", "Сталася помилка при збереженні платежу.");
        }
    }

    private void clearFields() {
        userComboBox.getSelectionModel().clearSelection();
        serviceComboBox.getSelectionModel().clearSelection();
        amountField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}