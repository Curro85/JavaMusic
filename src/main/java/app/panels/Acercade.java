package app.panels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Acercade extends Application{
	
	@Override
    public void start(Stage primaryStage) {
        // Creamos un objeto VBox para organizar los elementos de la interfaz
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(25, 25, 25, 25));

        // Agregamos etiquetas y campos de texto al VBox
        Label nombreLabel1 = new Label("Nombre 1:");
        Label nombreLabel2 = new Label("Nombre 2:");
        Label nombreLabel3 = new Label("Nombre 3:");
        vbox.getChildren().addAll(nombreLabel1, nombreLabel2, nombreLabel3);

        Label nombreValue1 = new Label("Alejandro Morillo");
        Label nombreValue2 = new Label("Manuel de Sande");
        Label nombreValue3 = new Label("Mario Fernandez");
        vbox.getChildren().addAll(nombreValue1, nombreValue2, nombreValue3);

        // Agregamos un enlace a GitHub
        Hyperlink githubLink = new Hyperlink("GitHub Repository");
        githubLink.setVisited(false);
        githubLink.setOnAction(event -> {
            getHostServices().showDocument("https://github.com/jjenkov/javafx-examples");
        });
        vbox.getChildren().add(githubLink);

        // Creamos una escena y la agregamos a la ventana
        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Acerca de");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


