package com.example.magazinonlineapp.controller;

import com.example.magazinonlineapp.HelloApplication;
import com.example.magazinonlineapp.domain.Angajat;
import com.example.magazinonlineapp.domain.Basket;
import com.example.magazinonlineapp.domain.Client;
import com.example.magazinonlineapp.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Text signUpClickable;
    @FXML
    public Text textResponse;

    Service service;

    public void setService(Service service){
        this.service = service;
    }
    public void handleLogin(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String parola = passwordField.getText();

        int result = checkFields(username, parola);

//        if(result == 1)
//            textResponse.setText("Login succesful! Angajat");
//        else if(result == 2)
//            textResponse.setText("Login succesful! Client");
//            else
//                textResponse.setText("Login failed!");

        FXMLLoader fxmlLoader = null;
        if (result != -1) {
            switch (result) {
                case 1:
                    fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/Orders_Angajat.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Angajat Page : "+username);
                    OrdersAngajatController ordersAngajatController = fxmlLoader.getController();
                    Angajat loggedAngajat = service.findAngajat(username);
                    System.out.println("NUMEEE" + loggedAngajat.getNume());
                    ordersAngajatController.setLoggedAngajat(loggedAngajat);
                    ordersAngajatController.setService(service);
                    stage.show();
                    textResponse.setText("Login succesful! Angajat");
                    //close();
                    break;
                case 2:
                    fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/ProduseView_Client.fxml"));
                    root = fxmlLoader.load();
                    stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Client Page");
                    ProduseClientController produseClientController = fxmlLoader.getController();
                    Client loggedClient = service.findClient(username);
                    System.out.println("NUMEEE" + loggedClient.getNume());
                    produseClientController.setLoggedClient(loggedClient);
                    produseClientController.setBasket(new Basket());
                    produseClientController.setService(service);
                    stage.show();
                    textResponse.setText("Login succesful! Client");
//                    close();
                    break;
                default:
                    fxmlLoader  = null;
            };
        }
        else
            textResponse.setText("Login failed!");

        usernameField.clear();
        passwordField.clear();
    }

    private int checkFields(String username, String password) {
        boolean isAngajat = service.loginAngajat(username, password);
        boolean isClient = service.loginClient(username, password);
        if (!isAngajat && !isClient) //login failed
            return -1;

        if (isAngajat)
            return 1;
        else return 2;
    }

    private void close() {
        Stage thisStage = (Stage) usernameField.getScene().getWindow();
        thisStage.close();
    }

    public void handleSignup(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/Signup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Signup Page");
        SignupController signupController = fxmlLoader.getController();
        signupController.setService(service);
        stage.show();
        //((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        close();
    }
}
