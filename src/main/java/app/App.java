package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Scene scene;
	private double ejeX = 0;
	private double ejeY = 0;

	@Override
	public void start(Stage stage) throws IOException {

		// Cargamos el FXML como escena principal
		scene = new Scene(loadFXML("reproductor"));

		// Con este código podemos arrastrar y mover la escena
		// sin necesidad de que esté la ventana predeterminada
		scene.setOnMousePressed(e -> {
			ejeX = e.getSceneX();
			ejeY = e.getSceneY();
		});

		scene.setOnMouseDragged(e -> {
			stage.setX(e.getScreenX() - ejeX);
			stage.setY(e.getScreenY() - ejeY);
		});

		// Cargamos una imagen para el icono y preparamos el stage
		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
		stage.show();
	}

	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}

}