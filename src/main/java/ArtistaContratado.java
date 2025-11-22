import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ArtistaContratado extends Artista {
    private int maximoCancionesPorRecital;

    public boolean llegoAlMaximo(final List<Cancion> cancionesAsignadas){
        return cancionesAsignadas.size() == maximoCancionesPorRecital;
    }
}
