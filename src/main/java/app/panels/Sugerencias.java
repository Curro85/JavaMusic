package app.panels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sugerencias extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Creamos un objeto VBox para organizar los elementos de la interfaz
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(25, 25, 25, 25));

		// Agregamos una etiqueta y un campo de texto al VBox
		Label label = new Label("Escriba su sugerencia:");
		label.setStyle("-fx-text-fill: white");
		TextField textField = new TextField();
		vbox.getChildren().addAll(label, textField);

		// Agregamos un botón al VBox
		Button sendButton = new Button("Enviar");
		vbox.getChildren().add(sendButton);

		// Creamos una escena y la agregamos a la ventana
		Scene scene = new Scene(vbox, 300, 250);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sugerencias");
		primaryStage.show();

		// Agregamos un controlador al botón
		sendButton.setOnAction(event -> {
			String sugerencia = textField.getText();
			System.out.println("Sugerencia: " + sugerencia);
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
