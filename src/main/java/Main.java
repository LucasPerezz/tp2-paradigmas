import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("TP PARADIGMAS");

        // == Recital
        List<Cancion> canciones = null;
        List<ArtistaBase> artistasBases = null;
        Set<ArtistaCandidato> artistasCandidatos = null;

        LectorJson lectorJson = new LectorJson();
        lectorJson.cargarDatos();
        canciones = lectorJson.getCanciones();
        artistasBases =  lectorJson.getArtistasBases();
        artistasCandidatos = new HashSet<>(lectorJson.getArtistasCandidatos());

        Recital recital = new Recital(artistasBases, canciones);
        recital.asignacionAutomaticaDeCanciones();

        MenuRecital menu = new MenuRecital(recital, artistasCandidatos);
        menu.iniciar();
    }
}
