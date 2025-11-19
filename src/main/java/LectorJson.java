import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LectorJson {

    public static void main(String[] args) {
        // Asegúrate de que el archivo 'canciones.json' esté en la raíz del proyecto 
        // o ajusta la ruta.
        File jsonFile = new File("canciones.json");

        try {
            List<Cancion> cancionesCargadas = cargarCanciones(jsonFile);

            System.out.println("Canciones cargadas con éxito:");
            for (Cancion cancion : cancionesCargadas) {
                System.out.println("- Nombre: " + cancion.getNombre() +
                        ", Roles requeridos: " + cancion.getRolesRequeridos().size());
            }

        } catch (IOException e) {
            System.err.println("Error al cargar o parsear el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga una lista de objetos Cancion desde un archivo JSON.
     * @param file El archivo JSON.
     * @return Una lista de objetos Cancion.
     * @throws IOException Si hay un error de lectura o parsing.
     */
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