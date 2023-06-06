package com.example.magazinonlineapp.controller;

import com.example.magazinonlineapp.HelloApplication;
import com.example.magazinonlineapp.domain.Basket;
import com.example.magazinonlineapp.domain.Client;
import com.example.magazinonlineapp.domain.Entity;
import com.example.magazinonlineapp.domain.Produs;
import com.example.magazinonlineapp.observer.Observer;
import com.example.magazinonlineapp.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProduseClientController {
    @FXML
    public TextField searchBarName;
    @FXML
    public TextField searchBarPrice;
    @FXML
    public Button searchButton;
    @FXML
    public Button addToBasketButton;
    @FXML
    public Button profileButton;
    @FXML
    public Text refreshTag;
    @FXML
    public TableView<Produs> tableProduse;
    @FXML
    public TableColumn<Produs, String> columnName;
    @FXML
    public TableColumn<Produs, Integer> columnPrice;
    Service service;
    Client loggedClient;
    Basket basket;
    private ObservableList<Produs> modelProdus = FXCollections.observableArrayList();

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public void setService(Service service){
        this.service = service;
        initModel();
    }

    public void setLoggedClient(Client loggedClient){
        this.loggedClient = loggedClient;
        //this.basket = new Basket();
    }

    @FXML
    public void initialize() {
        columnName.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("pret"));
        tableProduse.setItems(modelProdus);
    }

    public void handleSearch(ActionEvent actionEvent) {
        String denumire = searchBarName.getText();
        int pret = Integer.parseInt(searchBarPrice.getText());

        List<Produs> filtered = service.filterProduse(denumire, pret);

        modelProdus.setAll();
        modelProdus.addAll(filtered);
    }

    private void initModel() {
        modelProdus.setAll();
        modelProdus.addAll(service.getAllProduse());
    }

    public void handleRefresh(MouseEvent mouseEvent) {
        modelProdus.setAll();
        modelProdus.addAll(service.getAllProduse());
    }

    public void handleAddToBasket(ActionEvent actionEvent) {
        List<Produs> produseSelectate = tableProduse.getSelectionModel().getSelectedItems();

        for(Produs produs : produseSelectate)
            this.basket.addProdus(produs);

    }

    public void handleOpenProfile(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/Basket_Client.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Profile Page");
        ProfilClientController profilClientController = fxmlLoader.getController();
        profilClientController.setLoggedClient(loggedClient);
        profilClientController.setBasket(basket);
//        service.addObserver(profilClientController); //!!!!!
        profilClientController.setService(service);
        stage.show();
        close();
    }

    private void close() {
        Stage thisStage = (Stage) profileButton.getScene().getWindow();
        thisStage.close();
    }
}
