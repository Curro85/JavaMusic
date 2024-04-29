package app.model;

public class CancionDO {

	// Atributos
	private int idCancion;
	private String ruta;
	private String titulo;
	private String genero;
	private String duracion;
	private String album;
	private String caratula;

	// Constructor
	public CancionDO(int idCancion, String ruta, String titulo, String genero, String duracion) {
		this.idCancion = idCancion;
		this.ruta = ruta;
		this.titulo = titulo;
		this.genero = genero;
		this.duracion = duracion;
	}

	// Get & Set
	public int getIdCancion() {
		return idCancion;
	}

	public void setIdCancion(int idCancion) {
		this.idCancion = idCancion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	// toString
	@Override
	public String toString() {
		return "cancionDO [idCancion=" + idCancion + ", ruta=" + ruta + ", titulo=" + titulo + ", genero=" + genero
				+ ", duracion=" + duracion + "]";
	}

}
