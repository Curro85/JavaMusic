package app.panels;

import java.sql.Connection;
import app.model.PlaylistDAO;
import app.utils.UtilsBD;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CrearPlaylistPanel extends Application {

	// Conexion con Base de Datos
	Connection con = UtilsBD.conectarBD();

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
		leftVB.setAlignment(Pos.TOP_LEFT);

		// Labels del lado izquierdo
		Label lbl1 = new Label();
		lbl1.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(0));
		lbl1.getStyleClass().add("label-cancion");
		Label lbl2 = new Label();
		lbl2.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(2));
		lbl2.getStyleClass().add("label-cancion");
		Label lbl3 = new Label();
		lbl3.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(4));
		lbl3.getStyleClass().add("label-cancion");
		Label lbl4 = new Label();
		lbl4.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(6));
		lbl4.getStyleClass().add("label-cancion");
		Label lbl5 = new Label();
		lbl5.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(8));
		lbl5.getStyleClass().add("label-cancion");

		// Margenes de los labels
		VBox.setMargin(lbl1, new Insets(10, 0, 0, 0));
		VBox.setMargin(lbl2, new Insets(10, 0, 0, 0));
		VBox.setMargin(lbl3, new Insets(10, 0, 0, 0));
		VBox.setMargin(lbl4, new Insets(10, 0, 0, 0));
		VBox.setMargin(lbl5, new Insets(10, 0, 0, 0));

		// Añado los labels al VBox
		leftVB.getChildren().addAll(lbl1, lbl2, lbl3, lbl4, lbl5);

		// Tamaño del VBOX dentro del AnchorPane izquierdo
		AnchorPane.setTopAnchor(leftVB, 0.0);
		AnchorPane.setBottomAnchor(leftVB, 0.0);
		AnchorPane.setLeftAnchor(leftVB, 0.0);
		AnchorPane.setRightAnchor(leftVB, 0.0);

		// Añadimos VBox al panel izquierdo
		leftPane.getChildren().addAll(leftVB);

		// VBox para el panel derecho
		VBox rightVB = new VBox();
		rightVB.setAlignment(Pos.TOP_CENTER);

		// Imagen de la playlist
		ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/img/playlist-img.png")));
		img.setFitWidth(200);
		img.setFitHeight(150);

		// Labels para introducir en el VBox derecho
		Label lbl1R = new Label();
		lbl1R.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(0));
		lbl1R.getStyleClass().add("label-cancion");
		lbl1R.setVisible(false);
		Label lbl2R = new Label();
		lbl2R.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(2));
		lbl2R.getStyleClass().add("label-cancion");
		lbl2R.setVisible(false);
		Label lbl3R = new Label();
		lbl3R.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(4));
		lbl3R.getStyleClass().add("label-cancion");
		lbl3R.setVisible(false);
		Label lbl4R = new Label();
		lbl4R.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(6));
		lbl4R.getStyleClass().add("label-cancion");
		lbl4R.setVisible(false);
		Label lbl5R = new Label();
		lbl5R.setText(PlaylistDAO.cancionesPlaylist(con, 1).get(8));
		lbl5R.getStyleClass().add("label-cancion");
		lbl5R.setVisible(false);

		// Margenes de los Labels y la imagen
		VBox.setMargin(img, new Insets(20, 0, 0, 0));
		VBox.setMargin(lbl1R, new Insets(10, 0, 0, 0));
		VBox.setMargin(lbl2R, new Insets(10, 0, 0, 0));
		VBox.setMargin(lbl3R, new Insets(10, 0, 0, 0));
		VBox.setMargin(lbl4R, new Insets(10, 0, 0, 0));
		VBox.setMargin(lbl5R, new Insets(10, 0, 0, 0));

		// TextField para el nombre de la Playlist
		TextField nomPlaylist = new TextField();
		nomPlaylist.setLayoutX(14);
		nomPlaylist.setLayoutY(359);
		nomPlaylist.setPromptText("Playlist 1");

		// Botones para crear o cancelar la playlist
		Button btnAccept = new Button("Crear");
		btnAccept.setLayoutX(183);
		btnAccept.setLayoutY(359);
		btnAccept.setCursor(Cursor.HAND);

		Button btnCancel = new Button("Cancelar");
		btnCancel.setLayoutX(351);
		btnCancel.setLayoutY(359);
		btnCancel.setCursor(Cursor.HAND);

		// Funciones de los botones
		btnAccept.setOnAction(e -> {
			String nombrePl = nomPlaylist.getText();
			String rutaImgPl = "/img/john-face.png";
			PlaylistDAO.agregarPlaylist(con, nombrePl, rutaImgPl, 1);
			stage.close();
		});

		btnCancel.setOnAction(e -> {
			stage.close();
		});

		// Evento para las labels de la izquierda
		lbl1.setOnMouseClicked(e -> {
			lbl1R.setVisible(true);
		});

		lbl2.setOnMouseClicked(e -> {
			lbl2R.setVisible(true);
		});

		lbl3.setOnMouseClicked(e -> {
			lbl3R.setVisible(true);
		});

		lbl4.setOnMouseClicked(e -> {
			lbl4R.setVisible(true);
		});

		lbl5.setOnMouseClicked(e -> {
			lbl5R.setVisible(true);
		});

		// Añadir todo al VBox
		rightVB.getChildren().addAll(img, lbl1R, lbl2R, lbl3R, lbl4R, lbl5R);

		AnchorPane.setTopAnchor(rightVB, 0.0);
		AnchorPane.setBottomAnchor(rightVB, 50.0);
		AnchorPane.setLeftAnchor(rightVB, 0.0);
		AnchorPane.setRightAnchor(rightVB, 0.0);

		// Añadimos VBox y botones al panel derecho
		rightPane.getChildren().addAll(rightVB, nomPlaylist, btnAccept, btnCancel);

		// Introduzco los paneles dentro del panel dividido
		spPlay.getItems().addAll(leftPane, rightPane);
		spPlay.setDividerPositions(0.3);

		scene = new Scene(spPlay, 600, 400);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		stage.getIcons().add(icon);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Crear Playlist");
		stage.setScene(scene);
		stage.showAndWait();
	}
}
