package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCategoryController implements Initializable {

    @FXML
    private ListView<String> categoriesListView;

    @FXML
    private TextField categoryNameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshCategoryList();
    }

    private void refreshCategoryList() {
        categoriesListView.getItems().clear();
        for (Category category : Database.categories) {
            categoriesListView.getItems().add(String.format("%s - %s",
                    category.getName(), category.getDescription()));
        }
    }

    @FXML
    private void handleCreate(ActionEvent event) {
        String name = categoryNameField.getText().trim();
        String description = descriptionArea.getText().trim();

        // Validation
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Category name is required.");
            return;
        }

        // Check for duplicate category names
        boolean categoryExists = Database.categories.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name));

        if (categoryExists) {
            showAlert(Alert.AlertType.WARNING, "A category with this name already exists.");
            return;
        }

        // Create and add new category
        Category newCategory = new Category(name, description);
        Database.categories.add(newCategory);

        // Show success message
        showAlert(Alert.AlertType.INFORMATION, "Category created successfully!");

        // Clear form
        categoryNameField.clear();
        descriptionArea.clear();

        // Refresh the list view
        refreshCategoryList();
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
            showAlert(Alert.AlertType.ERROR, "Error returning to admin panel.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}