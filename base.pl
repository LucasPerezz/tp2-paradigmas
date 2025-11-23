% ============================================================================
% OPTIMIZADOR DE ENTRENAMIENTOS PARA RECITAL
% Encuentra el número mínimo de entrenamientos para cubrir todos los roles
% faltantes en todas las canciones, considerando reutilización de artistas
% ============================================================================
% --- PREDICADOS AUXILIARES ---

% Obtiene todos los artistas disponibles
artista_disponible(Artista) :-
    artista(Artista, _).

% Obtiene todos los roles necesarios en el recital
todos_los_roles(Roles) :-
    setof(Rol, Cancion^rol_requerido(Cancion, Rol), Roles).

% Verifica si un rol está cubierto naturalmente en una canción
rol_cubierto_naturalmente(Cancion, Rol) :-
    artista_asignado(Cancion, Artista),
    rol_natural(Artista, Rol).

% Obtiene los roles faltantes en una canción (no cubiertos naturalmente)
roles_faltantes(Cancion, RolesFaltantes) :-
    setof(Rol, (
        rol_requerido(Cancion, Rol),
        \+ rol_cubierto_naturalmente(Cancion, Rol)
    ), RolesFaltantes), !.
roles_faltantes(_, []).

% Obtiene todos los roles faltantes en el recital (unión de todos)
todos_roles_faltantes(RolesFaltantes) :-
    setof(Rol, Cancion^(
        rol_requerido(Cancion, Rol),
        \+ rol_cubierto_naturalmente(Cancion, Rol)
    ), RolesFaltantes), !.
todos_roles_faltantes([]).

% Obtiene los artistas no asignados a una canción
artistas_no_asignados(Cancion, ArtistasNoAsignados) :-
    findall(Artista, (
        artista_disponible(Artista),
        \+ artista_asignado(Cancion, Artista)
    ), ArtistasNoAsignados).

% Verifica si un artista puede ser entrenado para cubrir un rol específico
puede_entrenar_para(Artista, Rol) :-
    artista_disponible(Artista),
    \+ rol_natural(Artista, Rol).

% Obtiene los candidatos para entrenar en un rol específico
candidatos_para_rol(Cancion, Rol, Candidatos) :-
    artistas_no_asignados(Cancion, ArtistasNoAsignados),
    findall(Artista, (
        member(Artista, ArtistasNoAsignados),
        puede_entrenar_para(Artista, Rol)
    ), Candidatos).

% --- SOLUCIÓN PRINCIPAL ---

% Encuentra una asignación mínima de entrenamientos
% Estrategia: para cada rol faltante, asignar UN artista no utilizado
% Si el rol aparece en múltiples canciones, el mismo artista entrenado lo cubre todo

entrenamientos_minimos(Entrenamientos, CostoTotal, Plan) :-
    todos_roles_faltantes(RolesFaltantes),
    % Para cada rol faltante, encontrar quién entrenarlo
    resolver_roles_faltantes(RolesFaltantes, [], Plan),
    length(Plan, Entrenamientos),
    calcular_costo_entrenamientos(Plan, CostoTotal).

% Resuelve recursivamente cada rol faltante
resolver_roles_faltantes([], Acumulador, Acumulador).
resolver_roles_faltantes([Rol | RestoRoles], Acumulador, Plan) :-
    % Verificar si ya tenemos alguien entrenado para este rol
    (   member(entrenamiento(_, Rol, _), Acumulador)
    ->  % Ya está cubierto, pasar al siguiente rol
        resolver_roles_faltantes(RestoRoles, Acumulador, Plan)
    ;   % Necesitamos entrenar a alguien para este rol
        encontrar_candidato(Rol, Artista),
        Entrenamiento = entrenamiento(Artista, Rol, Cancion),
        obtener_cancion_para_rol(Rol, Cancion),
        NuevoAcumulador = [Entrenamiento | Acumulador],
        resolver_roles_faltantes(RestoRoles, NuevoAcumulador, Plan)
    ).

% Encuentra un candidato para entrenar en un rol
% Prioriza: artistas no asignados en canciones que necesitan este rol
encontrar_candidato(Rol, Artista) :-
    rol_requerido(Cancion, Rol),
    \+ rol_cubierto_naturalmente(Cancion, Rol),
    artistas_no_asignados(Cancion, Candidatos),
    member(Artista, Candidatos),
    puede_entrenar_para(Artista, Rol), !.

% Obtiene una canción que requiere este rol
obtener_cancion_para_rol(Rol, Cancion) :-
    rol_requerido(Cancion, Rol),
    \+ rol_cubierto_naturalmente(Cancion, Rol), !.



% Calcula potencia: base^exponente (con decimales)
potencia_decimal(_, 0, 1.0) :- !.
potencia_decimal(Base, Exp, Resultado) :-
    Exp > 0,
    Exp1 is Exp - 1,
    potencia_decimal(Base, Exp1, ResultadoAux),
    Resultado is Base * ResultadoAux.

% Calcula costo = suma de (costo_parametro * (1.5 ^ entrenamientos_del_artista))
calcular_costo_entrenamientos(Plan, CostoTotal) :-
    costo_parametro(CostoParam),
    % Obtener artistas únicos en el plan
    findall(Artista, member(entrenamiento(Artista, _, _), Plan), ArtistasConDups),
    sort(ArtistasConDups, ArtistasUnicos),
    % Para cada artista, calcular costo
    findall(Costo, (
        member(Artista, ArtistasUnicos),
        % Contar SOLO los entrenamientos de ESTE artista
        findall(1, member(entrenamiento(Artista, _, _), Plan), EntrenamientosEsteArtista),
        length(EntrenamientosEsteArtista, NumEnt),
        % Costo = CostoParam * (1.5 ^ NumEnt)
        potencia_decimal(1.5, NumEnt, Multiplicador),
        Costo is CostoParam * Multiplicador
    ), Costos),
    sumlist(Costos, CostoTotal).

% --- ANÁLISIS Y DIAGNÓSTICO ---

% Muestra el análisis completo del recital
analizar_recital :-
    write('=== ANÁLISIS DEL RECITAL ==='), nl, nl,
    write('Canciones y roles requeridos:'), nl,
    forall(
        cancion(Cancion),
        (   format('  ~w:~n', [Cancion]),
            setof(Rol, rol_requerido(Cancion, Rol), Roles),
            write('    Roles: '), write(Roles), nl,
            write('    Asignados: '),
            (   setof(Art, artista_asignado(Cancion, Art), Artistas)
            ->  write(Artistas)
            ;   write('ninguno')
            ), nl,
            write('    Roles faltantes: '),
            (   roles_faltantes(Cancion, Faltantes)
            ->  write(Faltantes)
            ;   write('ninguno')
            ), nl
        )
    ), nl.

% Muestra la solución
mostrar_solucion :-
    write('=== SOLUCIÓN: ENTRENAMIENTOS MÍNIMOS ==='), nl, nl,
    (   entrenamientos_minimos(NumEnt, Costo, Plan)
    ->  format('Entrenamientos necesarios: ~w~n', [NumEnt]),
        format('Costo total: ~w~n', [Costo]), nl,
        write('Plan de entrenamientos:'), nl,
        forall(
            member(entrenamiento(Artista, Rol, Cancion), Plan),
            format('  - Entrenar a ~w en ~w (necesario en ~w)~n', [Artista, Rol, Cancion])
        )
    ;   write('No se encontró solución')
    ), nl.