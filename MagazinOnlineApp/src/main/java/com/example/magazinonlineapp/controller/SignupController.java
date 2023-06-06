package com.example.magazinonlineapp.controller;

import com.example.magazinonlineapp.HelloApplication;
import com.example.magazinonlineapp.domain.validators.ValidationException;
import com.example.magazinonlineapp.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button signupButton;
    @FXML
    public TextField numeField;
    @FXML
    public TextField prenumeField;

    Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public void handleSignup(ActionEvent actionEvent) throws IOException {
        String nume = numeField.getText();
        String prenume = prenumeField.getText();
        String email = emailField.getText();
        String parola = passwordField.getText();

        try {
            service.addClient(nume, prenume, email, parola);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/Login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login Page");
            LoginController loginController = fxmlLoader.getController();
            loginController.setService(service);
            stage.show();

            close();
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }

    }

    private void close() {
        Stage thisStage = (Stage) prenumeField.getScene().getWindow();
        thisStage.close();
    }
}
