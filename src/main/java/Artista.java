import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
// 2. Mapear cada subclase al valor que tendrá su campo "tipo" en el JSON.
@JsonSubTypes({
        @JsonSubTypes.Type(value = ArtistaBase.class, name = "BASE"),
        @JsonSubTypes.Type(value = ArtistaCandidato.class, name = "CANDIDATO")
})

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
public abstract class Artista {
    protected String nombre;
    protected List<Banda> bandas;
    protected List<Rol> roles;
    protected double costoPorCancion;
    protected int maximoCancionesPorRecital;

    @JsonIgnore
    protected Map<Cancion, Set<Rol>> cancioneAsignadas;

    public Artista(final String nombre,
                   final List<Banda> bandas,
                   final ArrayList<Rol> roles,
                   final double costoPorCancion,
                   final int maximoCancionesPorRecital) {
        this.nombre = nombre;
        this.bandas = bandas;
        this.roles = roles;
        this.costoPorCancion = costoPorCancion;
        this.maximoCancionesPorRecital = maximoCancionesPorRecital;
        this.cancioneAsignadas = new HashMap<>();
    }

    /*   public Artista(String nombre, double costoPorCancion) {
           this.nombre = nombre;
           this.costoPorCancion = costoPorCancion;
           this.bandas = new ArrayList<>();
           this.roles = new ArrayList<>();
       }
   */
    public boolean tieneRol(final Rol rol) {
        return roles.contains(rol);
    }

    public abstract boolean llegoAlMaximo(final List<Cancion> cancionesAsignadas);

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Artista)) return false;
        Artista artista = (Artista) o;

        return Objects.equals(nombre, artista.nombre) &&
                Objects.equals(roles, artista.roles) &&
                Objects.equals(bandas, artista.bandas) &&
                maximoCancionesPorRecital == artista.maximoCancionesPorRecital;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, roles, bandas, maximoCancionesPorRecital);
    }

    public void cargarCancion(final Cancion cancion, final Rol rol) {
    }

    public boolean puedeAgregarOtraCancion() {
        return cancioneAsignadas.size() < this.maximoCancionesPorRecital;
    }

    public boolean tieneCancionAsignada(final Cancion cancion){
        return cancioneAsignadas.containsKey(cancion);
    }
}
