import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuTest {
    private Cancion cancion;
    private List<ArtistaBase> artistas = new ArrayList<>();
    private Recital recital;
    private ArtistaBase pepe1;
    private ArtistaBase pepe2;
    private ArtistaBase pepe3;
    MenuRecital menuRecital;


    @Before
    public void setUp() {
        cancion = new Cancion("Crimen", List.of(Rol.VOCALISTA, Rol.GUITARRISTA, Rol.PIANISTA, Rol.GUITARRISTA));

        pepe1 = new ArtistaBase("Pepe1", List.of(), new ArrayList<>(List.of(Rol.BAJISTA)),10.0, 3);
        pepe2 = new ArtistaBase("Pepe2", List.of(),  new ArrayList<>(List.of(Rol.VOCALISTA)),5.0, 1);
        pepe3 = new ArtistaBase("Pepe3", List.of(),  new ArrayList<>(List.of(Rol.GUITARRISTA)),10.0, 3);

        artistas.add(pepe1);
        artistas.add(pepe2);
        artistas.add(pepe3);
        recital = new Recital(artistas, List.of(cancion));

        String input = "1\n1\n1\n1\n1\n1\n1\n10.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        menuRecital = new MenuRecital(recital);

    }

    @Test
    public void testarOpcion1() {
        menuRecital.procesarOpcion(1);
    }

    @Test
    public void testarOpcion2() {
        menuRecital.procesarOpcion(2);
    }

    @Test
    public void testarOpcion3() {
        menuRecital.procesarOpcion(3);
    }

    @Test
    public void testearOpcion4(){

        menuRecital.procesarOpcion(4);
    }

    @Test
    public void testearOpcion5(){
        menuRecital.procesarOpcion(5);
    }
    @Test
    public void testearOpcion6(){
        menuRecital.procesarOpcion(6);
    }
    @Test
    public void testearOpcion7(){
        menuRecital.procesarOpcion(7);
    }

    /*@Test
    public void testearOpcion8(){
        menuRecital.procesarOpcion(8);
    }*/
}
