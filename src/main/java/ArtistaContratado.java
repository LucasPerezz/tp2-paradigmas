import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class ArtistaContratado extends Artista {

    private ArtistaContratado(final String nombre, List<Banda> bandas, List<Rol> roles, double costoPorCancion, int maximoCancionesPorRecital) {
        super(nombre, bandas, new ArrayList<>(roles), costoPorCancion, maximoCancionesPorRecital);
    }

    @Override
    public boolean llegoAlMaximo(List<Cancion> cancionesAsignadas) {
        return cancionesAsignadas.size() == maximoCancionesPorRecital;
    }

    public static ArtistaContratado contratar(final ArtistaCandidato artistaCandidato, final double costoPorCancionAcordado){
        return new ArtistaContratado(
                artistaCandidato.getNombre(),
                artistaCandidato.getBandas(),
                artistaCandidato.getRoles(),
                costoPorCancionAcordado,
                artistaCandidato.getMaximoCancionesPorRecital()
        );
    }
}
