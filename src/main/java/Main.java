import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("TP PARADIGMAS");

        // == Recital
        List<Cancion> canciones = null;
        List<Artista> artistas = null;
        try {
            canciones = LectorJson.cargarCanciones(new File("canciones.json"));
            artistas = LectorJson.cargarArtistas(new File("artistas.json"));

            Recital recital = new Recital(artistas, canciones);
            recital.asignacionAutomaticaDeCanciones();

            MenuRecital menu = new MenuRecital(recital);
            menu.iniciar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
