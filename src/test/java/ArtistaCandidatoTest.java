import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArtistaCandidatoTest {
    private ArtistaCandidato artistaCandidato;
    @Before
    public void setUp() {

        ArrayList<Rol> roles = new ArrayList<>();
        roles.add(Rol.VOCALISTA);
        roles.add(Rol.GUITARRISTA);

        artistaCandidato = new ArtistaCandidato("pepe", List.of(),roles, 10.0, 3);
    }

    @Test
    public void entrenamientoTest(){
        artistaCandidato.entrenar(Rol.BAJISTA);

       assertEquals(List.of(Rol.VOCALISTA, Rol.GUITARRISTA, Rol.BAJISTA), artistaCandidato.getRoles());

        assertEquals(15.0, artistaCandidato.getCostoPorCancion(), 0.0001);
    }
}
