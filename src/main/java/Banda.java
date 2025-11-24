import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Banda {
    public static final Object GUNS = null;
    private String nombre;

    @JsonCreator
    public Banda(@JsonProperty("nombre") String nombre) {
        this.nombre = nombre;
    }
}
