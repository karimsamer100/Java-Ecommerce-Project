package javafxapplication3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {
    @FXML private RadioButton storeCreditRadio;
    @FXML private RadioButton creditCardRadio;
    @FXML private RadioButton paypalRadio;
    @FXML private ListView<String> cartItemsListView;
    @FXML private Label totalAmountLabel;
    @FXML private Label storeCreditLabel;
    @FXML private TextArea orderConfirmationText;
    @FXML private Button placeOrderButton;
    @FXML private Button cancelButton;

    private Cart currentCart;
    private Customer currentCustomer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentCart = Database.getCurrentCart();
        currentCustomer = Database.getLoggedInCustomer();

        // Initialize the view
        updateCartDisplay();
        updateStoreCreditDisplay();

        // Select credit card as default payment method
        creditCardRadio.setSelected(true);
    }

    private void updateCartDisplay() {
        cartItemsListView.getItems().clear();
        for (Product product : currentCart.getProducts()) {
            cartItemsListView.getItems().add(
                    String.format("%s - $%.2f", product.getName(), product.getPrice())
            );
        }
        totalAmountLabel.setText(String.format("%.2f", currentCart.getTotalAmount()));
    }

    private void updateStoreCreditDisplay() {
        if (currentCustomer != null) {
            storeCreditLabel.setText(String.format("%.2f", currentCustomer.getStoreCredit()));
            storeCreditRadio.setDisable(currentCustomer.getStoreCredit() <= 0);
        }
    }

    @FXML
    private void handlePlaceOrder() {
        if (currentCart.getProducts().isEmpty()) {
            showAlert("Error", "Cart is empty!");
            return;
        }

        String paymentMethod = getSelectedPaymentMethod();
        double orderTotal = currentCart.getTotalAmount();

        // Handle store credit payment
        if (paymentMethod.equals("Store Credit")) {
            if (currentCustomer.getStoreCredit() < orderTotal) {
                showAlert("Error", "Insufficient store credit!");
                return;
            }
            currentCustomer.setStoreCredit(currentCustomer.getStoreCredit() - orderTotal);
        }

        // Create order details
        String orderDetails = String.format("Order placed by %s using %s for a total of $%.2f",
                currentCustomer.getUsername(),
                paymentMethod,
                orderTotal);

        // Create and save order
        Order order = new Order(orderDetails, paymentMethod);
        Database.orders.add(order);

        // Update product stock
        for (Product product : currentCart.getProducts()) {
            product.setStock(product.getStock() - 1);
        }

        // Add store credit (5% of purchase)
        double earnedCredit = orderTotal * 0.05;
        currentCustomer.setStoreCredit(currentCustomer.getStoreCredit() + earnedCredit);

        // Show confirmation and clear cart
        String confirmation = String.format("Payment completed using %s. You earned $%.2f in store credit! " +
                "Your cart has been cleared. Order placed successfully!", paymentMethod, earnedCredit);
        orderConfirmationText.setText(confirmation);

        // Clear the cart
        currentCart.clearCart();

        // Disable place order button
        placeOrderButton.setDisable(true);
    }

    private String getSelectedPaymentMethod() {
        if (storeCreditRadio.isSelected()) return "Store Credit";
        if (creditCardRadio.isSelected()) return "Credit Card";
        if (paypalRadio.isSelected()) return "PayPal";
        return "Credit Card"; // default
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void goToMainPage(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load main page.");
        }
    }

}