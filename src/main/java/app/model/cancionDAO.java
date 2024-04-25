package app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cancionDAO {

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
}
