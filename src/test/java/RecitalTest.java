import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class RecitalTest {
    private Cancion cancion;
    private List<Artista> artistas = new ArrayList<>();
    private Recital recital;
    private ArtistaBase pepe1;
    private ArtistaBase pepe2;
    private ArtistaBase pepe3;

    @Before
    public void setUp() {
        cancion = new Cancion("Crimen", List.of(Rol.VOCALISTA, Rol.GUITARRISTA, Rol.PIANISTA));

        pepe1 = new ArtistaBase("Pepe1", List.of(), new ArrayList<>(List.of(Rol.BAJISTA)),10.0, 3);
        pepe2 = new ArtistaBase("Pepe2", List.of(Banda.GUNS), new ArrayList<>(List.of(Rol.VOCALISTA)),5.0, 1);
        pepe3 = new ArtistaBase("Pepe3", List.of(), new ArrayList<>(List.of(Rol.GUITARRISTA)),10.0, 3);
        
        artistas.add(pepe1);
        artistas.add(pepe2);
        artistas.add(pepe3);
        recital = new Recital(artistas, List.of(cancion));

        recital.asignacionAutomaticaDeCanciones();
    }

    @Test
    public void asignacionDeCancionesTest(){
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
    public void imprimirCancionTest(){
        String response = recital.imprimirCancion(cancion);
        System.out.println(response);
    }

    @Test
    public void imprimirArtistaTest(){
        System.out.println(pepe1);
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
    public void calcularRolesFaltantesTodasTest() {
        RolesFaltantesInfo info = recital.obtenerRolesFaltantesRecital();

        Rol[] todosLosRoles = Rol.values();

        System.out.println("Roles necesarios: ");
        for (Rol rol : todosLosRoles) {
            int necesarios = info.getRolesNecesarios().getOrDefault(rol, 0);
            if (necesarios > 0) {
                String nombreRol = rol.toString();
                System.out.println("- " + nombreRol + ": " + necesarios);
            }
        }

        System.out.println("Roles cubiertos: ");
        for (Rol rol : todosLosRoles) {
            int necesarios = info.getRolesCubiertos().getOrDefault(rol, 0);
            if (necesarios > 0) {
                String nombreRol = rol.toString();
                System.out.println("- " + nombreRol + ": " + necesarios);
            }
        }

        System.out.println("Roles faltantes: ");
        for (Rol rol : todosLosRoles) {
            int necesarios = info.getRolesFaltantes().getOrDefault(rol, 0);
            if (necesarios > 0) {
                String nombreRol = rol.toString();
                System.out.println("- " + nombreRol + ": " + necesarios);
            }
        }

        System.out.println("\nTotal de roles necesarios: " + info.getTotalNecesarios());
        System.out.println("Total de roles cubiertos: " + info.getTotalCubiertos());
        System.out.println("Total de roles faltantes: " + info.getTotalFaltantes());
    }

    @Test
    public void contratarPorCancionTest() {
        final Recital nuevoRecital = recital;
        final ArtistaCandidato pedro1 = new ArtistaCandidato("Pedro1", List.of(Banda.GUNS), List.of(Rol.BAJISTA),10.0, 3);
        final ArtistaCandidato pedro2 = new ArtistaCandidato("Pedro2", List.of(Banda.GUNS), List.of(Rol.GUITARRISTA),10.0, 3);
        final ArtistaCandidato pedro3 = new ArtistaCandidato("Pedro3", List.of(Banda.SODA), List.of(Rol.GUITARRISTA),10.0, 3);

        nuevoRecital.contratar(Set.of(pedro1, pedro2, pedro3), cancion, Rol.GUITARRISTA);


       assertTrue(nuevoRecital.getArtistas().contains(pedro2));

        assertTrue(nuevoRecital.getRelacionArtistaCancion().stream()
                .anyMatch(rel -> rel.getArtista().equals(pedro2)));
    }

    @Test
    public void contratacionesMasivasTest() {
        final Cancion cancion2 = new Cancion("Setentistas", List.of(Rol.BAJISTA, Rol.VOCALISTA, Rol.GUITARRISTA, Rol.BATERISTA));

        final Recital nuevoRecital = new Recital(List.of(pepe1,pepe2), List.of(cancion,cancion2));
        nuevoRecital.asignacionAutomaticaDeCanciones();

        final ArtistaCandidato pedro1 = new ArtistaCandidato("Pedro1", List.of(Banda.GUNS), List.of(Rol.BAJISTA, Rol.PIANISTA),10.0, 3);
        final ArtistaCandidato pedro2 = new ArtistaCandidato("Pedro2", List.of(Banda.GUNS), List.of(Rol.GUITARRISTA),10.0, 3);
        final ArtistaCandidato pedro3 = new ArtistaCandidato("Pedro3", List.of(Banda.SODA), List.of(Rol.GUITARRISTA),10.0, 3);
        final ArtistaCandidato pedro4 = new ArtistaCandidato("Pedro4", List.of(Banda.SODA), List.of(Rol.BATERISTA),6.0, 3);
        final ArtistaCandidato pedro5 = new ArtistaCandidato("Pedro5", List.of(Banda.GUNS), List.of(Rol.BATERISTA),6.0, 2);
        final ArtistaCandidato pedro6 = new ArtistaCandidato("Pedro6", List.of(Banda.SODA), List.of(Rol.VOCALISTA),6.0, 2);

        final Set<ArtistaCandidato> artistas = Set.of(pedro1, pedro2, pedro3, pedro4, pedro5,pedro6);
        nuevoRecital.contratacionMasiva(artistas);

        assertTrue(nuevoRecital.getArtistas().contains(pepe2));
        assertTrue(nuevoRecital.getArtistas().contains(pedro1));
        assertTrue(nuevoRecital.getArtistas().contains(pedro2));
        assertTrue(nuevoRecital.getArtistas().contains(pedro5));
        assertTrue(nuevoRecital.getArtistas().contains(pedro6));
    }
}
