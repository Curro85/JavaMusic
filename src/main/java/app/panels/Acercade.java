package app.panels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Acercade extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Creamos un objeto VBox para organizar los elementos de la interfaz
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(25, 25, 25, 25));

		// Nombres desarrolladores y copyright
		Label principal = new Label("Desarrollador por: ");
		principal.setStyle("-fx-text-fill: white");
		Label nombreValue1 = new Label("Alejandro Morillo");
		nombreValue1.setStyle("-fx-text-fill: white");
		Label nombreValue2 = new Label("Manuel de Sande");
		nombreValue2.setStyle("-fx-text-fill: white");
		Label nombreValue3 = new Label("Mario Fernandez");
		nombreValue3.setStyle("-fx-text-fill: white");
		Label copyrightLabel = new Label("Copyright © 2024 JavaMusic");
		copyrightLabel.setStyle("-fx-text-fill: white");
		vbox.getChildren().addAll(principal, nombreValue1, nombreValue2, nombreValue3);

		// Agregamos un enlace a GitHub
		Hyperlink githubLink = new Hyperlink("GitHub Repository");
		githubLink.setVisited(false);
		githubLink.setOnAction(event -> {
			getHostServices().showDocument("https://github.com/Curro85/JavaMusic");
		});
		vbox.getChildren().addAll(githubLink, copyrightLabel);

		// Creamos una escena y la agregamos a la ventana
		Scene scene = new Scene(vbox, 300, 250);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Acerca de");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
