import java.util.Scanner;
import java.util.InputMismatchException;

public class MenuRecital {
    private Scanner scanner;
    private boolean enCurso;
    //private Recital recital;

    public MenuRecital() {
        this.scanner = new Scanner(System.in);
        this.enCurso = true;
    }

    public void iniciar() {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║                   GESTIÓN DE RECITAL                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");

        while (enCurso) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }

        scanner.close();
        System.out.println("\n¡Hasta luego!");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║                    MENÚ PRINCIPAL                      ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║ 1. Consultar roles faltantes para canción específica   ║");
        System.out.println("║ 2. Consultar roles faltantes para todas las canciones  ║");
        System.out.println("║ 3. Contratar artistas para canción específica          ║");
        System.out.println("║ 4. Contratar artistas para todas las canciones         ║");
        System.out.println("║ 5. Entrenar artista                                    ║");
        System.out.println("║ 6. Listar artistas contratados                         ║");
        System.out.println("║ 7. Listar canciones y su estado                        ║");
        System.out.println("║ 8. Consultas Prolog                                    ║");
        System.out.println("║ 9. Salir                                               ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.print("\nSeleccione una opción: ");
    }

    private int leerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            return opcion;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }


    // ========== OPCIÓN 1 ==========
    private void consultarRolesFaltantesCancion() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  ROLES FALTANTES PARA CANCIÓN ESPECÍFICA");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // Mostrar canciones disponibles primero
        System.out.println("Canciones disponibles en el recital:");
        // TODO: metodo de recital que liste todas las canciones
        // recital.listarCancionesDisponibles();

        System.out.print("\nIngrese el nombre de la canción: ");
        String nombreCancion = scanner.nextLine().trim();

        if (nombreCancion.isEmpty()) {
            System.out.println("Error: Debe ingresar un nombre de canción.");
            return;
        }

        // TODO: Implementar lógica
        System.out.println("\nROLES FALTANTES:");
        System.out.println("Canción: " + nombreCancion);
        System.out.println("\nRoles requeridos:");
        // TODO: Metodo de cancion que liste los roles que tiene, y a apartir de sus artistas, muestre los que faltan
        // Ejemplo
        System.out.println("===== Guitarrista =====");
        System.out.println("Requeridos: 2");
        System.out.println("Contratados: 1");
        System.out.println("\n> Faltantes: 1\n");

        System.out.println("===== Vocalista =====");
        System.out.println("Requeridos: 1");
        System.out.println("Contratados: 1");
        System.out.println("\n> Faltantes: 0\n");

        System.out.println("===== Baterista =====");
        System.out.println("Requeridos: 1");
        System.out.println("Contratados: 0");
        System.out.println("\n> Faltantes: 1\n");
    }

    // ========== OPCIÓN 2 ==========
    private void consultarRolesFaltantesTodas() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  ROLES FALTANTES PARA TODAS LAS CANCIONES");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // TODO: Implementar metodos
        System.out.println("\nRoles totales necesarios:");
        System.out.println("- Guitarrista: 5");
        System.out.println("- Baterista: 3");
        System.out.println("- Vocalista: 4");
        System.out.println("- Bajista: 2");
        System.out.println("\nTotal de roles cubiertos: 8");
        System.out.println("Total de roles faltantes: 6");
    }

    // ========== OPCIÓN 3 ==========
    private void contratarParaCancion() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  CONTRATAR ARTISTAS PARA CANCIÓN ESPECÍFICA");
        System.out.println("═══════════════════════════════════════════════════════\n");

        System.out.println("Canciones disponibles:");
        // TODO: Listar canciones sin contratos completos
        // 1 - Cancion
        // 2 - Cancion

        System.out.print("\nSeleccione la canción: ");
        int cancion = leerOpcion();

        // TODO: Validar selección

        // TODO: Verificar roles faltantes
        System.out.println("\nRoles faltantes para la cancion:");
        System.out.println("- Guitarrista: 1");
        System.out.println("- Baterista: 1");

        // TODO: Algoritmo de contratación
        System.out.println("\nContratación propuesta:");
        System.out.println("\nArtistas a contratar:");
        System.out.println("- Juan Pérez (Guitarrista) - $3,000");
        System.out.println("- María García (Baterista) - $3,000");
        System.out.println("Costo total: $6,000");

        System.out.print("\n¿Confirmar contratación? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        if (confirmacion.equals("S")) {
            // TODO: Realizar contratación. Metodo de cancion(?
            System.out.println("\nContratación realizada exitosamente.");
        } else {
            System.out.println("\nContratación cancelada.");
        }
    }

    // ========== OPCIÓN 4 ==========
    private void contratarParaTodasCanciones() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  CONTRATAR ARTISTAS PARA TODAS LAS CANCIONES");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // Considerar: descuentos por compartir banda

        // TODO: Mostrar estado actual
        System.out.println("Estado actual:");
        System.out.println("- Canciones no cubiertas: 5");
        System.out.println("- Canciones cubiertas: 2");
        System.out.println("- Roles totales faltantes: 12\n");

        // TODO: Algoritmo de optimización para todas las canciones

        System.out.println("Contrataciones propuestas:");

        // === Nombre de artista ===
        System.out.println("=== Nombre artista ===");
        System.out.println("- Cancion 1 (Rol) ");

        System.out.println("\n=== Nombre artista ===");
        System.out.println("- Cancion 1 (Rol) ");

        System.out.println("\n=== Nombre artista ===");
        System.out.println("- Cancion 1 (Rol) ");
        System.out.println("Costo total: $20,000");

        System.out.print("¿Confirmar todas las contrataciones? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        if (confirmacion.equals("S")) {
            // TODO: Realizar contrataciones, mismo que la opcion 3
            System.out.println("\nTodas las contrataciones realizadas exitosamente.");
        } else {
            System.out.println("\nContrataciones canceladas.");
        }
    }

    // ========== OPCIÓN 5 ==========
    private void entrenarArtista() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  ENTRENAR ARTISTA");
        System.out.println("═══════════════════════════════════════════════════════\n");

        System.out.println("Aviso:");
        System.out.println("No se puede entrenar artistas ya contratados");
        System.out.println("No se puede entrenar artistas base");
        System.out.println("Cada rol adicional incrementa el costo en 50%\n");

        System.out.println("Artistas disponibles para entrenar:");
        // TODO: Listar artistas disponibles
        // Nombre, Roles, Precio actual

        System.out.print("\nSeleccione el artista: ");
        String nombreArtista = scanner.nextLine().trim();


        // TODO: Validar selección
        System.out.println("\nArtista seleccionado: nombre");
        System.out.println("Roles actuales: Guitarrista");
        System.out.println("Costo actual: $5,000");

        System.out.println("\nRoles disponibles para aprender:");
        System.out.println("  1. Vocalista");
        System.out.println("  2. Bajista");
        System.out.println("  3. Teclista");

        System.out.print("\nSeleccione rol a aprender: ");
        int rolSeleccion = leerOpcion();

        // TODO: Validar y aplicar entrenamiento

        // Ejemplo
        double costoAnterior = 5000;
        double nuevoCosto = costoAnterior * 1.5;

        System.out.println("\nCÁLCULO DE NUEVO COSTO:");
        System.out.println("Costo anterior: $" + String.format("%.2f", costoAnterior));
        System.out.println("Incremento (50%): $" + String.format("%.2f", costoAnterior * 0.5));
        System.out.println("Nuevo costo: $" + String.format("%.2f", nuevoCosto));

        System.out.print("\n¿Confirmar entrenamiento? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        if (confirmacion.equals("S")) {
            // TODO: Aplicar entrenamiento
            System.out.println("\nEntrenamiento completado exitosamente.");
            System.out.println("nombreArtista ahora puede desempeñar el rol de nuevoRol.");
        } else {
            System.out.println("\nEntrenamiento cancelado.");
        }
    }

    // ========== OPCIÓN 6 ==========
    private void listarArtistasContratados() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  ARTISTAS CONTRATADOS");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // TODO: Obtener lista de artistas contratados;

