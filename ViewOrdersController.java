package javafxapplication3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrdersController implements Initializable {
    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String> orderDetailsColumn;

    @FXML
    private TableColumn<Order, String> paymentMethodColumn;

    @FXML
    private Button backButton;

    @FXML
    private Button refreshButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the columns
        orderDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("orderDetails"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        // Load the orders
        loadOrders();
    }

    private void loadOrders() {
        ObservableList<Order> ordersList = FXCollections.observableArrayList(Database.orders);
        ordersTable.setItems(ordersList);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("adminactions.fxml"));
            Scene adminScene = new Scene(adminView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(adminScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadOrders();
    }
}