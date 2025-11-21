
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
                Artista artista = obtenerArtistaConRol(rol, cancion);
                if(artista == null){
                    continue;
                }

                if (!cancionesPorArtista.containsKey(artista)){
                    List<Cancion> cancionesAsiganadas = new ArrayList<>();
                    cancionesAsiganadas.add(cancion);
                    cancionesPorArtista.put(artista, cancionesAsiganadas);
                }

                cancionesPorArtista.get(artista).add(cancion);
            }
        }
    }

    private Artista obtenerArtistaConRol(final Rol rol, final Cancion cancion) {
        return artistas.stream()
                .filter(artista -> artista.tieneRol(rol))
                .filter(artista -> !artista.llegoAlMaximo(cancionesPorArtista.getOrDefault(artista, List.of())))
                .filter(artista -> !cancionesPorArtista.getOrDefault(artista, List.of()).contains(cancion))
                .findFirst()
                .orElse(null);
    }

    private List<Artista> artistaPuedenTocarMas(){
        List<Artista> artistasSobrantes = new ArrayList<>();

        for(Artista artista : artistas){
            if (!artista.llegoAlMaximo(cancionesPorArtista.getOrDefault(artista, List.of()))){
                artistasSobrantes.add(artista);
            }
        }

        return artistasSobrantes;
    }
}
