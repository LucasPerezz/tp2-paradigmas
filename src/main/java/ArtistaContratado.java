import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtistaContratado extends Artista {
    private Set<Cancion> cancionesAsignadas;

    private ArtistaContratado(final String nombre, List<Banda> bandas, List<Rol> roles, double costoPorCancion, int maximoCancionesPorRecital) {
        super(nombre, bandas, new ArrayList<>(roles), costoPorCancion, maximoCancionesPorRecital);
    }

    @Override
    public boolean llegoAlMaximo(final List<Cancion> cancionesAsignadas) {
        return cancionesAsignadas.size() == maximoCancionesPorRecital;
    }

    public double calcularCostoDeContratacion(final List<Cancion> cancionesAsignadas) {
        return  cancionesAsignadas.size() * costoPorCancion;
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

    public void asignarCancion(final Cancion cancion) {
        cancionesAsignadas.add(cancion);
    }

    @Override
    public String toString(){
        final String bandasString = bandas.stream()
                .map(Banda::toString)
                .collect(Collectors.joining(", "));

        final String rolesString = roles.stream()
                .map(Rol::toString)
                .collect(Collectors.joining(", "));

        return String.format(
                "Nombre: %s, Bandas: %s, Roles: %s, consto contratado por cancion: %s, maximo dispuesto a tocar: %s",
                this.nombre, bandasString, rolesString, this.costoPorCancion
        );
    }
}
