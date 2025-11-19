import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AsignacionUtilidades {

    public static void vincularDatos(List<Artista> todosLosArtistas, List<Cancion> todasLasCanciones) {
        Map<String, Artista> mapaArtistas = todosLosArtistas.stream()
                .collect(Collectors.toMap(Artista::getNombre, Function.identity()));

        for (Cancion cancion : todasLasCanciones) {
            List<Rol> rolesRequeridos = cancion.getRolesRequeridos();
            List<AsignacionArtista> asignacionesJSON = cancion.getAsignaciones();

            // Crear una lista de artistas en el mismo orden que rolesRequeridos
            List<Artista> artistasVinculados = new ArrayList<>();

            // Mantener track de qué asignaciones ya fueron usadas
            Set<Integer> asignacionesUsadas = new HashSet<>();

            // Para cada rol requerido, buscar si hay una asignación
            for (int i = 0; i < rolesRequeridos.size(); i++) {
                Rol rolActual = rolesRequeridos.get(i);

                // Buscar en las asignaciones del JSON una que coincida con este rol
                // y que aún no haya sido usada
                AsignacionArtista asignacionEncontrada = null;
                int indiceAsignacion = -1;

                for (int j = 0; j < asignacionesJSON.size(); j++) {
                    AsignacionArtista asignacion = asignacionesJSON.get(j);
                    if (asignacion.getRolAsignado().equals(rolActual) && !asignacionesUsadas.contains(j)) {
                        asignacionEncontrada = asignacion;
                        indiceAsignacion = j;
                        break;
                    }
                }

                if (asignacionEncontrada != null) {
                    asignacionesUsadas.add(indiceAsignacion); // Marcar como usada
                    String nombreArtista = asignacionEncontrada.getNombreArtista();
                    Artista artista = mapaArtistas.get(nombreArtista);

                    if (artista != null) {
                        artistasVinculados.add(artista);
                        asignacionEncontrada.setArtistaReferencia(artista);
                    } else {
                        System.err.println("Advertencia: Artista " + nombreArtista +
                                " no encontrado para rol " + rolActual + " en canción " + cancion.getNombre());
                        artistasVinculados.add(null); // Rol sin cubrir
                    }
                } else {
                    // Rol requerido sin asignación
                    System.out.println("Rol " + rolActual + " sin asignar en canción: " + cancion.getNombre());
                    artistasVinculados.add(null); // Rol sin cubrir
                }
            }

            // Establecer la lista de artistas vinculados manteniendo el orden paralelo
            cancion.setArtistasAsignados(artistasVinculados);
        }
    }
}
