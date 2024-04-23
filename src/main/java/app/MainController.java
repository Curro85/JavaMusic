package app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.ImageView;
import java.io.File;

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
	private ImageView dancer;

	@FXML
	private ImageView playImg;

	@FXML
	private ImageView pauseImg;

	private MediaPlayer mp;
	private boolean isPlaying = false;

	// Metodos
	@FXML
	private void initialize() {
		startStop.setGraphic(playImg);
	}

	@FXML
	private void reproducir(ActionEvent e) {
		if (mp == null) {
			String song = "C:\\Users\\Curro\\Desktop\\Tareas Clases\\DAW\\Programación\\proyectos\\javafx-fxml\\src\\main\\resources\\music\\boomblast bass+speed.mp3";
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
}