//        System.out.println("┌──────────────────────────────────────────────────────┐");
//        System.out.println("│ Juan Pérez                                           │");
//        System.out.println("├──────────────────────────────────────────────────────┤");
//        System.out.println("│ Roles: Guitarrista                                   │");
//        System.out.println("│ Banda: Los Rockeros                                  │");
//        System.out.println("│ Canciones asignadas: Canción A, Canción C            │");
//        System.out.println("│ Costo: $8,000                                        │");
//        System.out.println("└──────────────────────────────────────────────────────┘\n");

        System.out.println("===== Nombre =====");
        System.out.println("Roles:");
        System.out.println("- Rol 1");
        System.out.println("- Rol 2 (Entrenado)");
        System.out.println("Canciones:");
        System.out.println("- Cancion 1");
        System.out.println(">Costo total:");

        System.out.println("\nCosto total de artistas contratados: $45,000");
    }

    private void listarCanciones() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  ESTADO DE CANCIONES");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // TODO: Obtener lista de canciones
        System.out.println("Total de canciones en el recital: 7\n");

        System.out.println("===== Nombre =====");
        System.out.println("Estado: Cubierta/No Cubierta");
        System.out.println("- Rol 1 -> Artista");
        System.out.println("- Rol 2 -> No cubierto");
        System.out.println(">Costo total:");

        //
        System.out.println("\nCanciones completas: 3/7");
        System.out.println("Canciones incompletas: 4/7");
        System.out.println("Costo total: $45,000");

    }

    // ========== OPCIÓN 8 ==========
    private void consultasProlog() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  CONSULTAS PROLOG");
        System.out.println("═══════════════════════════════════════════════════════\n");

        System.out.println("¿Cuántos entrenamientos mínimos debo realizar para cubrir todos " +
                "los roles para el recital, utilizando solo los miembros base, y artistas " +
                "contratados sin experiencia y con un coste base por parámetro, para todos iguales?");

        System.out.println("\n[Funcionalidad Prolog a implementar]");
    }

    // ========== OPCIÓN 9 ==========
    private void confirmarSalida() {
        System.out.print("¿Está seguro que desea salir? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        if (confirmacion.equals("S")) {
            enCurso = false;
        }
    }


    // Utilidad
    private void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    private void procesarOpcion(int opcion) {
        System.out.println(); // Línea en blanco para mejor legibilidad

        switch (opcion) {
            case 1:
                consultarRolesFaltantesCancion();
                break;
            case 2:
                consultarRolesFaltantesTodas();
                break;
            case 3:
                contratarParaCancion();
                break;
            case 4:
                contratarParaTodasCanciones();
                break;
            case 5:
                entrenarArtista();
                break;
            case 6:
                listarArtistasContratados();
                break;
            case 7:
                listarCanciones();
                break;
            case 8:
                consultasProlog();
                break;

                // añadir QUITAR artista ((?. Son 2 puntos más.
            case 0:
                confirmarSalida();
                break;
            default:
                System.out.println("Opción inválida. Por favor seleccione una opción del 1 al 8.");
        }

        if (enCurso && opcion != 0) {
            pausar();
        }
    }

}
