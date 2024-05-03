package app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CancionDAO {

	public static int agregarCancion(Connection con, int idPlaylist) {
		int agregadas = 0;
		ArrayList<CancionDO> temp = listarCanciones(con);
		try {
			for (int i = 0; i < temp.size(); i++) {
				PreparedStatement pstmt = con
						.prepareStatement("INSERT INTO TIENE (IDCANCION, IDPLAYLIST, IDUSUARIO) VALUES(?, ?, ?)");
				pstmt.setInt(1, temp.get(i).getIdCancion());
				pstmt.setInt(2, idPlaylist);
				pstmt.setInt(3, 1);

				agregadas += pstmt.executeUpdate();
			}

			return agregadas;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	public static String cargarCancion(Connection con, int id) {
		String ruta = "";

		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT RUTA FROM CANCION WHERE IDCANCION = ?;");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			ruta = rs.getString("ruta");

		} catch (SQLException e) {
			e.printStackTrace();
			return "Canción no encontrada";
		}
		return ruta;
	}

	public static ArrayList<CancionDO> listarCanciones(Connection con) {
		ArrayList<CancionDO> songs = new ArrayList<CancionDO>();
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM CANCION");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CancionDO temp = new CancionDO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				songs.add(temp);
			}
			return songs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int reproducirCancion(Connection con, String nombre) {
		int id = 0;
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT IDCANCION FROM CANCION WHERE TITULO LIKE ?;");
			pstmt.setString(1, "%" + nombre + "%");

			ResultSet rs = pstmt.executeQuery();
			rs.next();

			id = rs.getInt(1);

			return id;
		} catch (SQLException e) {
			return 0;
		}

	}
}
