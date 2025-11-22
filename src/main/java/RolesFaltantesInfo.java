import java.util.HashMap;

public class RolesFaltantesInfo {
        private final HashMap<Rol, Integer> rolesNecesarios;
        private final HashMap<Rol, Integer> rolesCubiertos;
        private final HashMap<Rol, Integer> rolesFaltantes;
        private final int totalNecesarios;
        private final int totalCubiertos;
        private final int totalFaltantes;

        public RolesFaltantesInfo(HashMap<Rol, Integer> rolesNecesarios, 
                                 HashMap<Rol, Integer> rolesCubiertos,
                                 HashMap<Rol, Integer> rolesFaltantes,
                                 int totalNecesarios, int totalCubiertos, int totalFaltantes) {
            this.rolesNecesarios = rolesNecesarios;
            this.rolesCubiertos = rolesCubiertos;
            this.rolesFaltantes = rolesFaltantes;
            this.totalNecesarios = totalNecesarios;
            this.totalCubiertos = totalCubiertos;
            this.totalFaltantes = totalFaltantes;
        }

        public HashMap<Rol, Integer> getRolesNecesarios() { return rolesNecesarios; }
        public HashMap<Rol, Integer> getRolesCubiertos() { return rolesCubiertos; }
        public HashMap<Rol, Integer> getRolesFaltantes() { return rolesFaltantes; }
        public int getTotalNecesarios() { return totalNecesarios; }
        public int getTotalCubiertos() { return totalCubiertos; }
        public int getTotalFaltantes() { return totalFaltantes; }
    }