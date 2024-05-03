package app.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
	public static int registrarUsuario(Connection con, String nombre, String email, String contrasenia) {
		try {
			PreparedStatement pstmt = con
					.prepareStatement("INSERT INTO USUARIO (NOMBRE, EMAIL, CONTRASENIA) VALUES (?, ?, ?)");
			pstmt.setString(1, nombre);
			pstmt.setString(2, email);
			pstmt.setString(3, contrasenia);

			int reg = pstmt.executeUpdate();
			return reg;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}
}
