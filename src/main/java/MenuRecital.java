import java.util.*;

public class MenuRecital {
    private Scanner scanner;
    private boolean enCurso;
    private Recital recital;

    public MenuRecital(Recital recital) {
        this.scanner = new Scanner(System.in);
        this.enCurso = true;
        this.recital = recital;
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
        System.out.println("║ 9. Eliminar artista                                    ║");
        System.out.println("║ 0. Salir                                               ║");
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
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║         ROLES FALTANTES PARA CANCION ESPECIFICA        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        System.out.println("Canciones disponibles en el recital:");

        List<Cancion> canciones = this.recital.getCancionesLineUp();

        for (int i = 0; i < canciones.size(); i++) {
            System.out.println((i+1) + ". " + canciones.get(i).getNombre());
        }

        System.out.print("\nIngrese la canción: ");
        int cancionOpcion;

        do {
            cancionOpcion = leerOpcion();

            if(cancionOpcion <= 0 || cancionOpcion > canciones.size()) {
                System.out.println("Seleccione una opcion valida.");
                System.out.print("\nSeleccione la cancion: ");
            }
        } while(cancionOpcion <= 0 ||  cancionOpcion > canciones.size());

        Cancion cancion = recital.getCancionesLineUp().get(cancionOpcion - 1);

        List<Rol> rolesCubiertos = recital.rolesCumplidosPorCancion(cancion);
        List<Rol> rolesRequeridos = cancion.getRolesRequeridos();

        System.out.println("Roles requeridos:");
        for (Rol rol : rolesRequeridos) {
            String estado = rolesCubiertos.contains(rol) ? "Cubierto" : "No cubierto";
            System.out.println(rol.toString() + ": " + estado);
        }

        int faltantes = cancion.getRolesRequeridos().size() - recital.rolesCumplidosPorCancion(cancion).size();
        if (faltantes > 0) {
            System.out.println("Faltan " + faltantes + " artistas en el recital para poder cubrir esta cancion");
        } else {
            System.out.println("No hace falta contratar artistas para cubrir esta cancion");
        }

    }

    // ========== OPCIÓN 2 ==========
    private void consultarRolesFaltantesTodas() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║        ROLES FALTANTES PARA TODAS LAS CANCIONES        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        RolesFaltantesInfo info = this.recital.obtenerRolesFaltantesRecital();
        
        System.out.println("\nRoles totales necesarios:");
        Rol[] todosLosRoles = Rol.values();
        
        for (Rol rol : todosLosRoles) {
            int necesarios = info.getRolesNecesarios().getOrDefault(rol, 0);
            if (necesarios > 0) {
                String nombreRol = capitalizarRol(rol.toString());
                System.out.println("- " + nombreRol + ": " + necesarios);
            }
        }
        
