package app.panels;

import java.sql.Connection;

import app.model.PlaylistDAO;
import app.utils.UtilsBD;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BorrarPlaylistPanel extends Application {
	// Conexion con BD
	Connection con = UtilsBD.conectarBD();

	private static Scene scene;

	public void start(Stage stage) {
		// VBox Principal
		VBox vb = new VBox();
		vb.setAlignment(Pos.TOP_CENTER);

		// Labels de las playlist
		Label lbl1 = new Label();
		lbl1.setText(PlaylistDAO.cargarPlaylist(con, 1)[0]);
		lbl1.getStyleClass().add("label-cancion");

		Label lbl2 = new Label();
		lbl2.setText(PlaylistDAO.cargarPlaylist(con, 2)[0]);
		lbl2.getStyleClass().add("label-cancion");

		// Eventos respecto a las labels
		lbl1.setOnMouseClicked(e -> {
			stage.close();
		});

		lbl2.setOnMouseClicked(e -> {
			stage.close();
		});

		// Margenes para organizar la escena
		VBox.setMargin(lbl1, new Insets(20, 0, 0, 0));
		VBox.setMargin(lbl2, new Insets(10, 0, 0, 0));

		// Añadimos todo al VBox
		vb.getChildren().addAll(lbl1, lbl2);

		// Creamos la escena
		scene = new Scene(vb, 250, 250);
		scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
		scene.getRoot().getStyleClass().add("body_background");

		// Mostramos el stage
		Image icon = new Image(getClass().getResourceAsStream("/img/carlton.png"));
		stage.getIcons().add(icon);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Borrar Playlist");
		stage.setScene(scene);
		stage.showAndWait();
	}
}
