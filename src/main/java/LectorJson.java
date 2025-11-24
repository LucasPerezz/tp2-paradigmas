import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class LectorJson {

    private static final String DIR_ARTISTA = "artistas.json";
    private static final String DIR_CANCIONES = "canciones.json";

    private List<Artista> artistas = new ArrayList<>();
    @Getter
    private List<Cancion> canciones = new ArrayList<>();

    public void cargarDatos() throws IOException {
        canciones = cargarCanciones();
        artistas = cargarArtistas();
    }

    public List<ArtistaBase> getArtistasBases() {
        final List<ArtistaBase> artistaBase = new ArrayList<>();

        for (Artista artista : artistas) {
            if (artista instanceof ArtistaBase) {
                artistaBase.add((ArtistaBase) artista);
            }
        }

        return artistaBase;
    }

    public List<ArtistaCandidato> getArtistasCandidatos() {
        final List<ArtistaCandidato> artistaCandidatos = new ArrayList<>();

        for (Artista artista : artistas) {
            if (artista instanceof ArtistaCandidato) {
                artistaCandidatos.add((ArtistaCandidato) artista);
            }
        }

        return artistaCandidatos;
    }

    private List<Cancion> cargarCanciones() throws IOException {
        final File file = new File(DIR_CANCIONES);
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Cancion>> typeRef = new TypeReference<List<Cancion>>() {
        };
        return mapper.readValue(file, typeRef);
    }

    private List<Artista> cargarArtistas() throws IOException {
        final File file = new File(DIR_ARTISTA);
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Artista>> typeRefBase = new TypeReference<List<Artista>>() {
        };

        return mapper.readValue(file, typeRefBase);
    }

}