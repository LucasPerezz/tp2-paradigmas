import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

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

public abstract class Artista {
    private String nombre;
    private List<Banda> bandas;
    private List<Rol> roles;
    private double costoPorCancion;

    public Artista() {
    }

    public Artista(String nombre, double costoPorCancion) {
        this.nombre = nombre;
        this.costoPorCancion = costoPorCancion;
        this.bandas = new ArrayList<>();
        this.roles = new ArrayList<>();
    }

    public List<Banda> getBandas() {
        return bandas;
    }

    public List<Rol> getRoles() {
        return roles;
    }

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

    public double getCostoPorCancion() {
        return costoPorCancion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCostoPorCancion(double costoPorCancion) {
        this.costoPorCancion = costoPorCancion;
    }
}
