
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtistaCandidato extends Artista {
    @Getter
    int costoContratacion;
    public ArtistaCandidato(final String nombre, List<Banda> bandas, List<Rol> roles, double costoPorCancion, int maximoCancionesPorRecital) {
        super(nombre, bandas, roles, costoPorCancion, maximoCancionesPorRecital);
    }

    public boolean llegoAlMaximo(final List<Cancion> cancionesAsignadas) {
        return cancionesAsignadas.size() == maximoCancionesPorRecital;
    }

    public double calcularCosto(final Set<ArtistaBase> artistasBases) {
        final Set<Banda> bandasDeLosBases = artistasBases.stream()
                .map(Artista::getBandas)
                .flatMap(List::stream)
                .collect(Collectors.toSet());

        boolean coincidencia = false;

        for (int i = 0; i < bandas.size() && !coincidencia; i++) {
            if (bandasDeLosBases.contains(bandas.get(i))) {
                coincidencia = true;
            }
        }


        return coincidencia ? costoPorCancion * 0.5 : costoPorCancion;
    }

   /* private Double costoContratacion;
    private List<Rol> rolesEntrenados;

    public ArtistaCandidato() {

    }

    public ArtistaCandidato(String nombre, Double costoPorCancion, Double costoContratacion )
    {
        super(nombre, costoPorCancion);
        this.costoContratacion = costoContratacion;
        this.rolesEntrenados = new ArrayList<Rol>();
    }

    public Double getCostoContratacion() {
        return costoContratacion;
    }

    public List<Rol> getRolesEntrenados() {
        return this.rolesEntrenados;
    }

    public void setCostoContratacion(Double costoContratacion) {
        this.costoContratacion = costoContratacion;
    }

    @Override
    public String toString() {

        List<String> lineas = new ArrayList<>();

        // Título
        lineas.add("Artista candidato: " + this.getNombre());

        // Estado
        lineas.add("Costo por contratación: " + this.getCostoContratacion());
        lineas.add("Costo por canción: " + this.getCostoPorCancion());

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

        for (int i = 0; i < getRolesEntrenados().size(); i++) {
            Rol rol = getRolesEntrenados().get(i);
            lineas.add("  " + rol + " (Entrenado)");
        }


        // ----------------------------------------------------
        //   Calcular ancho máximo
        // ----------------------------------------------------
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
    */
    // entrenar
    public Boolean entrenarArtista(Rol nuevoRol) {
        if(!getRoles().contains(nuevoRol)) {
            this.getRoles().add(nuevoRol);
            this.costoContratacion *= 1.5;
            return true;
        }
        return false;
    }
}
