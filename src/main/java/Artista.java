import java.util.*;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.AllArgsConstructor;
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

@NoArgsConstructor
@Getter
public abstract class Artista {
    protected String nombre;
    protected List<Banda> bandas;
    protected ArrayList<Rol> roles;
    protected double costoPorCancion;
    protected int maximoCancionesPorRecital;
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
        Artista artista = (Artista) o;
        return (this.nombre.equals(artista.nombre) &&
                this.roles.equals(artista.getRoles()) &&
                this.bandas.equals(artista.getBandas()) &&
                this.costoPorCancion == artista.costoPorCancion &&
                this.maximoCancionesPorRecital == artista.maximoCancionesPorRecital);
    }

    public void cargarCancion(final Cancion cancion, final Rol rol) {
    }

    public boolean puedeAgregarOtraCancion() {
        return cancioneAsignadas.size() < this.maximoCancionesPorRecital;
    }

    public boolean tieneCancionAsignada(final Cancion cancion){
        return cancioneAsignadas.containsKey(cancion);
    }
/*
    public Boolean agregarBanda(Banda banda) {
        if(this.bandas.contains(banda)) {
            return false;
        }
        this.bandas.add(banda);
        return true;
    }

    public Boolean agregarRol(Rol rol) {
        if(this.roles.contains(rol)) {
            return false;
        }
        this.roles.add(rol);
        return true;
    }
    public void setCostoPorCancion(double costoPorCancion) {
        this.costoPorCancion = costoPorCancion;
    }*/
}
