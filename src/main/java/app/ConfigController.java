package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigController {
	private static final String CONFIG = "config.txt";

	public static void guardarConfig(int idPlaylist, int idCancion, int bailarin) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG));
			writer.write(idPlaylist + "\n");
			writer.write(idCancion + "\n");
			writer.write(bailarin + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
