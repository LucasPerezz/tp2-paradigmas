import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RecitalTest {
    private Cancion cancion;
    private List<Artista> artistas = new ArrayList<>();
    private Recital recital;
    private Artista pepe1;
    private Artista pepe2;
    private Artista pepe3;

    @Before
    public void setUp() {
        cancion = new Cancion("Crimen", List.of(Rol.VOCALISTA, Rol.GUITARRISTA));

        pepe1 = new ArtistaBase("Pepe1", List.of(), List.of(Rol.BAJISTA),10.0, 3);
        pepe2 = new ArtistaBase("Pepe2", List.of(), List.of(Rol.VOCALISTA),5.0, 1);
        pepe3 = new ArtistaBase("Pepe3", List.of(), List.of(Rol.GUITARRISTA),10.0, 3);
        
        artistas.add(pepe1);
        artistas.add(pepe2);
        artistas.add(pepe3);
        recital = new Recital(artistas, List.of(cancion));

        recital.asignacionAutomaticaDeCanciones();
    }

    @Test
    public void asignacionDeCancionesTest(){
        assertNull(recital.getCancionesPorArtista().get(pepe1));
        assertEquals("Crimen", recital.getCancionesPorArtista().get(pepe2).get(0).getNombre());
        assertEquals("Crimen", recital.getCancionesPorArtista().get(pepe3).get(0).getNombre());
    }

    @Test
    public void rolesFaltantesTest() {
        final Cancion cancionNueva = new Cancion("Magia veneno", List.of(Rol.VOCALISTA, Rol.GUITARRISTA, Rol.BATERISTA, Rol.BAJISTA));
        final int falta = recital.rolesFaltantes(cancionNueva);
        assertEquals(2, falta);
    }
}
