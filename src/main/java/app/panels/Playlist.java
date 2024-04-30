package app.panels;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Playlist extends Application {
	private static Scene scene;

	@Override
	public void start(Stage stage) {
		// Panel dividido
		SplitPane spPlay = new SplitPane();

		// Paneles para cada lado del panel dividido
		AnchorPane leftPane = new AnchorPane();
		AnchorPane rightPane = new AnchorPane();

		// VBox para el panel izquierdo
		VBox leftVB = new VBox();
		leftVB.setAlignment(Pos.TOP_CENTER);

		Label lbl1 = new Label();
		lbl1.setText("Cancion1");
		lbl1.getStyleClass().add("label-cancion");
		Label lbl2 = new Label();
		lbl2.setText("Cancion2");
		lbl2.getStyleClass().add("label-cancion");
		Label lbl3 = new Label();
		lbl3.setText("Cancion3");
		lbl3.getStyleClass().add("label-cancion");
		Label lbl4 = new Label();
		lbl4.setText("Cancion4");
		lbl4.getStyleClass().add("label-cancion");
		Label lbl5 = new Label();
		lbl5.setText("Cancion5");
		lbl5.getStyleClass().add("label-cancion");

		leftVB.getChildren().addAll(lbl1, lbl2, lbl3, lbl4, lbl5);

		// Tamaño del VBOX dentro del AnchorPane
		leftPane.setTopAnchor(leftVB, 0.0);
		leftPane.setBottomAnchor(leftVB, 0.0);
		leftPane.setLeftAnchor(leftVB, 0.0);
		leftPane.setRightAnchor(leftVB, 0.0);
		leftPane.getChildren().addAll(leftVB);

		// Introduzco los paneles dentro del panel dividido
		spPlay.getItems().addAll(leftPane, rightPane);
		spPlay.setDividerPositions(0.3);

		scene = new Scene(spPlay, 600, 300);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		stage.getIcons().add(icon);
		stage.setTitle("Crear Playlist");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
