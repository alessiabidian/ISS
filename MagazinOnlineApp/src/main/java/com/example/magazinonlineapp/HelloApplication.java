package com.example.magazinonlineapp;

import com.example.magazinonlineapp.controller.LoginController;
import com.example.magazinonlineapp.repository.db.*;
import com.example.magazinonlineapp.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

    AngajatDbRepository angajatRepo;
    ClientDbRepository clientRepo;
    ProdusDbRepository produsRepo;
    ComandaDbRepository comandaRepo;
    ProdusComandaDbRepository pcRepo;
    Service service;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Muie");

        angajatRepo = new AngajatDbRepository();
        clientRepo = new ClientDbRepository();
        produsRepo = new ProdusDbRepository();
        comandaRepo = new ComandaDbRepository();
        pcRepo = new ProdusComandaDbRepository();

        service = new Service(angajatRepo, clientRepo, produsRepo, comandaRepo, pcRepo);
        loginView(stage);
        stage.show();
    }

    private void loginView(Stage stage) throws IOException {

        URL fxmlLocation = HelloApplication.class.getResource("views/Login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        GridPane Layout = fxmlLoader.load();
        stage.setScene(new Scene(Layout));
        stage.setTitle("Login Page");

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
    }

    public static void main(String[] args) {
        launch();
    }
}