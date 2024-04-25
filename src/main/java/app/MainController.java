package app;

import java.io.File;
import java.sql.Connection;

import app.model.cancionDAO;
import app.utils.UtilsBD;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MainController {

	// Atributos
	@FXML
	private Button last;

	@FXML
	private Button startStop;

	@FXML
	private Button next;

	@FXML
	private Button stop;

	@FXML
	private Button john;

	@FXML
	private ImageView dancer;

	@FXML
	private ImageView playImg;

	@FXML
	private ImageView pauseImg;

	private MediaPlayer mp;
	private boolean isPlaying = false;

	// Conexion con BD
	Connection con = UtilsBD.conectarBD();

	// Metodos
	@FXML
	private void initialize() {
		startStop.setGraphic(playImg);
	}

	@FXML
	private void reproducir(ActionEvent e) {
		if (mp == null) {
			String song = cancionDAO.cargarCancion(con, 1);
			Media sound = new Media(new File(song).toURI().toString());
			mp = new MediaPlayer(sound);
		}

		if (isPlaying) {
			mp.pause();
			dancer.setVisible(false);
			playImg.setVisible(true);
			pauseImg.setVisible(false);
			startStop.setGraphic(playImg);
		} else {
			mp.play();
			dancer.setVisible(true);
			playImg.setVisible(false);
			pauseImg.setVisible(true);
			startStop.setGraphic(pauseImg);
		}

		isPlaying = !isPlaying;
	}

	@FXML
	private void detener(ActionEvent e) {
		if (mp != null) {
			mp.stop();
			dancer.setVisible(false);
			isPlaying = false;
			playImg.setVisible(true);
			pauseImg.setVisible(false);
			startStop.setGraphic(playImg);
		}
	}

	@FXML
	private void john(ActionEvent e) {
		if (mp != null) {
			mp.stop();
			isPlaying = false;
		}

		String song = "C:/Users/Curro/Desktop/Tareas Clases/DAW/Programación/proyectos/javafx-fxml/src/main/resources/music/john-cena.mp3";
		Media sound = new Media(new File(song).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.play();

		isPlaying = true;

	}
}
