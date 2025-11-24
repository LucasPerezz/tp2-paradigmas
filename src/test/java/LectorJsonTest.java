import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LectorJsonTest {

    private LectorJson lector;

    @Before
    public void setUp() {
        lector = new LectorJson();
    }

    @Test
    public void cargarCancionesTest() throws IOException {
        lector.cargarDatos();
        List<Cancion> canciones = lector.getCanciones();

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
    public void getArtistasBase() throws IOException {
        lector.cargarDatos();

        List<ArtistaBase> bases = lector.getArtistasBases();
        
        // Recrear los 3 ArtistasBase del archivo artistas.json
        ArtistaBase johnnyMarr = new ArtistaBase(
            "Johnny Marr",
            List.of(new Banda("The Smiths")),
            new ArrayList<>(List.of(Rol.GUITARRISTA)),
            1000.00,
            10
        );
        
        ArtistaBase grahamCoxon = new ArtistaBase(
            "Graham Coxon",
            List.of(new Banda("Blur")),
            new ArrayList<>(List.of(Rol.GUITARRISTA)),
            900.00,
            3
        );
        
        ArtistaBase noelGallagher = new ArtistaBase(
            "Noel Gallagher",
            List.of(new Banda("Oasis")),
            new ArrayList<>(List.of(Rol.GUITARRISTA)),
            1500.00,
            7
        );

        assertNotNull(bases);
        assertFalse(bases.isEmpty());
        assertEquals(3, bases.size());
        assertTrue(bases.contains(johnnyMarr));
        assertTrue(bases.contains(grahamCoxon));
        assertTrue(bases.contains(noelGallagher));

    }

    @Test
    public void getArtistasCandidatos() throws IOException {
        lector.cargarDatos();

        List<ArtistaCandidato> candidatos = lector.getArtistasCandidatos();
        
        // Recrear los 3 ArtistasCandidato del archivo artistas.json
        ArtistaCandidato morrisey = new ArtistaCandidato(
            "Morrisey",
            List.of(new Banda("The Smiths")),
            new ArrayList<>(List.of(Rol.VOCALISTA)),
            1000.00,
            3
        );
        
        ArtistaCandidato damonAlbarn = new ArtistaCandidato(
            "Damon Albarn",
            List.of(new Banda("Blur")),
            new ArrayList<>(List.of(Rol.VOCALISTA)),
            750.00,
            3
        );
        
        ArtistaCandidato liamGallagher = new ArtistaCandidato(
            "Liam Gallagher",
            List.of(new Banda("Oasis")),
            new ArrayList<>(List.of(Rol.VOCALISTA)),
            1500.00,
            3
        );

        assertNotNull(candidatos);
        assertFalse(candidatos.isEmpty());
        assertEquals(3, candidatos.size());
        assertTrue(candidatos.contains(morrisey));
        assertTrue(candidatos.contains(damonAlbarn));
        assertTrue(candidatos.contains(liamGallagher));

    }
}

