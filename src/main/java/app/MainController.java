package app;

import java.io.File;
import java.sql.Connection;

import app.model.CancionDAO;
import app.model.PlaylistDAO;
import app.utils.UtilsBD;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MainController {

	// Conexion con BD
	Connection con = UtilsBD.conectarBD();

	// Botones
	@FXML
	private Button last;

	@FXML
	private Button startStop;

	@FXML
	private Button next;

	@FXML
	private Button stop;

	@FXML
	private Button exit;

	@FXML
	private Button john;

	// Imagenes
	@FXML
	private ImageView dancer;

	@FXML
	private ImageView playImg;

	@FXML
	private ImageView pauseImg;

	@FXML
	private ImageView imgPlaylist;

	// Sliders
	@FXML
	private Pane slider;

	@FXML
	private Slider repSlider;

	// Labels
	@FXML
	private Label optionsIn;

	@FXML
	private Label optionsOut;

	@FXML
	private Label actTime;

	@FXML
	private Label totalTime;

	@FXML
	private Label playlistName;

	@FXML
	private Label lblOne;

	// Atributos Musica
	private MediaPlayer mp;
	private boolean isPlaying = false;
	private int idCancionActual = 0;

	// Metodos
	@FXML
	private void initialize() {
		// Cargamos lista de canciones
		CancionDAO.listarCanciones(con);

		System.out.println(PlaylistDAO.cancionesPlaylist(con, 1)); // CAMBIAR LABELS

		// Cargo una cancion al iniciar la aplicacion
		String song = CancionDAO.cargarCancion(con, 1);
		Media sound = new Media(new File(song).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(() -> {
			siguienteCancion(null);
		});
		duracionTotal();
		sincSlider();

		startStop.setGraphic(playImg);
		slider.setTranslateX(50);

		TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.1), slider);
		slideIn.setByX(-50);

		TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.1), slider);
		slideOut.setByX(50);

		optionsIn.setOnMouseClicked(event -> {
			slideOut.stop();
			slideIn.play();
			optionsIn.setDisable(true);
			optionsOut.setDisable(false);
		});

		optionsOut.setOnMouseClicked(event -> {
			slideIn.stop();
			slideOut.play();
			optionsIn.setDisable(false);
			optionsOut.setDisable(true);
		});

		exit.setOnAction(e -> {
			System.exit(0);
		});

		john.setOnMouseEntered(e -> {
			john.setLayoutY(-100);
		});
	}

	// Cambiar imagen y texto segun playlist
//	Image img = new Image(getClass().getResourceAsStream(PlaylistDAO.cargarPlaylist(con, 1)[1]));
//	playlistName.setText(PlaylistDAO.cargarPlaylist(con, 1)[0]);
//	imgPlaylist.setImage(img);

	// Cambiar imagen bailarin segun config
//	 dancer.setImage(new Image(getClass().getResourceAsStream("/img/xtreme-dance.gif")));

	// Cambiar nombre segun playlist
//	lblOne.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(0));

	@FXML
	private void duracionTotal() {
		String total = CancionDAO.songs.get(idCancionActual).getDuracion();
		totalTime.setText(total);

		String[] time = total.split(":");
		int min = Integer.parseInt(time[0]);
		int sec = Integer.parseInt(time[1]);
		repSlider.setMax((min * 60) + sec);
	}

	@FXML
	private void sincSlider() {
		mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				repSlider.setValue(newValue.toSeconds());
			}
		});

		mp.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
			String formTime = formatTime(newValue);
			actTime.setText(formTime);
		});

		repSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (repSlider.isValueChanging()) {
				mp.seek(Duration.seconds(newValue.doubleValue()));
			}
		});

	}

	private String formatTime(Duration tiempo) {
		int min = (int) tiempo.toMinutes();
		int sec = (int) tiempo.toSeconds() % 60;
		return String.format("%02d:%02d", min, sec);
	}

	@FXML
	private void reproducir(ActionEvent e) {
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
	private void siguienteCancion(ActionEvent e) {
		if (mp != null) {
			mp.stop();
			isPlaying = false;
		}

		idCancionActual++;
		if (idCancionActual >= CancionDAO.songs.size()) {
			idCancionActual = 0;
		}

		String song = CancionDAO.cargarCancion(con, CancionDAO.songs.get(idCancionActual).getIdCancion());
		Media sound = new Media(new File(song).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(() -> {
			siguienteCancion(null);
		});
		duracionTotal();
		sincSlider();
		mp.play();
		dancer.setVisible(true);
		playImg.setVisible(false);
		pauseImg.setVisible(true);
		startStop.setGraphic(pauseImg);

		isPlaying = true;
	}

	@FXML
	private void anteriorCancion(ActionEvent e) {
		if (mp != null) {
			mp.stop();
			isPlaying = false;
		}

		idCancionActual--;
		if (idCancionActual < 0) {
			idCancionActual = CancionDAO.songs.size() - 1;
		}

		String song = CancionDAO.cargarCancion(con, CancionDAO.songs.get(idCancionActual).getIdCancion());
		Media sound = new Media(new File(song).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(() -> {
			siguienteCancion(null);
		});
		duracionTotal();
		sincSlider();
		mp.play();
		dancer.setVisible(true);
		playImg.setVisible(false);
		pauseImg.setVisible(true);
		startStop.setGraphic(pauseImg);

		isPlaying = true;
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

		// stop.setDisable(true);
		// stop.setVisible(false);

		String song = "C:/Users/Curro/Desktop/Tareas Clases/DAW/Programación/proyectos/javafx-fxml/src/main/resources/music/john-cena.mp3";
		Media sound = new Media(new File(song).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.play();

		isPlaying = true;
	}
}
