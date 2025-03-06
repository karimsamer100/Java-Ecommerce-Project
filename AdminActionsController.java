package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminActionsController {
    @FXML
    private Button showUsersBtn;
    @FXML
    private Button showProductsBtn;
    @FXML
    private Button showOrdersBtn;
    @FXML
    private Button createProductBtn;
    @FXML
    private Button updateProductBtn;
    @FXML
    private Button deleteProductBtn;

    @FXML
    private void handleShowUsers(ActionEvent event) {
        try {
            Parent usersView = FXMLLoader.load(getClass().getResource("viewusers.fxml"));
            Scene usersScene = new Scene(usersView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(usersScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowProducts(ActionEvent event) {
        try {
            Parent productsView = FXMLLoader.load(getClass().getResource("viewproducts.fxml"));
            Scene productsScene = new Scene(productsView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(productsScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowOrders(ActionEvent event) {
        try {
            Parent ordersView = FXMLLoader.load(getClass().getResource("vieworders.fxml"));
            Scene ordersScene = new Scene(ordersView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(ordersScene);
            window.show();
        } catch (IOException e) {}
    }

    @FXML
    private void handleCreateProduct(ActionEvent event) {
        try {
            Parent createProductView = FXMLLoader.load(getClass().getResource("createproduct.fxml"));
            Scene createProductScene = new Scene(createProductView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(createProductScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateProduct(ActionEvent event) {
        try {
            Parent updateProductView = FXMLLoader.load(getClass().getResource("updateproduct.fxml"));
            Scene updateProductScene = new Scene(updateProductView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(updateProductScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        try {
            Parent deleteProductView = FXMLLoader.load(getClass().getResource("deleteproduct.fxml"));
            Scene deleteProductScene = new Scene(deleteProductView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(deleteProductScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCreateCategory(ActionEvent event) {
        try {
            Parent createCategoryView = FXMLLoader.load(getClass().getResource("createcategory.fxml"));
            Scene createCategoryScene = new Scene(createCategoryView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(createCategoryScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}