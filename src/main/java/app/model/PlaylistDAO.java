package app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistDAO {

	public static String[] cargarPlaylist(Connection con, int id) {
		String[] playlist = new String[2];

		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT NOMBRE, IMG FROM PLAYLIST WHERE IDPLAYLIST = ?;");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			playlist[0] = rs.getString(1);
			playlist[1] = rs.getString(2);

			return playlist;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<String> cancionesPlaylist(Connection con, int id) { // CAMBIAR LABELS
		ArrayList<String> songs = new ArrayList<String>();

		try {
			PreparedStatement pstmt = con.prepareStatement(
					"SELECT TITULO, DURACION FROM CANCION C JOIN TIENE T ON C.IDCANCION = T.IDCANCION WHERE T.IDPLAYLIST = ?;");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				songs.add(rs.getString(1));
				songs.add(rs.getString(2));
			}

			return songs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
