import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("TP PARADIGMAS");

        /*// == Recital
        List<Cancion> canciones = null;
        List<Artista> artistas = null;
        try {
            canciones = LectorJson.cargarCanciones(new File("canciones.json"));
            artistas = LectorJson.cargarArtistas(new File("artistas.json"));
            AsignacionUtilidades.vincularDatos(artistas, canciones);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        Recital recital = new Recital(artistas, canciones, null);
        MenuRecital menu = new MenuRecital(recital);

        menu.iniciar();*/

        Cancion cancion;
        List<Artista> artistas = new ArrayList<>();
        Recital recital;
        ArtistaBase pepe1;
        ArtistaBase pepe2;
        ArtistaCandidato pepe3;
        MenuRecital menuRecital;


        cancion = new Cancion("Crimen", List.of(Rol.VOCALISTA, Rol.GUITARRISTA, Rol.PIANISTA));

        pepe1 = new ArtistaBase("Pepe1", List.of(), new ArrayList<>(List.of(Rol.PIANISTA)),10.0, 3);
        pepe2 = new ArtistaBase("Pepe2", List.of(Banda.GUNS), new ArrayList<>(List.of(Rol.VOCALISTA)),5.0, 1);
        pepe3 = new ArtistaCandidato("Pepe3", List.of(), new ArrayList<>(List.of(Rol.GUITARRISTA)),10.0, 3);
        ArtistaCandidato pepe4 = new ArtistaCandidato("Pepe4", List.of(), new ArrayList<>(List.of(Rol.GUITARRISTA)),10.0, 3);
        ArtistaCandidato pepe5 = new ArtistaCandidato("Pepe5", List.of(), new ArrayList<>(List.of(Rol.VOCALISTA)),10.0, 3);
        ArtistaCandidato pepe6 = new ArtistaCandidato("Pepe6", List.of(), new ArrayList<>(List.of(Rol.PIANISTA)),10.0, 3);


        artistas.add(pepe1);
        artistas.add(pepe2);
        artistas.add(pepe3);
        artistas.add(pepe5);
        artistas.add(pepe6);
        recital = new Recital(artistas, List.of(cancion));
        recital.asignacionAutomaticaDeCanciones();

        menuRecital = new MenuRecital(recital);

        menuRecital.iniciar();

    }
}
