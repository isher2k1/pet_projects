package com.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Date;
import java.io.IOException;
import javafx.scene.layout.VBox;
import java.sql.*;


public class HelloApplication extends Application {
    private static final String URL = "jdbc:postgresql://localhost:5432/services";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1402VLArsenium";
    private static final String ADMIN = "admin";
    private static final String ADMIN_PASSWORD = "0000";

    private ComboBox<String> userComboBox;
    private ComboBox<String> serviceComboBox;
    private ListView<Payment> servicesListView;
    private TextField amountField, userField, emailField;
    private Scene loginScene;
    private Scene registrationScene;
    private Scene mainScene, adminScene;
    private TextField loginEmailField;
    private PasswordField loginPasswordField;
    private TextField registrationUsernameField, registrationEmailField, registrationAddressField, registrationPhoneField;
    private PasswordField registrationPasswordField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Система ведення обліку комунальних послуг");

        // Сцена входу
        GridPane loginGridPane = new GridPane();
        loginGridPane.setPadding(new Insets(20));
        loginGridPane.setVgap(10);
        loginGridPane.setHgap(10);

        Label loginEmailLabel = new Label("Пошта користувача:");
        GridPane.setConstraints(loginEmailLabel, 0, 0);
        loginEmailField = new TextField();
        GridPane.setConstraints(loginEmailField, 1, 0);

        Label loginPasswordLabel = new Label("Пароль:");
        GridPane.setConstraints(loginPasswordLabel, 0, 1);
        loginPasswordField = new PasswordField();
        GridPane.setConstraints(loginPasswordField, 1, 1);

        Button loginButton = new Button("Увійти");
        //loginButton.setOnAction(e -> login(primaryStage));
        loginButton.setOnAction(e -> primaryStage.setScene(adminScene));
        GridPane.setConstraints(loginButton, 0, 2);

        Button registerButton = new Button("Зареєструватися");
        registerButton.setOnAction(e -> primaryStage.setScene(registrationScene));
        GridPane.setConstraints(registerButton, 1, 2);

        loginGridPane.getChildren().addAll(
                loginEmailLabel, loginEmailField,
                loginPasswordLabel, loginPasswordField,
                loginButton, registerButton
        );

        loginScene = new Scene(loginGridPane);

        // Сцена реєстрації
        GridPane registrationGridPane = new GridPane();
        registrationGridPane.setPadding(new Insets(20));
        registrationGridPane.setVgap(10);
        registrationGridPane.setHgap(10);

        Label registrationEmailLabel = new Label("Пошта користувача:");
        GridPane.setConstraints(registrationEmailLabel, 0, 0);
        registrationEmailField = new TextField();
        GridPane.setConstraints(registrationEmailField, 1, 0);

        Label registrationPasswordLabel = new Label("Пароль:");
        GridPane.setConstraints(registrationPasswordLabel, 0, 1);
        registrationPasswordField = new PasswordField();
        GridPane.setConstraints(registrationPasswordField, 1, 1);

        Label registrationUsernameLabel = new Label("ПІБ:");
        GridPane.setConstraints(registrationUsernameLabel, 0, 2);
        registrationUsernameField = new TextField();
        GridPane.setConstraints(registrationUsernameField, 1, 2);

        Label registrationAddressLabel = new Label("Адреса:");
        GridPane.setConstraints(registrationAddressLabel, 0, 3);
        registrationAddressField = new TextField();
        GridPane.setConstraints(registrationAddressField, 1, 3);

        Label registrationPhoneLabel = new Label("Телефон:");
        GridPane.setConstraints(registrationPhoneLabel, 0, 4);
        registrationPhoneField = new TextField();
        GridPane.setConstraints(registrationPhoneField, 1, 4);

        Button registerSubmitButton = new Button("Зареєструватися");
        registerSubmitButton.setOnAction(e -> register(primaryStage));
        GridPane.setConstraints(registerSubmitButton, 0, 5);

        Button registerBackButton = new Button("Назад");
        registerBackButton.setOnAction(e -> primaryStage.setScene(loginScene));
        GridPane.setConstraints(registerBackButton, 1, 5);

        registrationGridPane.getChildren().addAll(
                registrationEmailLabel, registrationEmailField,
                registrationPasswordLabel, registrationPasswordField,
                registrationUsernameLabel, registrationUsernameField,
                registrationAddressLabel, registrationAddressField,
                registrationPhoneLabel, registrationPhoneField,
                registerSubmitButton, registerBackButton
        );

        registrationScene = new Scene(registrationGridPane);

        //Admin scene
        GridPane adminGridPane = new GridPane();
        adminGridPane.setPadding(new Insets(20));
        adminGridPane.setVgap(10);
        adminGridPane.setHgap(10);

        Label userLabel = new Label("Користувач:");
        GridPane.setConstraints(userLabel, 0, 0);
        userComboBox = new ComboBox<>();
        GridPane.setConstraints(userComboBox, 1, 0);

        Button viewServicesButton = new Button("Переглянути послуги");
        viewServicesButton.setOnAction(e -> showUserServices());
        GridPane.setConstraints(viewServicesButton, 0, 1);

        adminGridPane.getChildren().addAll(
                userLabel, userComboBox,
                viewServicesButton
        );

