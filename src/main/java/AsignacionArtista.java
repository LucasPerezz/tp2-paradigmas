public class AsignacionArtista {
    private String nombreArtista; // Usado como ID para la vinculación
    private Rol rolAsignado;
    // Campo transitorio para la vinculación en Java
    private Artista artistaReferencia;

    // Constructor, Getters y Setters necesarios para Jackson...

    public AsignacionArtista(String nombreArtista, Rol rol, Artista artistaReferencia) {
        this.nombreArtista = nombreArtista;
        this.rolAsignado = rol;
        this.artistaReferencia = artistaReferencia;
    }

    public Rol getRolAsignado() {
        return rolAsignado;
    }

    public void setRolAsignado(Rol rolAsignado) {
        this.rolAsignado = rolAsignado;
    }

    public AsignacionArtista() {
    }

    public Artista getArtistaReferencia() {
        return artistaReferencia;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setArtistaReferencia(Artista artista) {
        this.artistaReferencia = artista;
    }
}