package app;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

import app.model.CancionDAO;
import app.model.PlaylistDAO;
import app.panels.Acercade;
import app.panels.Ayuda;
import app.panels.BorrarPlaylistPanel;
import app.panels.CambiarPlaylistPanel;
import app.panels.CrearPlaylistPanel;
import app.panels.Perfil;
import app.panels.Registro;
import app.panels.Sugerencias;
import app.utils.UtilsBD;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController {

	// Conexion con BD
	Connection con = UtilsBD.conectarBD();

	// Botones
	@FXML
	private Button last, startStop, next, stop, exit, john;

	// Imagenes
	@FXML
	private ImageView dancer, playImg, pauseImg, imgPlaylist;

	// Sliders
	@FXML
	private Pane slider;

	@FXML
	private Slider repSlider;

	// Labels
	@FXML
	private Label optionsIn, optionsOut, actTime, totalTime, playlistName, lblUno, lblDos, lblTres, lblCuatro, lblCinco,
			timeUno, timeDos, timeTres, timeCuatro, timeCinco;

	// Atributos Musica
	private MediaPlayer mp;
	private boolean isPlaying = false;
	private int idCancionActual = 0;

	// Metodos
	@FXML
	private void initialize() {
		// Cargamos lista de canciones
		CancionDAO.listarCanciones(con);

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

	}

	@FXML
	private void registro() {
		Registro rg = new Registro();
		Stage stage = new Stage();
		rg.start(stage);
	}

	@FXML
	private void ayuda() {
		Ayuda ayuda = new Ayuda();
		Stage stage = new Stage();
		ayuda.start(stage);
	}

	@FXML
	private void acercaDe() {
		Acercade acerca = new Acercade();
		Stage stage = new Stage();
		acerca.start(stage);
	}

	@FXML
	private void abrirPerfil() {
		Perfil perfil = new Perfil();
		Stage stage = new Stage();
		perfil.start(stage);
	}

	@FXML
	private void sugerencias() {
		Sugerencias sg = new Sugerencias();
		Stage stage = new Stage();
		sg.start(stage);
	}

	@FXML
	private void crearPlaylist() {
		CrearPlaylistPanel pl = new CrearPlaylistPanel();
		Stage stage = new Stage();
		pl.start(stage);
	}

	@FXML
	private void borrarPlaylist() {
		BorrarPlaylistPanel pl = new BorrarPlaylistPanel();
		Stage stage = new Stage();
		pl.start(stage);
	}

	@FXML
	private void cambiarPlaylist() {
		CambiarPlaylistPanel pl = new CambiarPlaylistPanel(this);
		Stage stage = new Stage();
		pl.start(stage);
	}

	public void changePl(int id) {
		// Cambiar imagen y texto segun playlist
		ArrayList<String> canciones = new ArrayList<String>();
		canciones = PlaylistDAO.cancionesPlaylist(con, id);
		Image img = new Image(getClass().getResourceAsStream(PlaylistDAO.cargarPlaylist(con, id)[1]));
		lblUno.setText(canciones.get(0));
		timeUno.setText(canciones.get(1));

		lblDos.setText(canciones.get(2));
		timeDos.setText(canciones.get(3));

		lblTres.setText(canciones.get(4));
		timeTres.setText(canciones.get(5));

		lblCuatro.setText(canciones.get(6));
		timeCuatro.setText(canciones.get(7));

		lblCinco.setText(canciones.get(8));
		timeCinco.setText(canciones.get(9));
		playlistName.setText(PlaylistDAO.cargarPlaylist(con, id)[0]);
		imgPlaylist.setFitHeight(245);
		imgPlaylist.setFitWidth(272);
		imgPlaylist.setImage(img);

	}

	// Cambiar imagen bailarin segun config
//	 dancer.setImage(new Image(getClass().getResourceAsStream("/img/xtreme-dance.gif")));

	// Cambiar nombre segun playlist
//	lblOne.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(0));

	@FXML
	private void duracionTotal() {
		String total = CancionDAO.listarCanciones(con).get(idCancionActual).getDuracion();
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
		if (idCancionActual >= CancionDAO.listarCanciones(con).size()) {
			idCancionActual = 0;
		}

		String song = CancionDAO.cargarCancion(con,
				CancionDAO.listarCanciones(con).get(idCancionActual).getIdCancion());
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
			idCancionActual = CancionDAO.listarCanciones(con).size() - 1;
		}

		String song = CancionDAO.cargarCancion(con,
				CancionDAO.listarCanciones(con).get(idCancionActual).getIdCancion());
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