        loadUsers();
        adminScene = new Scene(adminGridPane);


        //Payments scene
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label usersLabel = new Label("Користувач:");
        GridPane.setConstraints(usersLabel, 0, 0);
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
        GridPane.setConstraints(saveButton, 0, 3);

        gridPane.getChildren().addAll(
                usersLabel, userComboBox,
                serviceLabel, serviceComboBox,
                amountLabel, amountField,
                saveButton
        );

        //Scene scene = new Scene(gridPane, 430, 250);
        mainScene = new Scene(gridPane, 430, 250);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        loadUsers();
        loadServices();
    }

    private void showUserServices() {
        //String selectedUser = userComboBox.getValue();
        //String selectedUser = "Ivan Tester";
        String selectedUser = "Ilya Sheremet";
        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Помилка", "Будь ласка, виберіть користувача.");
            return;
        }

        Stage servicesStage = new Stage();
        servicesStage.setTitle("Послуги користувача");

        VBox servicesVBox = new VBox();
        servicesVBox.setPadding(new Insets(20));
        servicesVBox.setSpacing(10);

        Label userLabel = new Label("Користувач: " + selectedUser);
        servicesVBox.getChildren().add(userLabel);

        servicesListView = new ListView<>();
        servicesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Payment payment, boolean empty) {
                super.updateItem(payment, empty);

                if (empty || payment == null) {
                    setText(null);
                } else {
                    setText(payment.getServiceName() + " - " + payment.getAmount() + " грн, " + payment.getDate());
                }
            }
        });
        servicesVBox.getChildren().add(servicesListView);

        loadUserServices(selectedUser);

        Scene servicesScene = new Scene(servicesVBox);
        servicesStage.setScene(servicesScene);
        servicesStage.show();
    }

    private void loadUserServices(String username) {
        servicesListView.getItems().clear();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String getUserServicesSql = "SELECT \"Services\".name, \"Payments\".sum, \"Payments\".date FROM \"Services\"" +
                    "INNER JOIN \"Payments\" ON \"Services\".id = \"Payments\".service_id " +
                    "INNER JOIN \"Users\" ON \"Users\".id = \"Payments\".user_id " +
                    "WHERE \"Users\".user_name = ?";
            PreparedStatement getUserServicesStatement = connection.prepareStatement(getUserServicesSql);
            getUserServicesStatement.setString(1, username);
            ResultSet resultSet = getUserServicesStatement.executeQuery();

            while (resultSet.next()) {
                String serviceName = resultSet.getString("name");
                double amount = resultSet.getDouble("sum");
                Date date = resultSet.getDate("date");

                Payment payment = new Payment(serviceName, amount, date);
                servicesListView.getItems().add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Помилка", "Сталася помилка при завантаженні послуг користувача.");
        }
    }

    private void login(Stage primaryStage) {
        String email = loginEmailField.getText();
        String password = loginPasswordField.getText();

        // Перевірка користувача в базі даних
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM \"Users\" WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Успішний вхід, перейти до основної сцени
                loadUsers();
                loadServices();
                primaryStage.setScene(mainScene);
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Помилка", "Неправильне ім'я користувача або пароль.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Помилка", "Сталася помилка при вході.");
        }
    }

    private void register(Stage primaryStage) {
        String username = registrationUsernameField.getText();
        String address = registrationAddressField.getText();
        String phone = registrationPhoneField.getText();
        String email = registrationEmailField.getText();
        String password = registrationPasswordField.getText();

        // Перевірка наявності користувача в базі даних
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String checkUserSql = "SELECT * FROM \"Users\" WHERE email = ?";
            PreparedStatement checkUserStatement = connection.prepareStatement(checkUserSql);
            checkUserStatement.setString(1, email);
            ResultSet resultSet = checkUserStatement.executeQuery();

            if (resultSet.next()) {
                showAlert(Alert.AlertType.ERROR, "Помилка", "Користувач вже існує.");
            } else {
                // Додавання нового користувача в базу даних
                String insertUserSql = "INSERT INTO \"Users\" (user_name, adress, phone, email, password) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertUserStatement = connection.prepareStatement(insertUserSql);
                insertUserStatement.setString(1, username);
                insertUserStatement.setString(2, address);
                insertUserStatement.setString(3, phone);
                insertUserStatement.setString(4, email);
                insertUserStatement.setString(5, password);
                insertUserStatement.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Успіх", "Реєстрація пройшла успішно!");
                primaryStage.setScene(loginScene);
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Помилка", "Сталася помилка при реєстрації користувача.");
        }
    }

    private void loadUsers() {
        userComboBox.getItems().clear();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT user_name FROM \"Users\"";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                userComboBox.getItems().add(resultSet.getString("user_name"));
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
            String getUserIdSql = "SELECT id FROM \"Users\" WHERE user_name = ?";
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
        loginEmailField.clear();
        loginPasswordField.clear();
        registrationUsernameField.clear();
        registrationPasswordField.clear();
        registrationAddressField.clear();
        registrationPhoneField.clear();
        registrationEmailField.clear();
        amountField.clear();
        userComboBox.getSelectionModel().clearSelection();
        serviceComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}