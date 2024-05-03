package app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlaylistDAO {

	/**
	 * Funcion que nos carga una playlist
	 * 
	 * @param con
	 * @param id
	 * @return
	 */
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

	/**
	 * Funcion que nos carga una playlist
	 * 
	 * @param con
	 * @param nombre
	 * @return
	 */
	public static String[] cargarPlaylist(Connection con, String nombre) {
		String[] playlist = new String[2];

		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT NOMBRE, IMG FROM PLAYLIST WHERE NOMBRE = ?;");
			pstmt.setString(1, nombre);

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

	/**
	 * Funcion que nos carga las canciones asociadas a una playlist
	 * 
	 * @param con
	 * @param id
	 * @return
	 */
	public static ArrayList<String> cancionesPlaylist(Connection con, int id) {
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

	/**
	 * Funcion que nos carga las canciones asociadas a una playlist
	 * 
	 * @param con
	 * @param nombre
	 * @return
	 */
	public static ArrayList<String> cancionesPlaylist(Connection con, String nombre) {
		ArrayList<String> songs = new ArrayList<String>();

		try {
			PreparedStatement pstmt = con.prepareStatement(
					"SELECT TITULO, DURACION FROM CANCION C JOIN TIENE T ON C.IDCANCION = T.IDCANCION JOIN PLAYLIST P ON T.IDPLAYLIST = P.IDPLAYLIST WHERE NOMBRE = ?;");
			pstmt.setString(1, nombre);

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

	/**
	 * Funcion que recibe los datos necesarios para añadir una nueva playlist con
	 * canciones
	 * 
	 * @param con
	 * @param nombre
	 * @param ruta
	 * @param idUsuario
	 * @return
	 */
	public static int agregarPlaylist(Connection con, String nombre, String ruta, int idUsuario) {
		int numInsert = 0;
		try {
			// Creamos la playlist con los datos necesarios
			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO PLAYLIST (NOMBRE, IMG, IDUSUARIO) VALUES(?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, nombre);
			pstmt.setString(2, ruta);
			pstmt.setInt(3, idUsuario);

			numInsert = pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			int idPlaylist = -1;
			if (rs.next()) {
				idPlaylist = rs.getInt(1);
			}

			// Agregamos las canciones a la playlist para que no esté vacia
			CancionDAO.agregarCancion(con, idPlaylist);

			return numInsert;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
