package app.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class UtilsBD {
	public static Connection conectarBD() {
		try {

			// Definimos el driver de la BD a la que nos conectamos
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Creamos una conexión activa con BD
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/javamusic", "root", "");

			// Si no ha saltado la excepción devolvemos la conexión
			return con;

			// Capturamos
		} catch (Exception e) {
			// Cuando salta el fallo mostramos un mensaje
			System.out.println("Error al conectarse");
			e.printStackTrace();
			return null;
		}
	}
}
