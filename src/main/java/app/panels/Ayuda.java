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

public class Ayuda extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Creamos un objeto VBox para organizar los elementos de la interfaz
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(25, 25, 25, 25));

		// Agregamos cinco enlaces a la interfaz
		Hyperlink link1 = new Hyperlink("Link 1");
		Hyperlink link2 = new Hyperlink("Link 2");
		Hyperlink link3 = new Hyperlink("Link 3");
		Hyperlink link4 = new Hyperlink("Link 4");
		Hyperlink link5 = new Hyperlink("Link 5");

		vbox.getChildren().addAll(link1, link2, link3, link4, link5);

		// Agregamos un enlace a GitHub
		Hyperlink githubLink = new Hyperlink("GitHub Repository");
		githubLink.setVisited(false);
		githubLink.setOnAction(event -> {
			getHostServices().showDocument("https://github.com/jjenkov/javafx-examples");
		});
		vbox.getChildren().add(githubLink);

		// Agregamos un copyright a la interfaz
		Label copyrightLabel = new Label("Copyright © 2023 JavaMusic");
		copyrightLabel.setStyle("-fx-text-fill: white");
		vbox.getChildren().add(copyrightLabel);

		// Creamos una escena y la agregamos a la ventana
		Scene scene = new Scene(vbox, 300, 250);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		primaryStage.getIcons().add(icon);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Ayuda");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
