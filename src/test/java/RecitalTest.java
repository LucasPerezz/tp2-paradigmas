import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Set;

import static org.junit.Assert.*;

public class RecitalTest {
    private Cancion cancion;
    private List<ArtistaBase> artistas = new ArrayList<>();
    private Recital recital;
    private ArtistaBase pepe1;
    private ArtistaBase pepe2;
    private ArtistaBase pepe3;

    @Before
    public void setUp() {
        cancion = new Cancion("Crimen", List.of(Rol.VOCALISTA, Rol.GUITARRISTA, Rol.PIANISTA));

        pepe1 = new ArtistaBase("Pepe1", List.of(), new ArrayList<>(List.of(Rol.BAJISTA)), 10.0, 3);
        pepe2 = new ArtistaBase("Pepe2", List.of(Banda.GUNS), new ArrayList<>(List.of(Rol.VOCALISTA)), 5.0, 1);
        pepe3 = new ArtistaBase("Pepe3", List.of(), new ArrayList<>(List.of(Rol.GUITARRISTA)), 10.0, 3);

        artistas.add(pepe1);
        artistas.add(pepe2);
        artistas.add(pepe3);
        recital = new Recital(artistas, List.of(cancion));

        recital.asignacionAutomaticaDeCanciones();
    }

    @Test
    public void asignacionDeCancionesTest() {
        assertEquals(2, recital.getRelacionArtistaCancion().size());

        assertTrue(recital.getRelacionArtistaCancion().stream()
                .anyMatch(rel -> rel.getArtista().equals(pepe2)
                        && rel.getCancion().equals(cancion)
                        && rel.getRolQueCumple() == Rol.VOCALISTA));

        assertTrue(recital.getRelacionArtistaCancion().stream()
                .anyMatch(rel -> rel.getArtista().equals(pepe3)
                        && rel.getCancion().equals(cancion)
                        && rel.getRolQueCumple() == Rol.GUITARRISTA));
    }

    @Test
    public void rolesFaltantesTest() {
        final Cancion cancionNueva = new Cancion("Magia veneno", List.of(Rol.VOCALISTA, Rol.GUITARRISTA, Rol.BATERISTA, Rol.BAJISTA));
        final int falta = recital.rolesFaltantes(cancionNueva);
        assertEquals(2, falta);
    }

    @Test
    public void rolesFaltantesRecitalTest() {
        final int rolesFaltantes = recital.rolesFaltantesRecital();
        assertEquals(1, rolesFaltantes);
    }


    @Test
    public void contratarPorCancionTest() {
        final ArtistaCandidato pedro1 = new ArtistaCandidato("Pedro1", List.of(Banda.GUNS), List.of(Rol.BAJISTA), 10.0, 3);
        final ArtistaCandidato pedro2 = new ArtistaCandidato("Pedro2", List.of(Banda.GUNS), List.of(Rol.GUITARRISTA), 10.0, 3);
        final ArtistaCandidato pedro3 = new ArtistaCandidato("Pedro3", List.of(Banda.SODA), List.of(Rol.GUITARRISTA), 10.0, 3);

        final ArtistaContratado pedro2Contratado = ArtistaContratado.contratar(pedro2, 5.0);

        recital.contratar(Set.of(pedro1, pedro2, pedro3), cancion, Rol.GUITARRISTA);

        assertTrue(recital.getArtistas().contains(pedro2Contratado));

        assertTrue(recital.getRelacionArtistaCancion().stream()
                .anyMatch(rel -> rel.getArtista().equals(pedro2Contratado)));
    }

