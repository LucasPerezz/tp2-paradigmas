
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Recital {
    private final List<Artista> artistas;
    private final List<Cancion> cancionesLineUp;
    private final Set<RelacionArtistaCancion> relacionArtistaCancion;
    // TODO: Calcular descuentos por compartir bandas (estaba en el constructor)

    public Recital(final List<Artista> artistasBase, final List<Cancion> cancionesLineUp) {
        this.artistas = new ArrayList<>(artistasBase);
        this.cancionesLineUp = cancionesLineUp;
        relacionArtistaCancion = new HashSet<>();
    }
    // eliminar artista

    public void asignacionAutomaticaDeCanciones() {
        for (Cancion cancion : cancionesLineUp) {
            for (Rol rol : cancion.getRolesRequeridos()) {
                Artista artista = obtenerArtistaConRol(rol, cancion);
                if (artista == null) {
                    continue;
                }
                relacionArtistaCancion.add(new RelacionArtistaCancion(artista, cancion, rol));
            }
        }
    }

    public Set<ArtistaCandidato> getArtistasCandidatos() {
        Set<ArtistaCandidato> artistasCandidatos = new HashSet<>();
        for (Artista artista : artistas) {
            if(artista instanceof ArtistaCandidato) {
                artistasCandidatos.add((ArtistaCandidato) artista);
            }
        }

        return artistasCandidatos;
    }

    public int rolesFaltantes(final Cancion cancion) {
        final List<Artista> artistasDisponibles = artistaPuedenTocarMas();

        final List<Rol> roles = artistasDisponibles.stream()
                .map(Artista::getRoles)
                .flatMap(List::stream)
                .toList();

        return (int) cancion.getRolesRequeridos().stream()
                .filter(rolRequerido -> !roles.contains(rolRequerido))
                .count();
    }

    public int rolesFaltantesRecital() {
        int rolesFaltantes = 0;

        for (Cancion cancion : cancionesLineUp) {
            List<Rol> rolesCumplidos = rolesCumplidosPorCancion(cancion);

            int cantidadRequeridos = cancion.getRolesRequeridos().size();
            int cantidadCumplidos = rolesCumplidos.size();

            rolesFaltantes += (cantidadRequeridos - cantidadCumplidos);
        }

        return rolesFaltantes;
    }

    List<Rol> rolesCumplidosPorCancion(final Cancion cancion) {
        return relacionArtistaCancion.stream()
                .filter(rel -> rel.getCancion().equals(cancion))
                .map(RelacionArtistaCancion::getRolQueCumple)
                .collect(Collectors.toList());
    }

    public void contratar(final Set<ArtistaCandidato> artistas, final Cancion cancion, final Rol rol) {
        final Set<ArtistaCandidato> artistasPermitdos = artistas.stream()
                .filter(art -> !art.llegoAlMaximo(cancionesInterpretadasPor(art)))
                .collect(Collectors.toSet());

        final Set<ArtistaCandidato> artistasConRol = artistasPermitdos.stream()
                .filter(artista -> artista.getRoles().contains(rol))
                .collect(Collectors.toSet());

        final Set<ArtistaBase> artistaBases = getArtistasBase();

        ArtistaCandidato artistaMasBarato = null;
        double costoMasBarato = 0.0;

        for (ArtistaCandidato artista : artistasConRol) {
            double costo = artista.calcularCosto(artistaBases);
            if (costo < costoMasBarato || artistaMasBarato == null) {
                artistaMasBarato = artista;
                costoMasBarato = costo;
            }
        }

        if (artistaMasBarato == null) {
            throw new RuntimeException("No hay artista con rol " + rol + " que pueda interpretar la cancion " + cancion.getNombre());
        }

        this.artistas.add(artistaMasBarato);
        relacionArtistaCancion.add(new RelacionArtistaCancion(artistaMasBarato, cancion, rol));
        System.out.println(cancion.getNombre() + ": Se contrató a " + artistaMasBarato.getNombre() + " para desempeñar el rol " + rol.toString());
    }

    public void contratacionMasiva(final Set<ArtistaCandidato> artistaCandidatos) {
        for (Cancion cancion : cancionesLineUp) {
            List<Rol> rolesCumplidos = rolesCumplidosPorCancion(cancion);

            Set<Rol> rolesFaltantes = cancion.getRolesRequeridos().stream()
                    .filter(rol -> !rolesCumplidos.contains(rol))
                    .collect(Collectors.toSet());

            for (Rol rol : rolesFaltantes) {
                contratar(artistaCandidatos, cancion, rol);
            }
        }
    }


    private Set<ArtistaBase> getArtistasBase() {
        return artistas.stream()
                .filter(artista -> artista instanceof ArtistaBase)
                .map(ArtistaBase.class::cast)
                .collect(Collectors.toSet());
    }

    private Artista obtenerArtistaConRol(final Rol rol, final Cancion cancion) {
        return artistas.stream()
                .filter(artista -> artista.tieneRol(rol))
                .filter(artista -> !artista.llegoAlMaximo(cancionesInterpretadasPor(artista)))
                .filter(artista -> !relacionEntre(artista, cancion))
                .findFirst()
                .orElse(null);
    }

    private List<Artista> artistaPuedenTocarMas() {
        List<Artista> artistasSobrantes = new ArrayList<>();

        for (Artista artista : artistas) {
            final List<Cancion> cancionesInterpretadas = cancionesInterpretadasPor(artista);
            if (!artista.llegoAlMaximo(cancionesInterpretadas)) {
                artistasSobrantes.add(artista);
            }
        }

        return artistasSobrantes;
    }

    private List<Cancion> cancionesInterpretadasPor(final Artista artista) {
        return relacionArtistaCancion.stream()
                .filter(relacion -> relacion.getArtista().equals(artista))
                .map(RelacionArtistaCancion::getCancion)
                .toList();
    }

    private boolean relacionEntre(final Artista artista, final Cancion cancion) {
        return relacionArtistaCancion.stream()
                .anyMatch(relacion -> relacion.getArtista().equals(artista) && relacion.getCancion().equals(cancion));
    }

    public boolean tieneRolesCubiertos(final Cancion cancion) {
        final List<Rol> rolesCumplidos = rolesCumplidosPorCancion(cancion);
        final List<Rol> rolesRequeridos = cancion.getRolesRequeridos();

        return rolesCumplidos.size() == rolesRequeridos.size() &&
                rolesCumplidos.containsAll(rolesRequeridos);
    }

    public List<Artista> obtenerArtistasAsignados(final Cancion cancion) {
        return relacionArtistaCancion.stream()
                .filter(rel -> rel.getCancion().equals(cancion))
                .map(RelacionArtistaCancion::getArtista)
                .distinct()
                .toList();
    }

    public List<RelacionArtistaCancion> obtenerAsignacionesPorCancion(final Cancion cancion) {
        return relacionArtistaCancion.stream()
                .filter(rel -> rel.getCancion().equals(cancion))
                .toList();
    }

    public double calcularCostoRecital() {
        double costoTotal = 0.0;

        for (Cancion cancion : cancionesLineUp) {
            List<RelacionArtistaCancion> asignaciones = obtenerAsignacionesPorCancion(cancion);

            for (RelacionArtistaCancion asignacion : asignaciones) {
                Artista artista = asignacion.getArtista();
                costoTotal += artista.getCostoPorCancion();
            }
        }

        return costoTotal;
    }

    public String imprimirCancion(Cancion cancion) {

        if (cancion == null || !cancionesLineUp.contains(cancion)) {
            return "Cancion no incluida en recital";
        }

        List<String> lineas = new ArrayList<>();
        List<Rol> rolesRequeridos = cancion.getRolesRequeridos();
        List<Artista> artistasAsignados = obtenerArtistasAsignados(cancion);
        ArrayList<RelacionArtistaCancion> asignaciones = new ArrayList<>(obtenerAsignacionesPorCancion(cancion));

        // Título
        lineas.add("Canción: " + cancion.getNombre());

        // Estado
        lineas.add("Estado: " + (this.tieneRolesCubiertos(cancion) ? "Cubierta" : "No cubierta"));

        for (Rol rol : rolesRequeridos) {
            var artistaOpt = asignaciones.stream()
                    .filter(rel -> rel.getRolQueCumple().equals(rol))
                    .findFirst()
                    .map(RelacionArtistaCancion::getArtista);

            if (artistaOpt.isPresent()) {
                lineas.add(rol.toString() + ": Cubierto por " + artistaOpt.get().getNombre());
                asignaciones.remove(artistaOpt); // Por Roles duplicados
            } else {
                lineas.add(rol.toString() + ": No cubierto");
            }
        }


        // Costo total
        double total = 0;
        for (Artista a : artistasAsignados) {
            if (a != null)
                total += a.getCostoPorCancion();
        }
        lineas.add("Costo por tocar cancion: $" + total);

        // ----------------------------------------------------
        //   Calcular ancho máximo
        // ----------------------------------------------------
        int max = 54;
        for (String l : lineas) {
            if (l.length() > max) max = l.length();
        }

        int anchoInterior = max + 2; // margen interno
        StringBuilder sb = new StringBuilder();

        // Borde superior
        sb.append("┌").append("─".repeat(anchoInterior)).append("┐\n");

        // Contenido
        for (String l : lineas) {
            int padding = anchoInterior - l.length();
            sb.append("│ ").append(l).append(" ".repeat(padding - 1)).append("│\n");
        }

        // Borde inferior
        sb.append("└").append("─".repeat(anchoInterior)).append("┘");

        return sb.toString();
    }

    public HashMap<Rol, Integer> getRolesNecesariosCancion(final Cancion cancion) {
        HashMap<Rol, Integer> rolesNecesarios = new HashMap<>();

        for (Rol rol : cancion.getRolesRequeridos()) {
            rolesNecesarios.put(rol, rolesNecesarios.getOrDefault(rol, 0) + 1);
        }

        return rolesNecesarios;
    }

    public HashMap<Rol, Integer> getRolesCubiertosCancion(final Cancion cancion) {
        HashMap<Rol, Integer> rolesCubiertos = new HashMap<>();

        List<RelacionArtistaCancion> asignaciones = obtenerAsignacionesPorCancion(cancion);
        for (RelacionArtistaCancion asignacion : asignaciones) {
            Rol rol = asignacion.getRolQueCumple();
            rolesCubiertos.put(rol, rolesCubiertos.getOrDefault(rol, 0) + 1);
        }

        return rolesCubiertos;
    }

    public HashSet<ArtistaBase> getArtistasBaseAsignados(final Cancion cancion) {
        return obtenerAsignacionesPorCancion(cancion).stream()
                .map(RelacionArtistaCancion::getArtista)
                .filter(artista -> artista instanceof ArtistaBase)
                .map(artista -> (ArtistaBase) artista)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public RolesFaltantesInfo obtenerRolesFaltantesRecital() {
        HashMap<Rol, Integer> rolesNecesarios = new HashMap<>();
        HashMap<Rol, Integer> rolesCubiertos = new HashMap<>();

        for (Cancion cancion : cancionesLineUp) {
            HashMap<Rol, Integer> rolesNecesariosCancion = getRolesNecesariosCancion(cancion);
            HashMap<Rol, Integer> rolesCubiertosCancion = getRolesCubiertosCancion(cancion);

            for (Rol rol : rolesNecesariosCancion.keySet()) {
                rolesNecesarios.put(rol, rolesNecesarios.getOrDefault(rol, 0) + rolesNecesariosCancion.get(rol));
            }

            for (Rol rol : rolesCubiertosCancion.keySet()) {
                rolesCubiertos.put(rol, rolesCubiertos.getOrDefault(rol, 0) + rolesCubiertosCancion.get(rol));
            }
        }

        HashMap<Rol, Integer> rolesFaltantes = new HashMap<>();
        int totalFaltantes = 0;

        for (Rol rol : rolesNecesarios.keySet()) {
            int necesarios = rolesNecesarios.get(rol);
            int cubiertos = rolesCubiertos.getOrDefault(rol, 0);
            int faltantes = necesarios - cubiertos;

            if (faltantes > 0) {
                rolesFaltantes.put(rol, faltantes);
                totalFaltantes += faltantes;
            }
        }

        int totalNecesarios = rolesNecesarios.values().stream().mapToInt(Integer::intValue).sum();
        int totalCubiertos = rolesCubiertos.values().stream().mapToInt(Integer::intValue).sum();

        return new RolesFaltantesInfo(rolesNecesarios, rolesCubiertos, rolesFaltantes,
                totalNecesarios, totalCubiertos, totalFaltantes);
    }


    /*public RolesFaltantesInfo calcularRolesFaltantesTodas() {
        HashMap<Rol, Integer> rolesNecesarios = new HashMap<>();
        HashMap<Rol, Integer> rolesCubiertos = new HashMap<>();
        HashMap<ArtistaBase, HashSet<Cancion>> cancionesPorArtistaBase = new HashMap<>();

        for (Cancion cancion : cancionesLineUp) {
            HashMap<Rol, Integer> rolesNecesariosCancion = getRolesNecesariosCancion(cancion);
            HashMap<Rol, Integer> rolesCubiertosCancion = getRolesCubiertosCancion(cancion);

            for (Rol rol : rolesNecesariosCancion.keySet()) {
                rolesNecesarios.put(rol,
                        rolesNecesarios.getOrDefault(rol, 0) + rolesNecesariosCancion.get(rol));
            }

            for (Rol rol : rolesCubiertosCancion.keySet()) {
                rolesCubiertos.put(rol,
                        rolesCubiertos.getOrDefault(rol, 0) + rolesCubiertosCancion.get(rol));
            }

            HashSet<ArtistaBase> artistasBaseCancion = getArtistasBaseAsignados(cancion);
            for (ArtistaBase artistaBase : artistasBaseCancion) {
                cancionesPorArtistaBase.putIfAbsent(artistaBase, new HashSet<>());
                cancionesPorArtistaBase.get(artistaBase).add(cancion);
            }
        }

        HashMap<Rol, Integer> capacidadBaseDisponible = new HashMap<>();

        for (Artista artista : artistas) {
            if (artista instanceof ArtistaBase) {
                ArtistaBase artistaBase = (ArtistaBase) artista;
                int cancionesYaAsignadas = cancionesPorArtistaBase.containsKey(artistaBase)
                        ? cancionesPorArtistaBase.get(artistaBase).size() : 0;
                int cancionesDisponibles = artistaBase.getCancionesMaximas() - cancionesYaAsignadas;

                if (cancionesDisponibles > 0) {
                    for (Rol rol : artistaBase.getRoles()) {
                        capacidadBaseDisponible.put(rol,
                                capacidadBaseDisponible.getOrDefault(rol, 0) + cancionesDisponibles);
                    }
                }
            }
        }

        HashMap<Rol, Integer> rolesFaltantes = new HashMap<>();
        int totalNecesarios = 0;
        int totalCubiertos = 0;
        int totalFaltantes = 0;

        Rol[] todosLosRoles = Rol.values();

        for (Rol rol : todosLosRoles) {
            int necesarios = rolesNecesarios.getOrDefault(rol, 0);
            int cubiertos = rolesCubiertos.getOrDefault(rol, 0);
            int capacidadBase = capacidadBaseDisponible.getOrDefault(rol, 0);

            if (necesarios > 0) {
                totalNecesarios += necesarios;
                totalCubiertos += cubiertos;

                int rolesSinCubrir = necesarios - cubiertos;
                int rolesQuePuedeCubrirBase = Math.min(capacidadBase, rolesSinCubrir);
                int faltantes = Math.max(0, rolesSinCubrir - rolesQuePuedeCubrirBase);
                rolesFaltantes.put(rol, faltantes);
                totalFaltantes += faltantes;
            }
        }

        return new RolesFaltantesInfo(rolesNecesarios, rolesCubiertos, rolesFaltantes,
                totalNecesarios, totalCubiertos, totalFaltantes);
    }*/


}
