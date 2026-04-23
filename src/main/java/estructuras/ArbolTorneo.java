package estructuras;

import Pokemones.*;
import batalla.GeneradorAleatorio;
import modelo.Pokemon;

public class ArbolTorneo {
    private NodoTorneo combateActual;
    private int rondaActual;
    private int combateEnRonda;
    private GeneradorAleatorio generador;
    private ListaEquipos equipos;
    private String ganadorFinal;
    private String nombreJugador;
    private boolean torneoTerminado;
    private int ganadorCuartos1;
    private int ganadorCuartos2;
    private int ganadorCuartos3;
    private int ganadorCuartos4;
    private int ganadorSemis1;
    private int ganadorSemis2;
    private int ganadorFinalIdx;

    public ArbolTorneo() {
        this("Jugador");
    }

    public ArbolTorneo(String nombreJugador) {
        rondaActual = 1;
        combateEnRonda = 1;
        generador = new GeneradorAleatorio();
        equipos = new ListaEquipos();
        ganadorFinal = null;
        torneoTerminado = false;
        ganadorCuartos1 = -1;
        ganadorCuartos2 = -1;
        ganadorCuartos3 = -1;
        ganadorCuartos4 = -1;
        ganadorSemis1 = -1;
        ganadorSemis2 = -1;
        ganadorFinalIdx = -1;
        this.nombreJugador = (nombreJugador == null || nombreJugador.trim().isEmpty()) ? "Jugador" : nombreJugador.trim();
        inicializarTorneo();
    }

    private void inicializarTorneo() {
        combateActual = new NodoTorneo(generarEquipoEnemigo());

        equipos.agregarEquipo(nombreJugador);
        for (int i = 2; i <= 8; i++) {
            equipos.agregarEquipo("Equipo " + i);
        }

        // No simular llaves CPU hasta que el jugador avance
    }

    private void resolverCuartosCPU() {
        resolverCuartoCPU(2, 3, 2);
        resolverCuartoCPU(4, 5, 3);
        resolverCuartoCPU(6, 7, 4);
    }

    private void resolverSemifinalCPU() {
        if (ganadorCuartos3 < 0 || ganadorCuartos4 < 0) {
            resolverCuartosCPU();
        }
        if (ganadorSemis2 < 0 && ganadorCuartos3 >= 0 && ganadorCuartos4 >= 0) {
            int ganador = elegirGanadorAleatorio(ganadorCuartos3, ganadorCuartos4);
            int perdedor = (ganador == ganadorCuartos3) ? ganadorCuartos4 : ganadorCuartos3;
            equipos.setEliminado(perdedor, true);
            ganadorSemis2 = ganador;
        }
    }

    private void eliminarAleatorio(int idxA, int idxB) {
        if (generador.siguiente(2) == 0) {
            equipos.setEliminado(idxA, true);
        } else {
            equipos.setEliminado(idxB, true);
        }
    }

    private int obtenerGanadorIndice(int idxA, int idxB) {
        boolean eliminadoA = equipos.getEliminado(idxA);
        boolean eliminadoB = equipos.getEliminado(idxB);
        if (eliminadoA && !eliminadoB) {
            return idxB;
        }
        if (!eliminadoA && eliminadoB) {
            return idxA;
        }
        return -1;
    }

    private void resolverEnfrentamiento(int idxA, int idxB) {
        if (idxA == idxB) {
            return;
        }
        boolean eliminadoA = equipos.getEliminado(idxA);
        boolean eliminadoB = equipos.getEliminado(idxB);
        if (!eliminadoA && !eliminadoB) {
            eliminarAleatorio(idxA, idxB);
        }
    }

    private int elegirGanadorAleatorio(int idxA, int idxB) {
        if (generador.siguiente(2) == 0) {
            return idxA;
        }
        return idxB;
    }

    private void setGanadorCuartos(int numero, int ganadorIdx) {
        if (numero == 1) ganadorCuartos1 = ganadorIdx;
        if (numero == 2) ganadorCuartos2 = ganadorIdx;
        if (numero == 3) ganadorCuartos3 = ganadorIdx;
        if (numero == 4) ganadorCuartos4 = ganadorIdx;
    }

    private int resolverCuartoCPU(int idxA, int idxB, int numero) {
        int actual = getGanadorCuartos(numero);
        if (actual >= 0) {
            return actual;
        }
        int ganador = elegirGanadorAleatorio(idxA, idxB);
        int perdedor = (ganador == idxA) ? idxB : idxA;
        equipos.setEliminado(perdedor, true);
        setGanadorCuartos(numero, ganador);
        return ganador;
    }

    private int getGanadorCuartos(int numero) {
        if (numero == 1) return ganadorCuartos1;
        if (numero == 2) return ganadorCuartos2;
        if (numero == 3) return ganadorCuartos3;
        if (numero == 4) return ganadorCuartos4;
        return -1;
    }

