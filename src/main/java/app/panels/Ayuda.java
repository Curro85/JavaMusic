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
		Hyperlink link1 = new Hyperlink("https://www.w3schools.com/java");
		link1.setVisited(false);
		Hyperlink link2 = new Hyperlink("https://jenkov.com/tutorials/javafx");
		link2.setVisited(false);
		Hyperlink link3 = new Hyperlink("https://www.javatpoint.com/javafx-css");
		link3.setVisited(false);
		Hyperlink link4 = new Hyperlink("https://openjfx.io/openjfx-docs/#install-javafx");
		link4.setVisited(false);
		Hyperlink link5 = new Hyperlink("https://code.makery.ch/blog/javafx-8-dialogs/");
		link5.setVisited(false);

		vbox.getChildren().addAll(link1, link2, link3, link4, link5);

		// Agregamos un enlace a GitHub
		Hyperlink githubLink = new Hyperlink("GitHub Repository");
		githubLink.setVisited(false);
		githubLink.setOnAction(event -> {
			getHostServices().showDocument("https://github.com/Curro85/JavaMusic");
		});
		vbox.getChildren().add(githubLink);

		// Agregamos onAction a los enlaces
		link1.setOnAction(event -> {
			getHostServices().showDocument("https://www.w3schools.com/java/default.asp");
		});

		link2.setOnAction(event -> {
			getHostServices().showDocument("https://jenkov.com/tutorials/javafx/index.html");
		});

		link3.setOnAction(event -> {
			getHostServices().showDocument("https://www.javatpoint.com/javafx-css");
		});

		link4.setOnAction(event -> {
			getHostServices().showDocument("https://openjfx.io/openjfx-docs/#install-javafx");
		});

		link5.setOnAction(event -> {
			getHostServices().showDocument("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		});

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
