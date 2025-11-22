import org.jpl7.*;

import java.util.Map;

public class PrologHelper {

    private PrologHelper() {
        throw new AssertionError("No se puede instanciar una clase de utilidad");
    }

    public static void cargarDatos(Recital recital, double costoMinimo) {
        for(Artista a :recital.getArtistas()) {
            addArtista(a);
        }

        for(Cancion c :recital.getCancionesLineUp()) {
            addCancion(c);
        }

        agregarFact("costo_parametro", costoMinimo);

        queryEntrenamiento();
    }

    public static void addArtista(Artista artista) {

        agregarFact("artista", artista.getNombre(), (artista instanceof ArtistaBase) ? "base" : "contratado");

        for(Rol r : artista.getRoles()) {
            agregarFact("rol_natural", artista.getNombre(), r.toString().toLowerCase());
        }

    }

    public static void addCancion(Cancion cancion) {

        agregarFact("cancion", cancion.getNombre());

        for(Rol rol: cancion.getRolesRequeridos()) {
            agregarFact("rol_requerido", cancion.getNombre(), rol.toString().toLowerCase());
        }

        for(Artista a: cancion.getArtistasAsignados()) {
            if(a != null) {
                agregarFact("artista_asignado", cancion.getNombre(),  a.getNombre());
            }
        }

    }

    public static void agregarFact(String fact, String valor) {
        Term termino = new Compound(fact, new Term[] {
                new Atom(valor)
        });

        Term assertz = new Compound("assertz", new Term[] { termino });
        Query q = new Query(assertz);

        if (!q.hasSolution()) {
            System.out.println("Error al agregar:"  + fact + "(" + valor + ")");
        }
        q.close();
    }

    public static void agregarFact(String fact, Double valor) {
        Term termino = new Compound(fact, new Term[] {
                new org.jpl7.Float(valor)
        });

        Term assertz = new Compound("assertz", new Term[] { termino });
        Query q = new Query(assertz);

        if (!q.hasSolution()) {
            System.out.println("Error al agregar:"  + fact + "(" + valor + ")");
        }
        q.close();
    }

    public static void agregarFact(String fact, String valor1, String valor2) {
        Term termino = new Compound(fact, new Term[] {
                new Atom(valor1),
                new Atom(valor2)
        });

        Term assertz = new Compound("assertz", new Term[] { termino });
        Query q = new Query(assertz);

        if (!q.hasSolution()) {
            System.out.println("Error al agregar:" + fact + "(" + valor1 + ", " +  valor2 + ")");
        }
        q.close();
    }

    static void queryArtistas() {
        Variable X = new Variable("X");
        Variable anonymous = new Variable("_");
        Term likesTerm = new Compound("artista", new Term[] {
                X,
                anonymous
        });

        Query query = new Query(likesTerm);

        System.out.println("\nArtistas");
        while (query.hasMoreSolutions()) {
            Map<String, Term> solution = query.nextSolution();
            System.out.println("  - " + solution.get("X"));
        }
        query.close();
    }

    static void queryEntrenamiento() {
        Query q1 = new Query("consult", new Term[] { new Atom("base.pl") });
        Query q3 = new Query("analizar_recital");
        Query q2 = new Query("mostrar_solucion");

        try {
            if (q1.hasSolution()) {
                q1.oneSolution();

                System.out.println("Analisis del recital");
                q3.oneSolution();  // Just execute, don't print the map
                q3.close();

                System.out.println("Consulta de entrenamientos");
                q2.oneSolution();  // Just execute, don't print the map
                q2.close();
            }
        } finally {
            q1.close();
        }
    }

}