    private void resolverSemisDesdeCuartos() {
        if (ganadorSemis1 < 0 && ganadorCuartos1 >= 0 && ganadorCuartos2 >= 0) {
            int ganador = elegirGanadorAleatorio(ganadorCuartos1, ganadorCuartos2);
            int perdedor = (ganador == ganadorCuartos1) ? ganadorCuartos2 : ganadorCuartos1;
            equipos.setEliminado(perdedor, true);
            ganadorSemis1 = ganador;
        }
        if (ganadorSemis2 < 0 && ganadorCuartos3 >= 0 && ganadorCuartos4 >= 0) {
            int ganador = elegirGanadorAleatorio(ganadorCuartos3, ganadorCuartos4);
            int perdedor = (ganador == ganadorCuartos3) ? ganadorCuartos4 : ganadorCuartos3;
            equipos.setEliminado(perdedor, true);
            ganadorSemis2 = ganador;
        }
    }

    private void resolverFinalDesdeSemis() {
        if (ganadorFinalIdx >= 0) {
            return;
        }
        if (ganadorSemis1 >= 0 && ganadorSemis2 >= 0) {
            int ganador = elegirGanadorAleatorio(ganadorSemis1, ganadorSemis2);
            int perdedor = (ganador == ganadorSemis1) ? ganadorSemis2 : ganadorSemis1;
            equipos.setEliminado(perdedor, true);
            ganadorFinalIdx = ganador;
            ganadorFinal = equipos.getNombre(ganadorFinalIdx);
            torneoTerminado = true;
        }
    }

    private void completarResultadosSiFaltan() {
        if (ganadorCuartos1 < 0) {
            ganadorCuartos1 = equipos.getEliminado(0) ? 1 : 0;
        }
        if (ganadorCuartos2 < 0 || ganadorCuartos3 < 0 || ganadorCuartos4 < 0) {
            resolverCuartosCPU();
        }
        if (ganadorSemis1 < 0 && ganadorCuartos1 >= 0 && ganadorCuartos2 >= 0) {
            int ganador = elegirGanadorAleatorio(ganadorCuartos1, ganadorCuartos2);
            int perdedor = (ganador == ganadorCuartos1) ? ganadorCuartos2 : ganadorCuartos1;
            equipos.setEliminado(perdedor, true);
            ganadorSemis1 = ganador;
        }
        if (ganadorSemis2 < 0 && ganadorCuartos3 >= 0 && ganadorCuartos4 >= 0) {
            int ganador = elegirGanadorAleatorio(ganadorCuartos3, ganadorCuartos4);
            int perdedor = (ganador == ganadorCuartos3) ? ganadorCuartos4 : ganadorCuartos3;
            equipos.setEliminado(perdedor, true);
            ganadorSemis2 = ganador;
        }
        if (ganadorFinalIdx < 0) {
            resolverFinalDesdeSemis();
        }
        if (ganadorFinal == null || ganadorFinal.trim().isEmpty()) {
            if (ganadorFinalIdx >= 0) {
                ganadorFinal = equipos.getNombre(ganadorFinalIdx);
            }
        }
        if (ganadorFinal != null && !ganadorFinal.trim().isEmpty()) {
            torneoTerminado = true;
        }
    }

    private ListaPokemon generarEquipoEnemigo() {
        ListaPokemon equipo = new ListaPokemon();
        int agregados = 0;
        int intentos = 0;
        while (agregados < 4 && intentos < 200) {
            int tipoPokemon = generador.siguiente(9);
            Pokemon candidato = crearPokemonPorIndice(tipoPokemon);
            if (candidato != null) {
                String nombre = candidato.getNombre();
                if (contarEnEquipo(equipo, nombre) < 2) {
                    equipo.agregar(candidato);
                    agregados++;
                }
            }
            intentos++;
        }
        if (agregados < 4) {
            int idx = 0;
            while (agregados < 4 && idx < 9) {
                Pokemon candidato = crearPokemonPorIndice(idx);
                if (candidato != null) {
                    String nombre = candidato.getNombre();
                    if (contarEnEquipo(equipo, nombre) < 2) {
                        equipo.agregar(candidato);
                        agregados++;
                    }
                }
                idx++;
            }
        }
        return equipo;
    }

    private Pokemon crearPokemonPorIndice(int tipoPokemon) {
        switch (tipoPokemon) {
            case 0: return new Snorlax();
            case 1: return new Meowth();
            case 2: return new Pidgey();
            case 3: return new Charmander();
            case 4: return new Growlithe();
            case 5: return new Magmar();
            case 6: return new Squirtle();
            case 7: return new Psyduck();
            case 8: return new Poliwag();
            default: return null;
        }
    }

