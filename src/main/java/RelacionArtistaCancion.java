import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RelacionArtistaCancion {
    private final Artista artista;
    private final Cancion cancion;
    private final Rol rolQueCumple;
}
