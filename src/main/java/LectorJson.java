import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LectorJson {

    public static List<Cancion> cargarCanciones(File file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Cancion>> typeRef = new TypeReference<List<Cancion>>() {};
        return mapper.readValue(file, typeRef);
    }

    public static List<ArtistaBase> cargarArtistasBase(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ArtistaBase>> typeRefBase = new TypeReference<List<ArtistaBase>>() {};

        return mapper.readValue(file, typeRefBase);
    }

    public static List<ArtistaCandidato> cargarArtistasCandidato(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ArtistaCandidato>> typeRefBase = new TypeReference<List<ArtistaCandidato>>() {};

        return mapper.readValue(file, typeRefBase);
    }

    public static List<Artista> cargarArtistas(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Artista>> typeRefBase = new TypeReference<List<Artista>>() {};

        return mapper.readValue(file, typeRefBase);
    }

}