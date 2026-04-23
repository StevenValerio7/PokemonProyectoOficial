
package estructuras;
import Pokemones.*;
import modelo.*;
import batalla.*;
/**
 *
 * @author sebas
 */
public class ArbolTorneo {
 private NodoTorneo raizJugador;
    private NodoTorneo combateActual;
    private int rondaActual;
    private int combateEnRonda;
    private GeneradorAleatorio generador;
    private ListaEquipos equiposJugador;
    private ListaEquipos equiposNPC;
    private String ganadorNPC;
    private String ganadorFinal;
    
    //constructor
    public ArbolTorneo() {
        raizJugador = null;
        combateActual = null;
        rondaActual = 1;
        combateEnRonda = 1;
        generador = new GeneradorAleatorio();
        equiposJugador = new ListaEquipos();
        equiposNPC = new ListaEquipos();
        ganadorNPC = null;
        inicializarTorneo();
    }

    private void inicializarTorneo() {
        raizJugador = new NodoTorneo(generarEquipoEnemigo());
        combateActual = raizJugador;

        equiposJugador.agregarEquipo("Jugador");
        for (int i = 2; i <= 8; i++) {
            equiposJugador.agregarEquipo("Equipo " + i);
        }

        equiposNPC.agregarEquipo("Equipo 9");
        for (int i = 10; i <= 16; i++) {
            equiposNPC.agregarEquipo("Equipo " + i);
        }

        simularRondaNPC();
    }

    private void simularRondaNPC() {
        if (rondaActual == 1) {
            for (int i = 0; i < 8; i += 2) {
                equiposNPC.setEliminado(generador.siguiente(2) == 0 ? i : i + 1, true);
            }
        } else if (rondaActual == 2) {
            for (int i = 0; i < 4; i += 2) {
                equiposNPC.setEliminado(generador.siguiente(2) == 0 ? i : i + 1, true);
            }
        } else if (rondaActual == 3) {
            int ganadorIdx = generador.siguiente(2);
            equiposNPC.setEliminado(ganadorIdx == 0 ? 1 : 0, true);
            ganadorNPC = equiposNPC.getNombre(ganadorIdx);
        }
    }

    private ListaPokemon generarEquipoEnemigo() {
        ListaPokemon equipo = new ListaPokemon();

        for (int i = 0; i < 4; i++) {
            int tipoPokemon = generador.siguiente(9);

            switch (tipoPokemon) {
                case 0: equipo.agregar(new Snorlax()); break;
                case 1: equipo.agregar(new Meowth()); break;
                case 2: equipo.agregar(new Pidgey()); break;
                case 3: equipo.agregar(new Charmander()); break;
                case 4: equipo.agregar(new Growlithe()); break;
                case 5: equipo.agregar(new Magmar()); break;
                case 6: equipo.agregar(new Squirtle()); break;
                case 7: equipo.agregar(new Psyduck()); break;
                case 8: equipo.agregar(new Poliwag()); break;
            }
        }

        return equipo;
    }

    public NodoTorneo getSiguienteCombate() {
        if (combateActual == null) {
            return null;
        }

        if (rondaActual == 1) {
            equiposJugador.setEliminado(1, true);
        } else if (rondaActual == 2) {
            equiposJugador.setEliminado(equiposJugador.getEliminado(2) ? 3 : 2, true);
        } else if (rondaActual == 3) {
            equiposJugador.setEliminado(equiposJugador.getEliminado(4) ? 5 : 4, true);
        }

        combateEnRonda++;
        combateActual = null;
        if (rondaActual == 1) {
            NodoTorneo semi1 = new NodoTorneo(generarEquipoEnemigo());
            raizJugador = semi1;
            combateActual = raizJugador;
            combateEnRonda = 1;
            rondaActual = 2;

            String ganador3vs4 = !equiposJugador.getEliminado(2) ? "Equipo 3" : "Equipo 4";
            String ganador5vs6 = !equiposJugador.getEliminado(4) ? "Equipo 5" : "Equipo 6";
            String ganador7vs8 = !equiposJugador.getEliminado(6) ? "Equipo 7" : "Equipo 8";

            equiposJugador.setNombre(0, "Jugador");
            equiposJugador.setNombre(1, ganador3vs4);
            equiposJugador.setNombre(2, ganador5vs6);
            equiposJugador.setNombre(3, ganador7vs8);

            equiposJugador.setEliminado(generador.siguiente(2) == 0 ? 2 : 3, true);

            simularRondaNPC();
        } else if (rondaActual == 2) {
            NodoTorneo finalCombate = new NodoTorneo(generarEquipoEnemigo());
            raizJugador = finalCombate;
            combateActual = raizJugador;
            combateEnRonda = 1;
            rondaActual = 3;

            String ganador5vs6vs7vs8 = !equiposJugador.getEliminado(2) ? "Equipo 5" : "Equipo 6";

            equiposJugador.setNombre(0, "Jugador");
            equiposJugador.setNombre(1, ganador5vs6vs7vs8);

            simularRondaNPC();
        } else if (rondaActual == 3) {
            NodoTorneo granFinal = new NodoTorneo(generarEquipoEnemigo());
            raizJugador = granFinal;
            combateActual = raizJugador;
            combateEnRonda = 1;
            rondaActual = 4;

            String ganadorNPCFinal = !equiposNPC.getEliminado(0) ? "Equipo 9" : !equiposNPC.getEliminado(1) ? "Equipo 10"
                    : !equiposNPC.getEliminado(2) ? "Equipo 11" : !equiposNPC.getEliminado(3) ? "Equipo 12"
                    : !equiposNPC.getEliminado(4) ? "Equipo 13" : !equiposNPC.getEliminado(5) ? "Equipo 14"
                    : !equiposNPC.getEliminado(6) ? "Equipo 15" : "Equipo 16";

            equiposJugador.setNombre(0, "Jugador");
            equiposJugador.setNombre(1, ganadorNPCFinal);
        } else {
            return null;
        }

        return combateActual;
    }
    

public void setGanadorFinal(String ganador) {
    this.ganadorFinal = ganador;
}

public String getGanadorFinal() {
    return ganadorFinal;
}


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
        return raizJugador;
    }

    public ListaEquipos getEquiposJugador() {
        return equiposJugador;
    }

    public ListaEquipos getEquiposNPC() {
        return equiposNPC;
    }

    public int getCombateEnRonda() {
        return combateEnRonda;
    }
    
   
}
    

