import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("TP PARADIGMAS");

        // == Recital
        List<Cancion> canciones = null;
        List<ArtistaBase> artistasBases = null;

        LectorJson lectorJson = new LectorJson();
        lectorJson.cargarDatos();
         canciones = lectorJson.getCanciones();
         artistasBases =  lectorJson.getArtistasBases();

        Recital recital = new Recital(artistasBases, canciones);
        recital.asignacionAutomaticaDeCanciones();

        MenuRecital menu = new MenuRecital(recital);
        menu.iniciar();


    }
}
