package batalla;

import modelo.Jugador;
import modelo.JugadorCPU;
import modelo.Pokemon;

public class ManejoBatalla {
    private Jugador jugador;
    private JugadorCPU cpu;

    private boolean jugadorDefendiendo = false;
    private boolean cpuDefendiendo = false;
    private boolean jugadorDefendiendoEspecial = false;
    private boolean cpuDefendiendoEspecial = false;

    private int ultimaAccionJugador = 0;
    private int ultimaAccionCPU = 0;

    private static class ResultadoDanio {
        int danio;
        boolean critico;

        ResultadoDanio(int danio, boolean critico) {
            this.danio = danio;
            this.critico = critico;
        }
    }

    public ManejoBatalla(Jugador jugador, JugadorCPU cpu) {
        this.jugador = jugador;
        this.cpu = cpu;
    }

    private void reducirCooldowns(Pokemon p) {
        if (p.getCooldownAtaqueEspecial() > 0) {
            p.setCooldownAtaqueEspecial(p.getCooldownAtaqueEspecial() - 1);
        }
        if (p.getCooldownDefensaEspecial() > 0) {
            p.setCooldownDefensaEspecial(p.getCooldownDefensaEspecial() - 1);
        }
    }

    private ResultadoDanio calcularDanio(Pokemon atacante, Pokemon defensor, boolean especial) {
        int base = especial ? atacante.ataqueEspecial(defensor) : atacante.atacar(defensor);
        double factor = 0.9 + (Math.random() * 0.2);
        int variado = (int) Math.round(base * factor);
        boolean critico = Math.random() < 0.1;
        if (critico) {
            variado = (int) Math.round(variado * 1.5);
        }
        return new ResultadoDanio(variado, critico);
    }

    private int aplicarDefensa(Pokemon defensor, Pokemon atacante, int danio, boolean especial) {
        int reducido;
        if (especial) {
            reducido = danio - defensor.defensaEspecial(atacante);
        } else {
            reducido = defensor.defender(danio);
        }
        if (reducido < 0) {
            reducido = 0;
        }
        return reducido;
    }

