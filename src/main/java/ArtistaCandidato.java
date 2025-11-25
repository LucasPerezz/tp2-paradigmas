import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ArtistaCandidato extends Artista {

    @JsonCreator
    public ArtistaCandidato(
            @JsonProperty("nombre") final String nombre,
            @JsonProperty("bandas") List<Banda> bandas,
            @JsonProperty("roles") ArrayList<Rol> roles,
            @JsonProperty("costoPorCancion") double costoPorCancion,
            @JsonProperty(value = "maximoCancionesPorRecital", required = false) Integer maximoCancionesPorRecital) {
        super(nombre, bandas, new ArrayList<>(roles), costoPorCancion, 
              maximoCancionesPorRecital != null ? maximoCancionesPorRecital : 3);
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

    public void entrenar(final Rol rol) {
        if (roles.stream().anyMatch(r -> r.equals(rol))) {
            throw new RuntimeException("El artista " + nombre + " ya cumple con el rol " + rol);
        }

        roles.add(rol);
        costoPorCancion = costoPorCancion * 1.5;
    }

}
