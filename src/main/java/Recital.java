
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Recital {
    private final List<Artista> artistas;
    private final List<Cancion> cancionesLineUp;
    private final Map<Artista, List<Cancion>> cancionesPorArtista;
    // TODO: Calcular descuentos por compartir bandas (estaba en el constructor)

    // eliminar artista

    public int rolesFaltantes(final Cancion cancion) {
        final List<Artista> artistasDisponibles = artistaPuedenTocarMas();

        final List<Rol> roles = artistasDisponibles.stream()
                .map(Artista::getRoles)
                .flatMap(List::stream)
                .toList();

        return (int) cancion.getRolesRequeridos().stream()
                .filter(rolRequerido -> !roles.contains(rolRequerido))
                .count();
    }


    public void asignacionAutomaticaDeCanciones(){
        for (Cancion cancion : cancionesLineUp) {
            for (Rol rol : cancion.getRolesRequeridos()){
                Artista artista = obtenerArtistaConRol(artistas, rol, cancion);
                if (artista != null) {
                    artista.asignarCancion(cancion);
                }
            }
        }
    }

    private Artista obtenerArtistaConRol(final List<Artista> artistas, final Rol rol, final Cancion cancion) {
        return artistas.stream()
                .filter(artista -> !artista.llegoAlMaximo())
                .filter(artista -> !artista.getCancioneAsignadas().contains(cancion))
                .filter(artista -> artista.tieneRol(rol))
                .findFirst()
                .orElse(null);
    }

    private List<Artista> artistaPuedenTocarMas(){
        List<Artista> artistasSobrantes = new ArrayList<>();

        for(Artista artista : artistas){
            if (!artista.llegoAlMaximo()){
                artistasSobrantes.add(artista);
            }
        }

        return artistasSobrantes;
    }
}
