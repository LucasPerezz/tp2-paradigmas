import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Cancion {
    private String nombre;
    private List<Rol> rolesRequeridos;
    private Set<Rol> rolesCubiertos;

    public Cancion(final String nombre, final List<Rol> rolesRequeridos) {
        this.nombre = nombre;
        this.rolesRequeridos = rolesRequeridos;
        rolesCubiertos = new HashSet<>();
    }

    public void agregarRolCubierto(final Rol rol) {
        rolesCubiertos.add(rol);
    }

    public Set<Rol> getRolesFaltantes() {
        return rolesRequeridos.stream()
                .filter(rol -> !rolesCubiertos.contains(rol))
                .collect(Collectors.toSet());
    }

    public Set<Rol> rolesFaltantes(final Set<Rol> roles) {
        return rolesRequeridos.stream()
                .filter(rol -> !roles.contains(rol))
                .collect(Collectors.toSet());
    }

}
