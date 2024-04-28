package app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CancionDAO {

	public static ArrayList<CancionDO> songs = new ArrayList<CancionDO>();

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
}
