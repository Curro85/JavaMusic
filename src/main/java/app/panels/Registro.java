package app.panels;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Registro extends Application {
	
	@Override
    public void start(Stage primaryStage) {
        // Creamos un objeto GridPane para organizar los elementos de la interfaz
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Agregamos etiquetas y campos de texto al GridPane
        Label nombreLabel = new Label("Nombre:");
        GridPane.setConstraints(nombreLabel, 0, 0);
        TextField nombreField = new TextField();
        GridPane.setConstraints(nombreField, 1, 0);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 2);
        TextField emailField = new TextField();
        GridPane.setConstraints(emailField, 1, 2);

        Label passwordLabel = new Label("Contraseña:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        TextField passwordField = new TextField();
        GridPane.setConstraints(passwordField, 1, 3);

        // Agregamos un botón al GridPane
        Button guardarButton = new Button("Guardar");
        GridPane.setConstraints(guardarButton, 1, 4);

        // Agregamos los elementos al GridPane
        grid.getChildren().addAll(nombreLabel, nombreField, emailLabel, emailField, passwordLabel, passwordField, guardarButton);

        // Creamos una escena y la agregamos a la ventana
        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Agregamos un controlador a los campos de texto y al botón
        nombreField.setOnAction(event -> {
            String nombre = nombreField.getText();
            System.out.println("Nombre: " + nombre);
        });


        emailField.setOnAction(event -> {
            String email = emailField.getText();
            System.out.println("Email: " + email);
        });

        passwordField.setOnAction(event -> {
            String password = passwordField.getText();
            System.out.println("Contraseña: " + password);
        });

        guardarButton.setOnAction(event -> {
            String nombre = nombreField.getText();          
            String email = emailField.getText();
            String password = passwordField.getText();

            // Validamos que los campos no estén vacíos
            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Todos los campos son obligatorios");
                alert.setContentText("Por favor, complete todos los campos antes de guardar.");
                alert.show();
            }
           });
	     }
	}
