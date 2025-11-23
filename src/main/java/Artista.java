import java.util.List;
import java.util.Set;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class Artista {
    protected String nombre;
    protected List<Banda> bandas;
    protected List<Rol> roles;
    protected double costoPorCancion;
    protected int maximoCancionesPorRecital;

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
