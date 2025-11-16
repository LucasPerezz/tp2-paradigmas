import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("TP PARADIGMAS");

        // == The Smiths
        Banda theSmiths = new Banda("The Smiths");

        Artista morrisey = new ArtistaCandidato("Morrisey", 1000.00, 2000.00);
        morrisey.agregarBanda(theSmiths);
        morrisey.agregarRol(Rol.VOCALISTA);

        Artista johnnyMarr = new ArtistaBase("Johnny Marr", 1000.00, 10);
        johnnyMarr.agregarBanda(theSmiths);
        johnnyMarr.agregarRol(Rol.GUITARRARISTA);

        Cancion iKnowItsOver = new Cancion("I Know It's Over",
                new ArrayList<>(Arrays.asList(Rol.VOCALISTA, Rol.GUITARRARISTA))
        );
        iKnowItsOver.asignarArtista(Rol.GUITARRARISTA, johnnyMarr);
        // == Blur
        Banda blur = new Banda("Blur");

        Artista damonAlbarn = new ArtistaCandidato("Damon Albarn", 750.00, 20000.00);
        damonAlbarn.agregarBanda(blur);
        damonAlbarn.agregarRol(Rol.VOCALISTA);

        Artista grahamCoxon = new ArtistaBase("Graham Coxon", 900.00, 3);
        grahamCoxon.agregarBanda(blur);
        grahamCoxon.agregarRol(Rol.GUITARRARISTA);

        Cancion parklife = new Cancion("Parklife",
                new ArrayList<>(Arrays.asList(Rol.VOCALISTA, Rol.GUITARRARISTA))
        );
        parklife.asignarArtista(Rol.VOCALISTA, damonAlbarn);
        parklife.asignarArtista(Rol.GUITARRARISTA, grahamCoxon);
        // == Oasis
        Banda oasis = new Banda("Oasis");

        Artista liamGallagher = new ArtistaCandidato("Liam Gallagher", 1500.00, 20000.00);
        liamGallagher.agregarBanda(oasis);
        liamGallagher.agregarRol(Rol.VOCALISTA);

        Artista noelGallagher = new ArtistaBase("Noel Gallagher", 1500.00, 7);
        noelGallagher.agregarBanda(oasis);
        noelGallagher.agregarRol(Rol.GUITARRARISTA);

        Cancion dontLookBackInAnger = new Cancion("Don't Look Back In Anger",
                new ArrayList<>(Arrays.asList(Rol.VOCALISTA, Rol.GUITARRARISTA))
        );
        dontLookBackInAnger.asignarArtista(Rol.VOCALISTA, liamGallagher);
        dontLookBackInAnger.asignarArtista(Rol.GUITARRARISTA, noelGallagher);

        // == Recital
        List<Cancion> canciones = new ArrayList<>(Arrays.asList(iKnowItsOver, parklife, dontLookBackInAnger));
        List<Artista> artistas = new ArrayList<>(Arrays.asList(morrisey, johnnyMarr, damonAlbarn, grahamCoxon, liamGallagher, noelGallagher));

        System.out.println(parklife.estaCubierta());

        Recital recital = new Recital(artistas, canciones);
        MenuRecital menu = new MenuRecital(recital);

        menu.iniciar();
    }
}
