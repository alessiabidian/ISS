package com.example.magazinonlineapp.controller;

import com.example.magazinonlineapp.HelloApplication;
import com.example.magazinonlineapp.domain.*;
import com.example.magazinonlineapp.observer.Observer;
import com.example.magazinonlineapp.service.Service;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class StockAngajatController implements Observer {
    @FXML
    public TextField stockField;
    @FXML
    public Button modifyStockButton;
    @FXML
    public Button logoutButton;
    @FXML
    public TextField searchField;
    @FXML
    public Button searchButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button backButton;
    @FXML
    public TableView<Produs> productTable;
    @FXML
    public TableColumn<Produs, String> nameColumn;
    @FXML
    public TableColumn<Produs, Integer> priceColumn;
    @FXML
    public TableColumn<Produs, Integer> stockColumn;
    Service service;
    Angajat loggedAngajat;
    private ObservableList<Produs> modelProdus = FXCollections.observableArrayList();

    public void setLoggedAngajat(Angajat loggedAngajat) {
        this.loggedAngajat = loggedAngajat;
    }

    public void setService(Service service) {
        this.service = service;
        initModel();
        this.service.addObserver(this);
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stoc"));
        productTable.setItems(modelProdus);
    }

    private void initModel() {
        modelProdus.setAll();
        modelProdus.addAll(service.getAllProduse());
    }
    public void handleModifyStock(ActionEvent actionEvent) {
        int newStock = Integer.parseInt(stockField.getText());
        Produs p = productTable.getSelectionModel().getSelectedItem();

        p.setStoc(newStock);

        service.updateProdus(p);
//        initModel();
    }

    public void handleLogout(ActionEvent actionEvent) throws IOException {
        URL fxmlLocation = HelloApplication.class.getResource("views/Login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Login Page");

        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);

        stage.show();
        close();
    }

    public void handleSearch(ActionEvent actionEvent) {
        String denumire = searchField.getText();

        List<Produs> filtered = service.filterProduse(denumire, 90000);

        modelProdus.setAll(filtered);
    }

    public void handleDelete(ActionEvent actionEvent) {
        Produs p = productTable.getSelectionModel().getSelectedItem();

        try {
            service.deleteProdus(p);
        }
        catch (Exception ex){
//            System.out.println(ex.getMessage());
            MessageAlert.showErrorMessage(null, ex.getMessage());
        }

//        initModel();
    }

    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/Orders_Angajat.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Angajat Page : "+loggedAngajat.getUsername());
        OrdersAngajatController ordersAngajatController = fxmlLoader.getController();
        ordersAngajatController.setLoggedAngajat(loggedAngajat);
//        service.addObserver(ordersAngajatController); //!!!!!
        ordersAngajatController.setService(service);
        stage.show();
        close();
    }

    private void close() {
        Stage thisStage = (Stage) backButton.getScene().getWindow();
        thisStage.close();
    }

    @Override
    public void update() {
        initModel();
    }
}