    private int contarEnEquipo(ListaPokemon equipo, String nombre) {
        int contador = 0;
        NodoPokemon actual = equipo.getCabeza();
        while (actual != null) {
            if (actual.pokemon != null && actual.pokemon.getNombre().equals(nombre)) {
                contador++;
            }
            actual = actual.siguiente;
        }
        return contador;
    }

    public NodoTorneo getSiguienteCombate() {
        if (combateActual == null) {
            return null;
        }

        if (rondaActual == 1) {
            equipos.setEliminado(1, true);
            ganadorCuartos1 = 0;
            resolverCuartosCPU();
            rondaActual = 2;
            combateEnRonda = 1;
            combateActual = new NodoTorneo(generarEquipoEnemigo());
            return combateActual;
        }

        if (rondaActual == 2) {
            if (ganadorCuartos2 < 0) {
                resolverCuartosCPU();
            }
            if (ganadorCuartos2 >= 0) {
                equipos.setEliminado(ganadorCuartos2, true);
                ganadorSemis1 = 0;
            }
            resolverSemifinalCPU();
            rondaActual = 3;
            combateEnRonda = 1;
            combateActual = new NodoTorneo(generarEquipoEnemigo());
            return combateActual;
        }

        if (rondaActual == 3) {
            ganadorFinalIdx = 0;
            ganadorFinal = nombreJugador;
            torneoTerminado = true;
            rondaActual = 4;
            combateEnRonda = 1;
            combateActual = null;
            return null;
        }

        return null;
    }

    public void setGanadorFinal(String ganador) {
        this.ganadorFinal = ganador;
        this.torneoTerminado = (ganador != null && !ganador.trim().isEmpty());
    }

    public void limpiarGanador() {
        this.ganadorFinal = null;
        this.torneoTerminado = false;
        ganadorCuartos1 = -1;
        ganadorCuartos2 = -1;
        ganadorCuartos3 = -1;
        ganadorCuartos4 = -1;
        ganadorSemis1 = -1;
        ganadorSemis2 = -1;
        ganadorFinalIdx = -1;
    }

    private String obtenerPrimerNoEliminado() {
        int total = equipos.getTamano();
        for (int i = 0; i < total; i++) {
            if (!equipos.getEliminado(i)) {
                String nombre = equipos.getNombre(i);
                if (nombre != null && !nombre.trim().isEmpty()) {
                    return nombre;
                }
            }
        }
        return "CPU";
    }

    public void finalizarTorneoPorDerrota(String ganador) {
        equipos.setEliminado(0, true);

        if (rondaActual == 1) {
            ganadorCuartos1 = 1;
            resolverCuartosCPU();
            resolverSemisDesdeCuartos();
            resolverFinalDesdeSemis();
        } else if (rondaActual == 2) {
            if (ganadorCuartos2 < 0) {
                resolverCuartosCPU();
            }
            ganadorSemis1 = ganadorCuartos2;
            resolverSemifinalCPU();
            resolverFinalDesdeSemis();
        } else if (rondaActual == 3) {
            if (ganadorSemis2 < 0) {
                resolverSemifinalCPU();
            }
            ganadorFinalIdx = ganadorSemis2;
            if (ganadorFinalIdx >= 0) {
                ganadorFinal = equipos.getNombre(ganadorFinalIdx);
                torneoTerminado = true;
            }
        } else {
            resolverCuartosCPU();
            resolverSemisDesdeCuartos();
            resolverFinalDesdeSemis();
        }

        completarResultadosSiFaltan();
        if (ganadorFinal == null || ganadorFinal.trim().isEmpty()) {
            ganadorFinal = "CPU";
            torneoTerminado = true;
        }
        rondaActual = 4;
        combateEnRonda = 1;
        torneoTerminado = true;
    }

    public String getGanadorFinal() {
        return ganadorFinal;
    }

    public boolean isTorneoTerminado() {
        return torneoTerminado;
    }

    public int getGanadorCuartos1() { return ganadorCuartos1; }
    public int getGanadorCuartos2() { return ganadorCuartos2; }
    public int getGanadorCuartos3() { return ganadorCuartos3; }
    public int getGanadorCuartos4() { return ganadorCuartos4; }
    public int getGanadorSemis1() { return ganadorSemis1; }
    public int getGanadorSemis2() { return ganadorSemis2; }
    public int getGanadorFinalIdx() { return ganadorFinalIdx; }

    public ListaPokemon getEquipoActualCPU() {
        if (combateActual != null) {
            return combateActual.getCombateActual();
        }
        return null;
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public NodoTorneo getRaizJugador() {
        return combateActual;
    }

    public ListaEquipos getEquiposJugador() {
        return equipos;
    }

    public int getCombateEnRonda() {
        return combateEnRonda;
    } 
}