    public String turnoJugador(int opcion) {
        Pokemon atacante = jugador.getPokemonActivo();
        Pokemon defensor = cpu.getPokemonActivo();

        String resultado = "";
        if (atacante == null || defensor == null) return resultado;

        ultimaAccionJugador = opcion;

        switch (opcion) {
            case 1:
                if (Math.random() < 0.2) {
                    resultado += atacante.getNombre() + " fallo el ataque.\n";
                } else {
                    ResultadoDanio res = calcularDanio(atacante, defensor, false);
                    boolean usoDefEsp = cpuDefendiendoEspecial;
                    boolean usoDef = cpuDefendiendo;
                    if (usoDefEsp) {
                        cpuDefendiendoEspecial = false;
                        resultado += "(CPU se defendio especial) ";
                    } else if (usoDef) {
                        cpuDefendiendo = false;
                        resultado += "(CPU se defendio) ";
                    }
                    int danioFinal = res.danio;
                    if (usoDefEsp || usoDef) {
                        danioFinal = aplicarDefensa(defensor, atacante, res.danio, usoDefEsp);
                    }
                    defensor.recibirDa_io(danioFinal);
                    if (res.critico) {
                        resultado += "¡Golpe critico! ";
                    }
                    resultado += atacante.getNombre() + " ataco causando " + danioFinal + " de danio.\n";
                }
                break;
            case 2:
                if (atacante.getCooldownAtaqueEspecial() > 0) {
                    resultado += "Ataque especial en cooldown.\n";
                } else {
                    if (Math.random() < 0.2) {
                        resultado += atacante.getNombre() + " fallo el ataque especial.\n";
                    } else {
                        ResultadoDanio res = calcularDanio(atacante, defensor, true);
                        boolean usoDefEsp = cpuDefendiendoEspecial;
                        boolean usoDef = cpuDefendiendo;
                        if (usoDefEsp) {
                            cpuDefendiendoEspecial = false;
                            resultado += "(CPU se defendio especial) ";
                        } else if (usoDef) {
                            cpuDefendiendo = false;
                            resultado += "(CPU se defendio) ";
                        }
                        int danioFinal = res.danio;
                        if (usoDefEsp || usoDef) {
                            danioFinal = aplicarDefensa(defensor, atacante, res.danio, usoDefEsp);
                        }
                        defensor.recibirDa_io(danioFinal);
                        if (res.critico) {
                            resultado += "¡Golpe critico! ";
                        }
                        resultado += atacante.getNombre() + " uso ataque especial causando " + danioFinal + " de danio.\n";
                        atacante.setCooldownAtaqueEspecial(2);
                    }
                }
                break;
            case 3:
                jugadorDefendiendo = true;
                jugadorDefendiendoEspecial = false;
                resultado += atacante.getNombre() + " se esta defendiendo.\n";
                break;
            case 4:
                if (atacante.getCooldownDefensaEspecial() > 0) {
                    resultado += "Defensa especial en cooldown.\n";
                } else {
                    jugadorDefendiendo = false;
                    jugadorDefendiendoEspecial = true;
                    resultado += atacante.getNombre() + " activo defensa especial.\n";
                    atacante.setCooldownDefensaEspecial(2);
                }
                break;
            case 5:
                resultado += jugador.cambiarPokemon();
                break;
            default:
                break;
        }

        if (!defensor.estaVivo()) {
            cpu.pokemonDerrotado();
            cpuDefendiendo = false;
            cpuDefendiendoEspecial = false;
            resultado += defensor.getNombre() + " fue derrotado.\n";
        }
        reducirCooldowns(atacante);
        return resultado;
    }

    public String cambiarPokemonJugador(String nombrePokemon) {
        Pokemon actual = jugador.getPokemonActivo();
        ultimaAccionJugador = 5;
        if (actual == null) {
            return "No hay Pokemon activo.\n";
        }
        if (actual.getNombre().equals(nombrePokemon)) {
            return "Ese Pokemon ya esta activo.\n";
        }
        boolean cambio = jugador.cambiarPokemonA(nombrePokemon);
        if (cambio) {
            return jugador.getNombre() + " retira a " + actual.getNombre() + ". Adelante " + jugador.getPokemonActivo().getNombre() + ".\n";
        }
        return "No se pudo cambiar de Pokemon.\n";
    }

    private int elegirAccionCPU(Pokemon atacante) {
        int hp = atacante.getHp();
        int hpMax = atacante.getHpMaximo();
        if (hpMax > 0 && hp < (hpMax * 35) / 100 && atacante.getCooldownDefensaEspecial() == 0) {
            return 4;
        }
        if (atacante.getCooldownAtaqueEspecial() == 0 && Math.random() < 0.5) {
            return 2;
        }
        if (Math.random() < 0.2) {
            return 3;
        }
        return 1;
    }

