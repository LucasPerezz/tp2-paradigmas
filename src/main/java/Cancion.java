import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties
public class Cancion {
    private String nombre;
    private List<Rol> rolesRequeridos;
    private List<Artista> artistasAsignados;  // Índice paralelo a rolesRequeridos
    private List<AsignacionArtista> asignaciones;
    // private List<HashMap<Rol, Artista>>??

    public Cancion() {
    }

    public Cancion(String nombre, List<Rol> rolesRequeridos) {
        this.nombre = nombre;
        this.rolesRequeridos = rolesRequeridos;
        this.asignaciones = new ArrayList<>(Collections.nCopies(rolesRequeridos.size(), null));
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
        double total = 0;
        for (Artista a : this.getArtistasAsignados()) {
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
        return !asignaciones.contains(null) && asignaciones.size() == rolesRequeridos.size();
    }

    public double getCostoTotal() {
        double total = 0;
        for (Artista a : this.getArtistasAsignados()) {
            if (a != null) total += a.getCostoPorCancion();
        }
        return total;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Rol> getRolesRequeridos() { return rolesRequeridos; }
    public List<Artista> getArtistasAsignados() { return artistasAsignados; }
    public void setArtistasAsignados(List<Artista> artistasAsignados) {
        this.artistasAsignados = artistasAsignados;
    }

    public List<AsignacionArtista> getAsignaciones() {
        return asignaciones;
    }

    public void getRolesFaltantes() {
        HashMap<Rol, Integer> rolesCubiertos = new HashMap<>();

        for(int i = 0; i < rolesRequeridos.size(); i++) {
            Rol rol = rolesRequeridos.get(i);
            int cantidad = (this.artistasAsignados.get(i) == null) ? 0 : 1;
            rolesCubiertos.put(rol, rolesCubiertos.getOrDefault(rol, 0) + cantidad);

        }
        for (Rol rol: rolesCubiertos.keySet()) {
            int ocurrencias = Collections.frequency(rolesRequeridos, rol);
            int totales = rolesCubiertos.get(rol);
            System.out.println("═════════════ " + rol + " ═════════════");
            System.out.println("Necesitados: " + ocurrencias);
            System.out.println("Cubiertos: " + totales);
            System.out.println("> Faltantes: " + (ocurrencias - totales));
        }
    }
}
