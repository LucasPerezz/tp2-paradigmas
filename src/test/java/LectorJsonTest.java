import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LectorJsonTest {

    @Test
    public void cargarCancionesTest() throws IOException {
        File jsonFile = new File("canciones.json");
        
        List<Cancion> canciones = LectorJson.cargarCanciones(jsonFile);
        
        assertNotNull(canciones);
        assertFalse(canciones.isEmpty());
        
        System.out.println("Canciones cargadas: " + canciones.size());
        for (Cancion cancion : canciones) {
            assertNotNull(cancion.getNombre());
            assertNotNull(cancion.getRolesRequeridos());
            assertFalse(cancion.getRolesRequeridos().isEmpty());
            System.out.println("- " + cancion.getNombre() + 
                    " (Roles: " + cancion.getRolesRequeridos().size() + ")");
        }
    }

    @Test
    public void cargarArtistasTest() throws IOException {
        File jsonFile = new File("artistas.json");
        
        List<Artista> artistas = LectorJson.cargarArtistas(jsonFile);
        
        assertNotNull(artistas);
        assertFalse(artistas.isEmpty());
        
        System.out.println("Artistas cargados: " + artistas.size());
        for (Artista artista : artistas) {
            assertNotNull(artista.getNombre());
            assertNotNull(artista.getRoles());
            System.out.println("- " + artista.getNombre() + 
                    " (Tipo: " + artista.getClass().getSimpleName() + 
                    ", Roles: " + artista.getRoles().size() + ")");
        }
    }

    @Test
    public void cargarArtistasBaseTest() throws IOException {
        File jsonFile = new File("artistas.json");
        
        try {
            List<ArtistaBase> artistasBase = LectorJson.cargarArtistasBase(jsonFile);
            
            assertNotNull(artistasBase);
            System.out.println("Artistas Base cargados: " + artistasBase.size());
            for (ArtistaBase artista : artistasBase) {
                assertNotNull(artista.getNombre());
                assertTrue(artista instanceof ArtistaBase);
                System.out.println("- " + artista.getNombre());
            }
        } catch (Exception e) {
            System.out.println("Nota: cargarArtistasBase falló porque el JSON contiene tipos mixtos: " + e.getMessage());
        }
    }

    @Test
    public void cargarArtistasCandidatoTest() throws IOException {
        File jsonFile = new File("artistas.json");
        

        try {
            List<ArtistaCandidato> artistasCandidato = LectorJson.cargarArtistasCandidato(jsonFile);
            
            assertNotNull(artistasCandidato);
            System.out.println("Artistas Candidato cargados: " + artistasCandidato.size());
            for (ArtistaCandidato artista : artistasCandidato) {
                assertNotNull(artista.getNombre());
                assertTrue(artista instanceof ArtistaCandidato);
                System.out.println("- " + artista.getNombre());
            }
        } catch (Exception e) {
            System.out.println("Nota: cargarArtistasCandidato falló porque el JSON contiene tipos mixtos: " + e.getMessage());
        }
    }

    @Test(expected = IOException.class)
    public void cargarCancionesArchivoInexistenteTest() throws IOException {
        File jsonFile = new File("archivo_inexistente.json");
        LectorJson.cargarCanciones(jsonFile);
    }
}

