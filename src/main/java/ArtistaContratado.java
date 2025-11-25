import java.util.*;

public class ArtistaContratado extends Artista {

    private ArtistaContratado(final String nombre, List<Banda> bandas, List<Rol> roles, double costoPorCancion, int maximoCancionesPorRecital) {
        super(nombre, bandas, new ArrayList<>(roles), costoPorCancion, maximoCancionesPorRecital);
    }

    @Override
    public boolean llegoAlMaximo(final List<Cancion> cancionesAsignadas) {
        return cancionesAsignadas.size() == maximoCancionesPorRecital;
    }

    public static ArtistaContratado contratar(final ArtistaCandidato artistaCandidato, final double costoPorCancionAcordado) {
        return new ArtistaContratado(
                artistaCandidato.getNombre(),
                artistaCandidato.getBandas(),
                artistaCandidato.getRoles(),
                costoPorCancionAcordado,
                artistaCandidato.getMaximoCancionesPorRecital()
        );
    }

    @Override
    public void cargarCancion(final Cancion cancion, final Rol rol) {
        if (cancioneAsignadas.containsKey(cancion)) {
            cancioneAsignadas.put(cancion, new HashSet<>());
        }
        cancioneAsignadas.get(cancion).add(rol);
    }

    public int getCancionesMaximas() {
        return maximoCancionesPorRecital;
    }

    @Override
    public String toString() {

        List<String> lineas = new ArrayList<>();

        // Título
        lineas.add("Artista contratado: " + this.getNombre());

        // Estado
        lineas.add("Costo por canción: " + this.getCostoPorCancion());
        lineas.add("Canciones maximas: " + this.getCancionesMaximas());

        // Bandas
        lineas.add("Bandas: ");
        for (int i = 0; i < getBandas().size(); i++) {
            Banda banda = getBandas().get(i);
            lineas.add("  " + banda.getNombre());
        }

        // Roles
        lineas.add("Roles: ");
        for (int i = 0; i < getRoles().size(); i++) {
            Rol rol = getRoles().get(i);
            lineas.add("  " + rol);
        }

        //   Calcular ancho máximo
        int max = 54;
        for (String l : lineas) {
            if (l.length() > max) max = l.length();
        }

        int anchoInterior = max + 2; // margen interno
        StringBuilder sb = new StringBuilder();

        // Borde superior
        sb.append("┌").append("─".repeat(anchoInterior)).append("┐\n");

        // Contenido
        for (String l : lineas) {
            int padding = anchoInterior - l.length();
            sb.append("│ ").append(l).append(" ".repeat(padding - 1)).append("│\n");
        }

        // Borde inferior
        sb.append("└").append("─".repeat(anchoInterior)).append("┘");

        return sb.toString();
    }
}
