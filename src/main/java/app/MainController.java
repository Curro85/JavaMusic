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
import app.panels.Video;
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
import javafx.scene.layout.HBox;
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
	private ImageView dancer, playImg, pauseImg, imgPlaylist, elegirBaile;

	// Sliders
	@FXML
	private Pane slider;

	@FXML
	private Slider repSlider;

	// HBox con canciones
	@FXML
	private HBox hb1, hb2, hb3, hb4, hb5;

	// Labels
	@FXML
	private Label optionsIn, optionsOut, actTime, totalTime, playlistName, lblUno, lblDos, lblTres, lblCuatro, lblCinco,
			timeUno, timeDos, timeTres, timeCuatro, timeCinco;

	// Atributos Musica
	private MediaPlayer mp;
	private boolean isPlaying = false;
	private int idCancionActual = 0;
	private int idPlaylistActual = 0;
	private int bailarin = 0;

	// Metodos
	@FXML
	/**
	 * Metodo inicial que carga la configuracion inicial de la aplicacion con una
	 * playlist y una cancion a reproducir
	 */
	private void initialize() {
		// Leemos el fichero de configuracion para cargar el estado del reproductor
		ConfigController.Configuracion config = ConfigController.cargarConfig();
		int idPlaylist = config.getIdPlaylist();
		int idCancion = config.getIdCancion() + 1; // Sumamos uno al id para que coincida con el id de BD
		bailarin = config.getBailarin();
		dancer.setImage(new Image(getClass().getResourceAsStream(cambiarBailarin(bailarin))));

		// Cargamos lista de canciones
		CancionDAO.listarCanciones(con);

		// Cargo una cancion y playlist al iniciar la aplicacion según configuración
		idCancionActual = idCancion - 1; // Restamos uno para que coincida con la posicion en el array
		changePl(idPlaylist);
		String song = CancionDAO.cargarCancion(con, idCancion);
		Media sound = new Media(new File(song).toURI().toString());
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(() -> {
			siguienteCancion(null);
		});
		duracionTotal();
		sincSlider();

		startStop.setGraphic(playImg);
		// Creo una animacion para la barra de opciones
		slider.setTranslateX(50);

		TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.1), slider);
		slideIn.setByX(-50);

		TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.1), slider);
		slideOut.setByX(50);

		// Metodos del boton opciones
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

		// OnClick para las labels y reproducir una cancion
		hb1.setOnMouseClicked(e -> {
			String titulo = lblUno.getText();
			elegirCancion(titulo);
		});

		hb2.setOnMouseClicked(e -> {
			String titulo = lblDos.getText();
			elegirCancion(titulo);
		});

		hb3.setOnMouseClicked(e -> {
			String titulo = lblTres.getText();
			elegirCancion(titulo);
		});

		hb4.setOnMouseClicked(e -> {
			String titulo = lblCuatro.getText();
			elegirCancion(titulo);
		});

		hb5.setOnMouseClicked(e -> {
			String titulo = lblCinco.getText();
			elegirCancion(titulo);
		});

		// Elegir bailarin con la rueda del raton
		dancer.setOnScroll(e -> {
			dancer.setImage(new Image(getClass().getResourceAsStream(cambiarBailarin(bailarin))));
		});

		// Boton para cerrar la aplicacion y guarda la configuracion
		exit.setOnAction(e -> {
			ConfigController.guardarConfig(idPlaylistActual, idCancionActual, bailarin);
			System.exit(0);
		});

	}

	/**
	 * Metodo para elegir una cancion de la label que se muestra
	 * 
	 * @param titulo
	 */
	private void elegirCancion(String titulo) {
		// Si el reproductor está activo lo pauso
		if (mp != null) {
			mp.stop();
			isPlaying = false;
		}

		// Selecciono la cancion segun el parametro pasado
		int idElegida = CancionDAO.reproducirCancion(con, titulo);
		idCancionActual = idElegida - 1;
		// Cargo la cancion al reproductor e inicio su reproduccion
		String cancion = CancionDAO.cargarCancion(con, idElegida);
		Media cancionElegida = new Media(new File(cancion).toURI().toString());
		mp = new MediaPlayer(cancionElegida);
		mp.setOnEndOfMedia(() -> {
			siguienteCancion(null);
		});
		totalTime.setText(CancionDAO.listarCanciones(con).get(idElegida - 1).getDuracion());
		sincSlider();
		reproducir(null);
	}

	@FXML
	/**
	 * Metodo para abrir la ventana de registro
	 */
	private void registro() {
		Registro rg = new Registro();
		Stage stage = new Stage();
		rg.start(stage);
	}

	@FXML
	/**
	 * Metodo para abrir la ventana de ayuda
	 */
	private void ayuda() {
		Ayuda ayuda = new Ayuda();
		Stage stage = new Stage();
		ayuda.start(stage);
	}

	@FXML
	/**
	 * Metodo para abrir la ventana acerca de
	 */
	private void acercaDe() {
		Acercade acerca = new Acercade();
		Stage stage = new Stage();
		acerca.start(stage);
	}

	@FXML
	/**
	 * Metodo para abrir la ventana de perfil
	 */
	private void abrirPerfil() {
		Perfil perfil = new Perfil();
		Stage stage = new Stage();
		perfil.start(stage);
	}

	@FXML
	/**
	 * Metodo para abrir la ventana sugerencias
	 */
	private void sugerencias() {
		Sugerencias sg = new Sugerencias();
		Stage stage = new Stage();
		sg.start(stage);
	}

	@FXML
	/**
	 * Metodo para la ventana crear Playlist
	 */
	private void crearPlaylist() {
		CrearPlaylistPanel pl = new CrearPlaylistPanel();
		Stage stage = new Stage();
		pl.start(stage);
	}

	@FXML
	/**
	 * Metodo para la ventana borrar Playlist
	 */
	private void borrarPlaylist() {
		BorrarPlaylistPanel pl = new BorrarPlaylistPanel();
		Stage stage = new Stage();
		pl.start(stage);
	}

	@FXML
	/**
	 * Metodo para la ventana cambiar Playlist
	 */
	private void cambiarPlaylist() {
		CambiarPlaylistPanel pl = new CambiarPlaylistPanel(this);
		Stage stage = new Stage();
		pl.start(stage);
	}

	/**
	 * Metodo que recibe un id de playlist para cambiarla por la actual
	 * 
	 * @param id
	 */
	public void changePl(int id) {
		// Cambiar imagen y texto segun playlist
		idPlaylistActual = id;
		ArrayList<String> canciones = new ArrayList<String>();
		canciones = PlaylistDAO.cancionesPlaylist(con, id);
		Image img = new Image(getClass().getResourceAsStream(PlaylistDAO.cargarPlaylist(con, id)[1]));
		// Despues de cargar las canciones en un arraylist cambio la imagen de la
		// playlist
		// y las labels con los nombres y tiempo de las canciones
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

	@FXML
	/**
	 * Metodo para mostrar u ocultar el bailarn
	 */
	private void bailarin() {
		if (dancer.isVisible()) {
			dancer.setVisible(false);
		} else {
			dancer.setVisible(true);
		}
	}

	/**
	 * Metodo que recibe el numero de bailarin y segun ese numero lo añade al
	 * imageview
	 * 
	 * @param bailarin
	 * @return
	 */
	private String cambiarBailarin(int bailarin) {
		if (bailarin == 0) {
			this.bailarin++;
			return "/img/carlton-dance.gif";
		} else if (bailarin == 1) {
			this.bailarin++;
			return "/img/xtreme-dance.gif";
		} else if (bailarin == 2) {
			this.bailarin++;
			return "/img/khaled-dance.gif";
		} else if (bailarin == 3) {
			this.bailarin++;
			return "/img/snoop-dance.gif";
		} else {
			this.bailarin = 0;
			return "/img/carlton-dance.gif";
		}
	}

	@FXML
	/**
	 * Metodo que muestra la duracion de la cancion
	 */
	private void duracionTotal() {
		// Obtengo la cancion actual en reproduccion
		String total = CancionDAO.listarCanciones(con).get(idCancionActual).getDuracion();
		totalTime.setText(total);

		// Transformo todo el tiempo en segundos y se lo vinculo al slider para que
		// avance conforme a la cancion
		String[] time = total.split(":");
		int min = Integer.parseInt(time[0]);
		int sec = Integer.parseInt(time[1]);
		repSlider.setMax((min * 60) + sec);
	}

	@FXML
	/**
	 * Metodo que "observa" la reproduccion y ajusta el slider segun el momento en
	 * el que esté la canción
	 */
	private void sincSlider() {
		// Creo una "escucha" al tiempo que me pasa el reproductor y actualizo su valor
		// conforme avanza para así obtener el tiempo de la cancion
		mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				repSlider.setValue(newValue.toSeconds());
			}
		});

		// Pongo formato del tiempo en minutos : segundos
		mp.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
			String formTime = formatTime(newValue);
			actTime.setText(formTime);
		});

		// Añado una "escucha" para poder arrastrar y avanzar la reproduccion con el
		// ratón
		repSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (repSlider.isValueChanging()) {
				mp.seek(Duration.seconds(newValue.doubleValue()));
			}
		});

	}

	/**
	 * Funcion que formatea el tiempo de las canciones
	 * 
	 * @param tiempo
	 * @return
	 */
	private String formatTime(Duration tiempo) {
		int min = (int) tiempo.toMinutes();
		int sec = (int) tiempo.toSeconds() % 60;
		return String.format("%02d:%02d", min, sec);
	}

	@FXML
	/**
	 * Metodo para reproducir las canciones o pausarlas
	 * 
	 * @param e
	 */
	private void reproducir(ActionEvent e) {
		// Si está en reproduccion lo pauso
		if (isPlaying) {
			mp.pause();
			playImg.setVisible(true);
			pauseImg.setVisible(false);
			startStop.setGraphic(playImg);
		} else {
			mp.play();
			playImg.setVisible(false);
			pauseImg.setVisible(true);
			startStop.setGraphic(pauseImg);
		}

		// Actualizo el estado del reproductor a su contrario
		isPlaying = !isPlaying;
	}

	@FXML
	/**
	 * Metodo para pasar a la siguiente cancion
	 * 
	 * @param e
	 */
	private void siguienteCancion(ActionEvent e) {
		// Si el reproductor está activo lo paro
		if (mp != null) {
			mp.stop();
			isPlaying = false;
		}

		// Incremento el id de la cancion actual
		idCancionActual++;
		// Si supera al tamaño del arraylist reinicio el reproductor
		if (idCancionActual >= CancionDAO.listarCanciones(con).size()) {
			idCancionActual = 0;
		}

		// Cargo la cancion al reproductor
		String song = CancionDAO.cargarCancion(con,
				CancionDAO.listarCanciones(con).get(idCancionActual).getIdCancion());
		Media sound = new Media(new File(song).toURI().toString());
		// Inicio la reproduccion
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(() -> {
			siguienteCancion(null);
		});
		duracionTotal();
		sincSlider();
		mp.play();
		playImg.setVisible(false);
		pauseImg.setVisible(true);
		startStop.setGraphic(pauseImg);

		// Actualizo el estado del reproductor
		isPlaying = true;
	}

	@FXML
	/**
	 * Metodo para retroceder en las canciones
	 * 
	 * @param e
	 */
	private void anteriorCancion(ActionEvent e) {
		// Si el reproductor está activo lo paro
		if (mp != null) {
			mp.stop();
			isPlaying = false;
		}

		// Decremento el id de la cancion actual
		idCancionActual--;
		// Si es inferior a cero lo pongo al inicio del arraylist
		if (idCancionActual < 0) {
			idCancionActual = CancionDAO.listarCanciones(con).size() - 1;
		}

		// Cargo la cancion al reproductor
		String song = CancionDAO.cargarCancion(con,
				CancionDAO.listarCanciones(con).get(idCancionActual).getIdCancion());
		Media sound = new Media(new File(song).toURI().toString());
		// Inicio la reproduccion
		mp = new MediaPlayer(sound);
		mp.setOnEndOfMedia(() -> {
			siguienteCancion(null);
		});
		duracionTotal();
		sincSlider();
		mp.play();
		playImg.setVisible(false);
		pauseImg.setVisible(true);
		startStop.setGraphic(pauseImg);

		// Actualizo el estado del reproductor
		isPlaying = true;
	}

	@FXML
	/**
	 * Metodo para detener la reproduccion
	 * 
	 * @param e
	 */
	private void detener(ActionEvent e) {
		// Si el reproductor está activo, lo paro
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

		Video video = new Video();
		Stage stage = new Stage();
		video.start(stage);
	}
}
