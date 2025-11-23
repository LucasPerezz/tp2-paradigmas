
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Recital {
    private final List<Artista> artistas;
    private final List<Cancion> cancionesLineUp;
    private final Set<RelacionArtistaCancion> relacionArtistaCancion;
    // TODO: Calcular descuentos por compartir bandas (estaba en el constructor)

    public Recital(final List<ArtistaBase> artistasBase, final List<Cancion> cancionesLineUp) {
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
            Set<Rol> rolesCumplidos = rolesCumplidosPorCancion(cancion);

            int cantidadRequeridos = cancion.getRolesRequeridos().size();
            int cantidadCumplidos = rolesCumplidos.size();

            rolesFaltantes += (cantidadRequeridos - cantidadCumplidos);
        }

        return rolesFaltantes;
    }

    private Set<Rol> rolesCumplidosPorCancion(final Cancion cancion) {
        return relacionArtistaCancion.stream()
                .filter(rel -> rel.getCancion().equals(cancion))
                .map(RelacionArtistaCancion::getRolQueCumple)
                .collect(Collectors.toSet());
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

        final ArtistaContratado artistaContratado = ArtistaContratado.contratar(artistaMasBarato, costoMasBarato);

        final boolean artistaCargado =  this.artistas.stream()
                .anyMatch(art -> art.equals(artistaContratado));

        if(!artistaCargado) { //evito cargar repetidos
            this.artistas.add(artistaContratado);
        }

        relacionArtistaCancion.add(new RelacionArtistaCancion(artistaContratado, cancion, rol));

    }

    public Set<ArtistaContratado> getArtistasContratados() {
       Set<ArtistaContratado> artistasContratados = new HashSet<>();

        for (Artista artista : artistas) {
            if(artista instanceof ArtistaContratado) {
                artistasContratados.add((ArtistaContratado) artista);
            }
        }

        return  artistasContratados;
    }

    public void contratacionMasiva(final Set<ArtistaCandidato> artistaCandidatos) {
        for(Cancion cancion : cancionesLineUp) {
            Set<Rol> rolesCumplidos = rolesCumplidosPorCancion(cancion);

            Set<Rol> rolesFaltantes = cancion.getRolesRequeridos().stream()
                    .filter(rol -> !rolesCumplidos.contains(rol))
                    .collect(Collectors.toSet());

            for(Rol rol : rolesFaltantes) {
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


   /* public RolesFaltantesInfo calcularRolesFaltantesTodas() {
        HashMap<Rol, Integer> rolesNecesarios = new HashMap<>();
        HashMap<Rol, Integer> rolesCubiertos = new HashMap<>();
        HashMap<ArtistaBase, HashSet<Cancion>> cancionesPorArtistaBase = new HashMap<>();
        
        for (Cancion cancion : cancionesLineUp) {
            HashMap<Rol, Integer> rolesNecesariosCancion = cancion.getRolesNecesarios();
            HashMap<Rol, Integer> rolesCubiertosCancion = cancion.getRolesCubiertos();
            
            for (Rol rol : rolesNecesariosCancion.keySet()) {
                rolesNecesarios.put(rol, 
                    rolesNecesarios.getOrDefault(rol, 0) + rolesNecesariosCancion.get(rol));
            }
            
            for (Rol rol : rolesCubiertosCancion.keySet()) {
                rolesCubiertos.put(rol, 
                    rolesCubiertos.getOrDefault(rol, 0) + rolesCubiertosCancion.get(rol));
            }
            
            HashSet<ArtistaBase> artistasBaseCancion = cancion.getArtistasBaseAsignados();
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