    @Test
    public void contratacionesMasivasTest() {
        final Cancion cancion2 = new Cancion("Setentistas", List.of(Rol.BAJISTA, Rol.VOCALISTA, Rol.GUITARRISTA, Rol.BATERISTA));

        final Recital nuevoRecital = new Recital(List.of(pepe1, pepe2), List.of(cancion, cancion2));
        nuevoRecital.asignacionAutomaticaDeCanciones();

        final ArtistaCandidato pedro1 = new ArtistaCandidato("Pedro1", List.of(Banda.GUNS), List.of(Rol.BAJISTA, Rol.PIANISTA), 10.0, 3);
        final ArtistaCandidato pedro2 = new ArtistaCandidato("Pedro2", List.of(Banda.GUNS), List.of(Rol.GUITARRISTA), 10.0, 3);
        final ArtistaCandidato pedro3 = new ArtistaCandidato("Pedro3", List.of(Banda.SODA), List.of(Rol.GUITARRISTA), 10.0, 3);
        final ArtistaCandidato pedro4 = new ArtistaCandidato("Pedro4", List.of(Banda.SODA), List.of(Rol.BATERISTA), 6.0, 3);
        final ArtistaCandidato pedro5 = new ArtistaCandidato("Pedro5", List.of(Banda.GUNS), List.of(Rol.BATERISTA), 6.0, 2);
        final ArtistaCandidato pedro6 = new ArtistaCandidato("Pedro6", List.of(Banda.SODA), List.of(Rol.VOCALISTA), 6.0, 2);

        final Set<ArtistaCandidato> artistas = Set.of(pedro1, pedro2, pedro3, pedro4, pedro5, pedro6);
        
        // Calcular costos esperados (con descuento si comparten banda con pepe2 que tiene GUNS)
        final Set<ArtistaBase> artistasBase = Set.of(pepe1, pepe2);
        final double costoPedro1 = pedro1.calcularCosto(artistasBase); // GUNS -> 10.0 * 0.5 = 5.0
        final double costoPedro2 = pedro2.calcularCosto(artistasBase); // GUNS -> 10.0 * 0.5 = 5.0
        final double costoPedro5 = pedro5.calcularCosto(artistasBase); // GUNS -> 6.0 * 0.5 = 3.0
        final double costoPedro6 = pedro6.calcularCosto(artistasBase); // SODA -> 6.0 (sin descuento)
        
        // Crear los ArtistaContratado esperados
        final ArtistaContratado pedro1Contratado = ArtistaContratado.contratar(pedro1, costoPedro1);
        final ArtistaContratado pedro2Contratado = ArtistaContratado.contratar(pedro2, costoPedro2);
        final ArtistaContratado pedro5Contratado = ArtistaContratado.contratar(pedro5, costoPedro5);
        final ArtistaContratado pedro6Contratado = ArtistaContratado.contratar(pedro6, costoPedro6);
        
        nuevoRecital.contratacionMasiva(artistas);

        // Verificar que pepe2 (ArtistaBase) sigue en la lista
        assertTrue(nuevoRecital.getArtistas().contains(pepe2));
        
        // Verificar que los artistas contratados están en la lista usando equals
        assertTrue(nuevoRecital.getArtistas().contains(pedro1Contratado));
        assertTrue(nuevoRecital.getArtistas().contains(pedro2Contratado));
        assertTrue(nuevoRecital.getArtistas().contains(pedro5Contratado));
        assertTrue(nuevoRecital.getArtistas().contains(pedro6Contratado));
    }

    @Test
    public void listadoDeContratados() {
        final ArtistaCandidato pedro1 = new ArtistaCandidato("Pedro1", List.of(Banda.GUNS), List.of(Rol.BAJISTA), 10.0, 3);
        final ArtistaCandidato pedro2 = new ArtistaCandidato("Pedro2", List.of(Banda.GUNS), List.of(Rol.GUITARRISTA), 10.0, 3);
        final ArtistaCandidato pedro3 = new ArtistaCandidato("Pedro3", List.of(Banda.SODA), List.of(Rol.GUITARRISTA), 10.0, 3);

        final ArtistaContratado pedro2Contratado = ArtistaContratado.contratar(pedro2, 5.0);

        recital.contratar(Set.of(pedro1, pedro2, pedro3), cancion, Rol.GUITARRISTA);

        assertEquals(1,recital.getArtistasContratados().size());
        assertTrue(recital.getArtistasContratados().stream().anyMatch(artistaContratado -> artistaContratado.equals(pedro2Contratado)));
    }
}