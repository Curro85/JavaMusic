package app.panels;

import java.sql.Connection;

import app.MainController;
import app.model.PlaylistDAO;
import app.utils.UtilsBD;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CambiarPlaylistPanel extends Application {
	// Conexion con BD
	Connection con = UtilsBD.conectarBD();

	private MainController mc;

	public CambiarPlaylistPanel(MainController mc) {
		this.mc = mc;
	}

	private static Scene scene;

	public void start(Stage stage) {
		// VBox principal
		VBox vb = new VBox();

		// HBox con las playlist a elegir
		HBox hb = new HBox();
		hb.setAlignment(Pos.TOP_LEFT);

		// Labels
		Label lbl1 = new Label();
		lbl1.setText(PlaylistDAO.cargarPlaylist(con, 1)[0]);
		lbl1.getStyleClass().add("label-cancion");
		lbl1.setCursor(Cursor.HAND);

		Label lbl2 = new Label();
		lbl2.setText(PlaylistDAO.cargarPlaylist(con, 2)[0]);
		lbl2.getStyleClass().add("label-cancion");
		lbl2.setCursor(Cursor.HAND);

		// Imagenes junto a las labels
		ImageView imgPl1 = new ImageView();
		Image img1 = new Image(getClass().getResourceAsStream(PlaylistDAO.cargarPlaylist(con, 1)[1]));
		imgPl1.setFitHeight(40);
		imgPl1.setFitWidth(40);
		imgPl1.setImage(img1);
		imgPl1.setCursor(Cursor.HAND);

		ImageView imgPl2 = new ImageView();
		Image img2 = new Image(getClass().getResourceAsStream(PlaylistDAO.cargarPlaylist(con, 2)[1]));
		imgPl2.setFitHeight(40);
		imgPl2.setFitWidth(40);
		imgPl2.setImage(img2);
		imgPl2.setCursor(Cursor.HAND);

		// Margenes para los labels
		HBox.setMargin(imgPl2, new Insets(0, 0, 0, 50));

		// Introducimos todo en el HBox y a su vez en el VBox
		hb.getChildren().addAll(imgPl1, lbl1, imgPl2, lbl2);
		vb.getChildren().addAll(hb);

		// Eventos
		lbl1.setOnMouseClicked(e -> {
			if (mc != null) {
				mc.changePl(1);
			}
			stage.close();
		});

		imgPl1.setOnMouseClicked(e -> {
			if (mc != null) {
				mc.changePl(1);
			}
			stage.close();
		});

		lbl2.setOnMouseClicked(e -> {
			if (mc != null) {
				mc.changePl(2);
			}
			stage.close();
		});

		imgPl2.setOnMouseClicked(e -> {
			if (mc != null) {
				mc.changePl(2);
			}
			stage.close();
		});

		// Generamos la escena que se mostrará
		scene = new Scene(vb, 250, 250);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		// Mostramos el stage
		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		stage.getIcons().add(icon);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Cambiar Playlist");
		stage.setScene(scene);
		stage.showAndWait();
	}
}
