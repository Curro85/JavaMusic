package app.panels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Video extends Application {

	private static Scene scene;

	public void start(Stage stage) {
		// Panel principal que contendrá el video
		Pane principal = new Pane();

		// Creamos objeto media con la ruta al video
		String ruta = getClass().getResource("/img/john-entrance.mp4").toExternalForm();
		Media video = new Media(ruta);

		// Creamos el reproductor del video
		MediaPlayer mp = new MediaPlayer(video);
		mp.setAutoPlay(true);
		mp.setCycleCount(MediaPlayer.INDEFINITE);

		// MediaView para visualizar el video
		MediaView mv = new MediaView(mp);

		// Agregamos el mediaview al panel
		principal.getChildren().add(mv);

		// Ajustamos el video al tamaño de la ventana
		mv.fitWidthProperty().bind(stage.widthProperty());
		mv.fitHeightProperty().bind(stage.heightProperty());

		// Creamos la escena
		scene = new Scene(principal, 1250, 756);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		// Mostramos el stage
		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		stage.getIcons().add(icon);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("JOHN CENA");
		stage.setScene(scene);
		stage.setOnCloseRequest(e -> {
			mp.stop();
		});
		stage.showAndWait();
	}

}
