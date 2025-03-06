package javafxapplication3;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ViewProductsController {

    @FXML
    private FlowPane productsFlowPane;  // fx:id of the FlowPane in the FXML

    // This method will be called when the view is initialized
    @FXML
    public void initialize() {
        // Load the products from the Database and display them in the FlowPane
        for (Product product : Database.products) {
            // Create a Label for each product to display its name and price
            Label productLabel = new Label(product.getName() + " - $" + product.getPrice());
            productLabel.setFont(new Font("Arial", 14));  // You can change the font as needed
            productLabel.setWrapText(true);  // Allows for text wrapping if necessary
            productLabel.setMaxWidth(150.0);  // You can adjust the width of the labels

            // Add the label to the FlowPane
            productsFlowPane.getChildren().add(productLabel);
        }
    }
}