        System.out.println("\nTotal de roles necesarios: " + info.getTotalNecesarios());
        System.out.println("Total de roles cubiertos: " + info.getTotalCubiertos());
        System.out.println("Total de roles faltantes: " + info.getTotalFaltantes());
    }

    private String capitalizarRol(String rol) {
        if (rol == null || rol.isEmpty()) {
            return rol;
        }
        return rol.substring(0, 1).toUpperCase() + rol.substring(1).toLowerCase();
    }

    // ========== OPCIÓN 3 ==========
    private void contratarParaCancion() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║       CONTRATAR ARTISTAS PARA CANCIÓN ESPECÍFICA       ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");


        System.out.println("Canciones disponibles:");

        List<Cancion> canciones = this.recital.getCancionesLineUp();

        for (int i = 0; i < canciones.size(); i++) {
            System.out.println((i+1) + ". " + canciones.get(i).getNombre());
        }

        System.out.print("\nIngrese la canción: ");
        int cancionOpcion;

        do {
            cancionOpcion = leerOpcion();

            if(cancionOpcion <= 0 || cancionOpcion > canciones.size()) {
                System.out.println("Seleccione una opcion valida.");
                System.out.print("\nSeleccione la cancion: ");
            }
        } while(cancionOpcion <= 0 ||  cancionOpcion > canciones.size());

        Cancion cancion = recital.getCancionesLineUp().get(cancionOpcion - 1);

        if(recital.tieneRolesCubiertos(cancion)) {
            System.out.println("No hay contrataciones restantes para esta canción");
            return;
        }

        System.out.println("Antes de contratacion:");
        System.out.println(recital.imprimirCancion(cancion));

        for (Rol rol : cancion.getRolesFaltantes()) {
            try {
                recital.contratar(recital.getArtistasCandidatos(), cancion, rol);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Contratación exitosa\nEstado de canción despues de contracion:");
        System.out.println(recital.imprimirCancion(cancion));
    }

    // ========== OPCIÓN 4 ==========
    private void contratarParaTodasCanciones() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  CONTRATAR ARTISTAS PARA TODAS LAS CANCIONES");
        System.out.println("═══════════════════════════════════════════════════════\n");

        recital.contratacionMasiva(recital.getArtistasCandidatos());

        System.out.println("Contratacion exitosa");
    }

    // ========== OPCIÓN 5 ==========
   private void entrenarArtista() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║                  ENTRENAR ARTISTA                      ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        System.out.println("Aviso:");
        System.out.println("No se puede entrenar artistas ya contratados");
        System.out.println("No se puede entrenar artistas base");
        System.out.println("Cada rol adicional incrementa el costo en 50%\n");

        System.out.println("Artistas disponibles para entrenar:");

        // == Listamos artistas disponibles
        List<ArtistaCandidato> listaArtistas = new ArrayList<>();
        int cantidad = 1;
        for(Artista a : this.recital.getArtistas()){
            if(a instanceof ArtistaCandidato) {
                listaArtistas.add((ArtistaCandidato) a);
                System.out.println(cantidad + ". " + a.getNombre());
                cantidad++;
            }
        }
        if(listaArtistas.isEmpty()) {
            System.out.println("No hay artistas candidatos disponibles para entrenar!");
            return;
        }

        // == Pedimos artista
        System.out.print("\nSeleccione el artista: ");
        int artistaSeleccion;

        do {
            artistaSeleccion = leerOpcion();

            if(artistaSeleccion <= 0 || artistaSeleccion > listaArtistas.size()) {
                System.out.println("Seleccione una opcion valida.");
                System.out.print("\nSeleccione el artista: ");
            }
        } while(artistaSeleccion <= 0 ||  artistaSeleccion > listaArtistas.size());

        System.out.println("Artista seleccionado:");
        ArtistaCandidato artistaSeleccionado = listaArtistas.get(artistaSeleccion - 1);
        System.out.println(artistaSeleccionado);

        // == Listamos roles disponibles
        List<Rol> roles = new ArrayList<>(Arrays.asList(Rol.class.getEnumConstants()));
        roles.removeAll(artistaSeleccionado.getRoles());

        System.out.println("\nRoles disponibles para aprender:");
        for (int i = 0; i<roles.size(); i++) {
            System.out.println((i+1) + ". " + roles.get(i).toString());
        }

        if (roles.isEmpty()) {
            System.out.println("No hay roles disponibles para aprender!");
            return;
        }

        // == Pedimos rol
        System.out.print("\nSeleccione rol a aprender: ");
        int rolSeleccion;
        do {
            rolSeleccion = leerOpcion();
            if (rolSeleccion > roles.size() || rolSeleccion <= 0) {
                System.out.println("Opcion no valida.");
                System.out.print("\nSeleccione rol a aprender: ");
            }

        } while(rolSeleccion > roles.size() || rolSeleccion <= 0);

        Rol rolSeleccionado = roles.get(rolSeleccion - 1);
        System.out.println("Rol seleccionado: " + rolSeleccionado.toString());

        // == Aviso de incremento de costo
        double costoAnterior = artistaSeleccionado.getCostoPorCancion();
        double nuevoCosto = costoAnterior * 1.5;

        System.out.println("\nCÁLCULO DE NUEVO COSTO:");
        System.out.println("Costo anterior: $" + String.format("%.2f", costoAnterior));
        System.out.println("Incremento (50%): $" + String.format("%.2f", costoAnterior * 0.5));
        System.out.println("Nuevo costo: $" + String.format("%.2f", nuevoCosto));

        System.out.print("\n¿Confirmar entrenamiento? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        // == Entrenamiento
        if (confirmacion.equals("S")) {
            artistaSeleccionado.entrenar(rolSeleccionado);
            System.out.println("\nEntrenamiento completado exitosamente.");
            System.out.println(artistaSeleccionado.getNombre() + " ahora puede desempeñar el rol " + rolSeleccionado);
        } else {
            System.out.println("\nEntrenamiento cancelado.");
        }
    }

    // ========== OPCIÓN 6 ==========
    private void listarArtistasContratados() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║                  ARTISTAS CONTRATADOS                  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        for( Artista a: recital.getArtistas()) {
            if(a instanceof ArtistaBase) {
                System.out.println(a);
            }
        }

    }

    private void listarCanciones() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║                  ESTADO DE CANCIONES                   ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        int completas = 0;
        double costoTotal = 0;

        for (Cancion c : this.recital.getCancionesLineUp()) {
            System.out.println(recital.imprimirCancion(c));

            if (recital.tieneRolesCubiertos(c)) completas++;
        }

        System.out.println("Canciones completas: " + completas + "/" + this.recital.getCancionesLineUp().size());
        System.out.println("Costo total de las canciones: $" + recital.calcularCostoRecital());
    }

    // ========== OPCIÓN 8 ==========
    private void consultasProlog() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║                  CONSULTAS PROLOG                      ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        System.out.println("¿Cuántos entrenamientos mínimos debo realizar para cubrir");
        System.out.println("todos los roles para el recital, utilizando solo los");
        System.out.println("miembros base, y artistas contratados sin experiencia");
        System.out.println("y con un coste base por parámetro, para todos iguales?");

        System.out.println("\nIngrese el costo base: ");
        double costoBase = scanner.nextDouble();
        scanner.nextLine();

        if (costoBase < 0) {
            System.out.println("Costo base no puede ser menor a 0");
            return;
        }

        PrologHelper.cargarDatos(this.recital, costoBase);
    }

    // ========== OPCIÓN 9 ==========
    private void eliminarArtista() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║               QUITAR ARTISTA DE RECITAL                ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        // == Listamos artistas disponibles
        List<Artista> listaArtistas = this.recital.getArtistas();

        for(int i = 0; i < listaArtistas.size(); i++) {
                Artista a = listaArtistas.get(i);
                System.out.println((i + 1) + ". " + a.getNombre());
            }

        if(listaArtistas.isEmpty()) {
            System.out.println("No hay artistas disponibles!");
            return;
        }

        // == Pedimos artista
        System.out.print("\nSeleccione el artista: ");
        int artistaSeleccion;

        do {
            artistaSeleccion = leerOpcion();

            if(artistaSeleccion <= 0 || artistaSeleccion > listaArtistas.size()) {
                System.out.println("Seleccione una opcion valida.");
                System.out.print("\nSeleccione el artista: ");
            }
        } while(artistaSeleccion <= 0 ||  artistaSeleccion > listaArtistas.size());

        System.out.println("Artista seleccionado:");
        Artista artistaSeleccionado = listaArtistas.get(artistaSeleccion - 1);
        System.out.println(artistaSeleccionado);

        System.out.print("\n¿Estas seguro que queres borrar al artista? (S/N): ");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        // == Entrenamiento
        if (confirmacion.equals("S")) {
            recital.eliminarArtista(artistaSeleccionado);
            System.out.println("\nArtista borrado exitosamente.");
        } else {
            System.out.println("\nOperacion cancelada.");
        }

    }

    // ========== OPCIÓN 0 ==========
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

    void procesarOpcion(int opcion) {
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
                try {
                    contratarParaTodasCanciones();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
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
            case 9:
                eliminarArtista();
                break;

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