    public String turnoCPU() {
        Pokemon atacante = cpu.getPokemonActivo();
        Pokemon defensor = jugador.getPokemonActivo();

        String resultado = "";
        if (atacante == null || defensor == null) return resultado;

        int opcion = elegirAccionCPU(atacante);
        ultimaAccionCPU = opcion;

        switch (opcion) {
            case 1:
                if (Math.random() < 0.2) {
                    resultado += atacante.getNombre() + " fallo el ataque.\n";
                } else {
                    ResultadoDanio res = calcularDanio(atacante, defensor, false);
                    boolean usoDefEsp = jugadorDefendiendoEspecial;
                    boolean usoDef = jugadorDefendiendo;
                    if (usoDefEsp) {
                        jugadorDefendiendoEspecial = false;
                        resultado += "(Jugador se defendio especial) ";
                    } else if (usoDef) {
                        jugadorDefendiendo = false;
                        resultado += "(Jugador se defendio) ";
                    }
                    int danioFinal = res.danio;
                    if (usoDefEsp || usoDef) {
                        danioFinal = aplicarDefensa(defensor, atacante, res.danio, usoDefEsp);
                    }
                    defensor.recibirDa_io(danioFinal);
                    if (res.critico) {
                        resultado += "¡Golpe critico! ";
                    }
                    resultado += atacante.getNombre() + " ataco causando " + danioFinal + " de danio.\n";
                }
                break;
            case 2:
                if (atacante.getCooldownAtaqueEspecial() == 0) {
                    if (Math.random() < 0.2) {
                        resultado += atacante.getNombre() + " fallo el ataque especial.\n";
                    } else {
                        ResultadoDanio res = calcularDanio(atacante, defensor, true);
                        boolean usoDefEsp = jugadorDefendiendoEspecial;
                        boolean usoDef = jugadorDefendiendo;
                        if (usoDefEsp) {
                            jugadorDefendiendoEspecial = false;
                            resultado += "(Jugador se defendio especial) ";
                        } else if (usoDef) {
                            jugadorDefendiendo = false;
                            resultado += "(Jugador se defendio) ";
                        }
                        int danioFinal = res.danio;
                        if (usoDefEsp || usoDef) {
                            danioFinal = aplicarDefensa(defensor, atacante, res.danio, usoDefEsp);
                        }
                        defensor.recibirDa_io(danioFinal);
                        if (res.critico) {
                            resultado += "¡Golpe critico! ";
                        }
                        resultado += atacante.getNombre() + " uso ataque especial causando " + danioFinal + " de danio.\n";
                        atacante.setCooldownAtaqueEspecial(2);
                    }
                } else {
                    resultado += atacante.getNombre() + " no pudo usar ataque especial.\n";
                }
                break;
            case 3:
                cpuDefendiendo = true;
                cpuDefendiendoEspecial = false;
                resultado += atacante.getNombre() + " se esta defendiendo.\n";
                break;
            case 4:
                if (atacante.getCooldownDefensaEspecial() > 0) {
                    resultado += "Defensa especial en cooldown.\n";
                } else {
                    cpuDefendiendo = false;
                    cpuDefendiendoEspecial = true;
                    resultado += atacante.getNombre() + " activo defensa especial.\n";
                    atacante.setCooldownDefensaEspecial(2);
                }
                break;
            default:
                break;
        }

        if (!defensor.estaVivo()) {
            jugador.pokemonDerrotado();
            jugadorDefendiendo = false;
            jugadorDefendiendoEspecial = false;
            resultado += defensor.getNombre() + " fue derrotado.\n";
        }
        reducirCooldowns(atacante);
        return resultado;
    }

    public boolean batallaTerminado() {
        return !jugador.tienePokemonVivos() || !cpu.tienePokemonVivos();
    }

    public String obtenerGanador() {
        if (jugador.tienePokemonVivos()) {
            return jugador.getNombre();
        }
        return cpu.getNombre();
    }

    public Pokemon getPokemonJugador() {
        return jugador.getPokemonActivo();
    }

    public Pokemon getPokemonCPU() {
        return cpu.getPokemonActivo();
    }

    public boolean isJugadorDefendiendo() {
        return jugadorDefendiendo;
    }

    public boolean isJugadorDefendiendoEspecial() {
        return jugadorDefendiendoEspecial;
    }

    public boolean isCpuDefendiendo() {
        return cpuDefendiendo;
    }

    public boolean isCpuDefendiendoEspecial() {
        return cpuDefendiendoEspecial;
    }

    public int getUltimaAccionJugador() {
        return ultimaAccionJugador;
    }

    public int getUltimaAccionCPU() {
        return ultimaAccionCPU;
    }
}

