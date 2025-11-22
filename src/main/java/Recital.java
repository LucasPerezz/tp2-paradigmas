
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Recital {
    private final List<Artista> artistas;
    private final List<Cancion> cancionesLineUp;
    private final Map<Artista, List<Cancion>> cancionesPorArtista;
    // TODO: Calcular descuentos por compartir bandas (estaba en el constructor)

    public  Recital(final List<Artista> artistas, final List<Cancion> cancionesLineUp) {
        this.artistas = artistas;
        this.cancionesLineUp = cancionesLineUp;
        cancionesPorArtista = new HashMap<>();
    }
    // eliminar artista

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

    public void asignacionAutomaticaDeCanciones(){
        for (Cancion cancion : cancionesLineUp) {
            for (Rol rol : cancion.getRolesRequeridos()){
                Artista artista = obtenerArtistaConRol(rol, cancion);
                if(artista == null){
                    continue;
                }

                if (!cancionesPorArtista.containsKey(artista)){
                    List<Cancion> cancionesAsiganadas = new ArrayList<>();
                    cancionesPorArtista.put(artista, cancionesAsiganadas);
                }

                List<Cancion> cancionesAsignadas = cancionesPorArtista.get(artista);
                if (!cancionesAsignadas.contains(cancion)) {
                    cancionesAsignadas.add(cancion);
                }
            }
        }
    }

    private Artista obtenerArtistaConRol(final Rol rol, final Cancion cancion) {
        return artistas.stream()
                .filter(artista -> artista.tieneRol(rol))
                .filter(artista -> !artista.llegoAlMaximo(cancionesPorArtista.getOrDefault(artista, List.of())))
                .filter(artista -> !cancionesPorArtista.getOrDefault(artista, List.of()).contains(cancion))
                .findFirst()
                .orElse(null);
    }

    private List<Artista> artistaPuedenTocarMas(){
        List<Artista> artistasSobrantes = new ArrayList<>();

        for(Artista artista : artistas){
            if (!artista.llegoAlMaximo(cancionesPorArtista.getOrDefault(artista, List.of()))){
                artistasSobrantes.add(artista);
            }
        }

        return artistasSobrantes;
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
