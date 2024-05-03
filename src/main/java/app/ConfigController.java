package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigController {
	// Constante para el archivo de configuracion
	private static final String CONFIG = "config.txt";

	/**
	 * Metodo que recibe los datos que quiero almacenar en el fichero de
	 * configuracion
	 * 
	 * @param idPlaylist
	 * @param idCancion
	 * @param bailarin
	 */
	public static void guardarConfig(int idPlaylist, int idCancion, int bailarin) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG));
			// Si el id de la canción es cero, lo pongo a la cancion inicial de la playlist
			if (idCancion == 0) {
				idCancion = 1;
			}

			// Si el id del bailarin es inferior a cero, lo pongo al bailarin inicial
			if (bailarin <= 0) {
				bailarin = 0;
			} else {
				bailarin -= 1;
			}

			writer.write(idPlaylist + "\n");
			writer.write(idCancion + "\n");
			writer.write(bailarin + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que devuelve los datos almacenados en el fichero de configuracion
	 * 
	 * @return
	 */
	public static Configuracion cargarConfig() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(CONFIG));
			int idPlaylist = Integer.parseInt(reader.readLine());
			int idCancion = Integer.parseInt(reader.readLine());
			int bailarin = Integer.parseInt(reader.readLine());
			reader.close();

			return new Configuracion(idPlaylist, idCancion, bailarin);

		} catch (FileNotFoundException e) {
			// Si hay error establezco la configuracion de inicio
			return new Configuracion(1, 1, 0);
		} catch (NumberFormatException e) {
			// Si hay error establezco la configuracion de inicio
			return new Configuracion(1, 1, 0);
		} catch (IOException e) {
			// Si hay error establezco la configuracion de inicio
			return new Configuracion(1, 1, 0);
		}
	}

	// Clase configuracion con su constructor, set y get
	public static class Configuracion {
		private int idPlaylist;
		private int idCancion;
		private int bailarin;

		public Configuracion(int idPlaylist, int idCancion, int bailarin) {
			this.idPlaylist = idPlaylist;
			this.idCancion = idCancion;
			this.bailarin = bailarin;
		}

		public int getIdPlaylist() {
			return idPlaylist;
		}

		public int getIdCancion() {
			return idCancion;
		}

		public int getBailarin() {
			return bailarin;
		}
	}
}
