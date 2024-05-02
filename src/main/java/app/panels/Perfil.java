package app.panels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Perfil extends Application {

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
		nombreLabel.setStyle("-fx-text-fill: white");
		GridPane.setConstraints(nombreLabel, 0, 0);
		TextField nombreField = new TextField();
		GridPane.setConstraints(nombreField, 1, 0);

		// Agregamos un botón al GridPane
		Button guardarButton = new Button("Guardar");
		GridPane.setConstraints(guardarButton, 1, 4);

		// Agregamos el elemento al GridPane
		grid.getChildren().addAll(nombreLabel, nombreField, guardarButton);

		// Creamos una escena y la agregamos a la ventana
		Scene scene = new Scene(grid, 300, 250);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.setScene(scene);
		primaryStage.show();

		// Agregamos un controlador a los campos de texto y al botón
		nombreField.setOnAction(event -> {
			String nombre = nombreField.getText();
			System.out.println("Nombre: " + nombre);
		});

		guardarButton.setOnAction(event -> {
			String nombre = nombreField.getText();

			// Validamos que los campos no estén vacíos
			if (nombre.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Introduzca un Nombre");
				alert.show();
			}
		});
	}
}
