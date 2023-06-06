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
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.criterion.Order;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrdersAngajatController implements Observer {
    @FXML
    public TableView<Comanda> ordersTable;
    @FXML
    public TableColumn<Comanda, Integer> orderIdColumn;
    @FXML
    public TableColumn<Comanda, String> clientIdColumn;
    @FXML
    public TableColumn<Comanda, LocalDateTime> dateColumn;
    @FXML
    public TableColumn<Comanda, String> detailsColumn;
    @FXML
    public Button acceptButton;
    @FXML
    public Button seeStockButton;
    @FXML
    public Button declineButton;

    Service service;
    Angajat loggedAngajat;

    private ObservableList<Comanda> modelComanda = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        initModel();
        service.addObserver(this);
    }

    public void setLoggedAngajat(Angajat loggedAngajat) {
        this.loggedAngajat = loggedAngajat;
    }

    @FXML
    public void initialize() {
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("emailclient"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        detailsColumn.setCellValueFactory(c -> {
            Comanda comanda = c.getValue();

            List<ProdusComanda> produsComandaList = service.getDetaliiComanda(comanda.getId());

            String str = "";
            for (ProdusComanda pc : produsComandaList) {
                Produs produs = service.findProdus(pc.getIdProdus());
                str += produs.getDenumire()+ ", ";
            }
            if(str.length() > 1)
                str = str.substring(0, str.length() - 2);

            return new ReadOnlyObjectWrapper<String>(str);
        });
        ordersTable.setItems(modelComanda);

        ordersTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
    }

    private void initModel() {
        modelComanda.setAll();
        List<Comanda> comenziPending = service.filterComenzi(Stadiu.Pending);
        modelComanda.addAll(comenziPending);
    }

    public void handleAcceptOrder(ActionEvent actionEvent) {
        List<Comanda> comenziSelectate = ordersTable.getSelectionModel().getSelectedItems();

        if(comenziSelectate.size() > 0)
            for(Comanda c : comenziSelectate) {
                c.setStadiuCurent(Stadiu.Confirmat);
                service.updateComanda(c);
            }

        //initModel();
    }

    public void handleSeeStock(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/Stock_Angajat.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Angajat Page : "+loggedAngajat.getUsername());
        StockAngajatController stockAngajatController = fxmlLoader.getController();
        stockAngajatController.setLoggedAngajat(loggedAngajat);
//        service.addObserver(stockAngajatController); //!!!!!
        stockAngajatController.setService(service);
        stage.show();
        close();
    }
    private void close() {
        Stage thisStage = (Stage) acceptButton.getScene().getWindow();
        thisStage.close();
    }

    @Override
    public void update() {
        initModel();
    }

    /*public void handleDeclineOrder(ActionEvent actionEvent) {
        List<Comanda> comenziSelectate = ordersTable.getSelectionModel().getSelectedItems();

        for(Comanda c : comenziSelectate) {
            service.deleteComanda(c);
        }

        //initModel();
    }*/
}
