import java.util.List;

public class Recital {
    private List<Artista> artistas;
    private List<Cancion> cancionesLineUp;

    public Recital(List<Artista> artistas, List<Cancion> cancionesLineUp) {
        this.artistas = artistas;
        // TODO: Calcular descuentos por compartir bandas
        this.cancionesLineUp = cancionesLineUp;
    }

    public List<Cancion> getCancionesLineUp() {
        return cancionesLineUp;
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    // eliminar artista
}
