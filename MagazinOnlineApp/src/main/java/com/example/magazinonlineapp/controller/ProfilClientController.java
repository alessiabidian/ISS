package com.example.magazinonlineapp.controller;

import com.example.magazinonlineapp.HelloApplication;
import com.example.magazinonlineapp.domain.*;
import com.example.magazinonlineapp.observer.Observable;
import com.example.magazinonlineapp.observer.Observer;
import com.example.magazinonlineapp.service.Service;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfilClientController implements Observer {
    @FXML
    public Button logoutButton;
    @FXML
    public Button makeOrderButton;
    @FXML
    public Text numeDisplay;
    @FXML
    public TableView<Produs> basketTable;
    @FXML
    public TableColumn<Produs, String> productNameField;
    @FXML
    public TableColumn<Produs, Integer> priceField;
    @FXML
    public TableColumn<Produs, Integer> quantityField;
    @FXML
    public Button backButton;
    @FXML
    public Button cancelOrderButton;
    @FXML
    public TableView<Comanda> ordersTable;
    @FXML
    public TableColumn<Comanda, String> detailsColumn;
    @FXML
    public TableColumn<Comanda, Integer> priceColumn;
    @FXML
    public TableColumn<Comanda, LocalDateTime> dateColumn;
    @FXML
    public TableColumn<Comanda, Stadiu> statusColumn;
    Client loggedClient;
    Service service;
    Basket basket;

    private ObservableList<Produs> modelProdus = FXCollections.observableArrayList();
    private ObservableList<Comanda> modelComanda = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        initModel();
        this.service.addObserver(this);
    }

    public void handleMakeOrder(ActionEvent actionEvent) {
        service.addComanda(basket.getProduse(), loggedClient.getEmail());

        basket.clearBasket();

        modelProdus.setAll();
//        initModel();
    }

    public void handleDeleteFromBasket(ActionEvent actionEvent) {
        List<Produs> produseSelectate = basketTable.getSelectionModel().getSelectedItems();

        for(Produs p: produseSelectate)
            basket.getProduse().remove(p);

        modelProdus.setAll();
        List<Produs> set = basket.getProduse().stream().distinct().toList();
        modelProdus.addAll(set);
    }

    public void setLoggedClient(Client client) {
        this.loggedClient = client;
        numeDisplay.setText(loggedClient.getPrenume() + " " + loggedClient.getNume());
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @FXML
    public void initialize() {
        productNameField.setCellValueFactory(new PropertyValueFactory<>("denumire"));
        priceField.setCellValueFactory(new PropertyValueFactory<>("pret"));
        basketTable.setItems(modelProdus);

        quantityField.setCellValueFactory(c -> {
            Produs produs = c.getValue();

            int count = 0;
            for (Produs p : basket.getProduse()) {
                if (Objects.equals(produs, p))
                    count++;
            }
            return new ReadOnlyObjectWrapper<Integer>(count);
        });

        basketTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        // ------------------------------------------------
        detailsColumn.setCellValueFactory(c -> {
            Comanda comanda = c.getValue();

            List<ProdusComanda> produsComandaList = service.getDetaliiComanda(comanda.getId());

            String str = "";
            for (ProdusComanda pc : produsComandaList) {
                Produs produs = service.findProdus(pc.getIdProdus());
                str += produs.getDenumire()+ ", ";
            }
            if(str.length() > 2)
                str = str.substring(0, str.length() - 2);

            return new ReadOnlyObjectWrapper<String>(str);
        });

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("stadiuCurent"));

        priceColumn.setCellValueFactory(c -> {
            Comanda comanda = c.getValue();

            List<ProdusComanda> produsComandaList = service.getDetaliiComanda(comanda.getId());

            int suma = 0;
            for (ProdusComanda pc : produsComandaList) {
                Produs produs = service.findProdus(pc.getIdProdus());
                suma += produs.getPret() * pc.getCantitate();
            }
            return new ReadOnlyObjectWrapper<Integer>(suma);
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        ordersTable.setItems(modelComanda);

//        numeDisplay.setText(loggedClient.getPrenume() + " " + loggedClient.getNume());
    }

    private void initModel() {
        modelProdus.setAll();
        List<Produs> set = basket.getProduse().stream().distinct().toList();
        modelProdus.addAll(set);

        modelComanda.setAll();
        List<Comanda> comenziClient = service.getComenziClient(loggedClient.getEmail());
        modelComanda.addAll(comenziClient);
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

    private void close() {
        Stage thisStage = (Stage) logoutButton.getScene().getWindow();
        thisStage.close();
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/ProduseView_Client.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Client Page");
        ProduseClientController produseClientController = fxmlLoader.getController();
        produseClientController.setLoggedClient(loggedClient);
        produseClientController.setBasket(basket);
        produseClientController.setService(service);
        stage.show();
        close();
    }

    public void handleCancelOrder(ActionEvent actionEvent) {
        Comanda comanda = ordersTable.getSelectionModel().getSelectedItem();

        service.deleteComanda(comanda);

//        initModel();
    }

    @Override
    public void update() {
        initModel();
    }
}
