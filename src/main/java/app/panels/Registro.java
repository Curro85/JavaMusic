package app.panels;

import java.sql.Connection;

import app.model.UsuarioDAO;
import app.utils.UtilsBD;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Registro extends Application {

	@Override
	public void start(Stage stage) {

		Connection con = UtilsBD.conectarBD();
		// Creamos un objeto GridPane para organizar los elementos de la interfaz
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Agregamos etiquetas y campos de texto al GridPane
		Label nombreLabel = new Label("Nombre:");
		nombreLabel.setStyle("-fx-text-fill: white");
		GridPane.setConstraints(nombreLabel, 0, 1);
		TextField nombreField = new TextField();
		GridPane.setConstraints(nombreField, 1, 1);

		Label emailLabel = new Label("Email:");
		emailLabel.setStyle("-fx-text-fill: white");
		GridPane.setConstraints(emailLabel, 0, 2);
		TextField emailField = new TextField();
		GridPane.setConstraints(emailField, 1, 2);

		Label passwordLabel = new Label("Contraseña:");
		passwordLabel.setStyle("-fx-text-fill: white");
		GridPane.setConstraints(passwordLabel, 0, 3);
		PasswordField passwordField = new PasswordField();
		GridPane.setConstraints(passwordField, 1, 3);

		// Agregamos un botón al GridPane
		Button guardarButton = new Button("Guardar");
		GridPane.setConstraints(guardarButton, 1, 4);

		// Controlador del boton Guardar
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
			} else {
				UsuarioDAO.registrarUsuario(con, nombre, email, password);
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Registro");
				alert.setHeaderText(null);
				alert.setContentText("Registro completado!");
				alert.show();
				stage.close();
			}
		});

		// Agregamos los elementos al GridPane
		grid.getChildren().addAll(nombreLabel, nombreField, emailLabel, emailField, passwordLabel, passwordField,
				guardarButton);

		// Creamos una escena y la agregamos a la ventana
		Scene scene = new Scene(grid, 300, 250);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		stage.getIcons().add(icon);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Registrate");
		stage.setScene(scene);
		stage.showAndWait();
	}
}
