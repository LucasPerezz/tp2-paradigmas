import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cancion {
    private String nombre;
    private List<Rol> rolesRequeridos;
    private List<Artista> artistasAsignados;
    // private List<HashMap<Rol, Artista>>??

    public Cancion(String nombre, List<Rol> rolesRequeridos) {
        this.nombre = nombre;
        this.rolesRequeridos = rolesRequeridos;
        this.artistasAsignados = new ArrayList<>(Collections.nCopies(rolesRequeridos.size(), null));
        System.out.println(rolesRequeridos.size());
    }

    public Boolean asignarArtista(Rol rol, Artista artista) {
        if(this.artistasAsignados.contains(artista)) {
            return false;
        }

        // TODO: Checkear el maximo de canciones del artista
        if(!this.rolesRequeridos.contains(rol)) {
            return false;
        }

        this.artistasAsignados.set(this.rolesRequeridos.indexOf(rol), artista);

        return true;
    }

    @Override
    public String toString() {

        List<String> lineas = new ArrayList<>();

        // Título
        lineas.add("Canción: " + nombre);

        // Estado
        lineas.add("Estado: " + (estaCubierta() ? "Cubierta" : "No cubierta"));

        // Roles y artistas
        for (int i = 0; i < rolesRequeridos.size(); i++) {
            Rol rol = rolesRequeridos.get(i);
            Artista artista = (i < artistasAsignados.size() ? artistasAsignados.get(i) : null);

            String artistaNombre = (artista != null ? artista.getNombre() : "No cubierto");
            lineas.add(rol + " -> " + artistaNombre);
        }

        // Costo total
        int total = 0; for (Artista a : artistasAsignados) {
            if (a != null)
                total += a.getCostoPorCancion();
        }
        lineas.add("Costo total: $" + total);

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


    public boolean estaCubierta() {
        return !artistasAsignados.contains(null) && artistasAsignados.size() == rolesRequeridos.size();
    }

    public int getCostoTotal() {
        int total = 0;
        for (Artista a : artistasAsignados) {
            if (a != null) total += a.getCostoPorCancion();
        }
        return total;
    }


}
