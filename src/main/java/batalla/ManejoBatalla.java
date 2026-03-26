/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package batalla;
import modelo.Jugador;
import modelo.JugadorCPU;
import modelo.Pokemon;
        
/**
 *
 * @author jimen
 */

public class ManejoBatalla {
    private Jugador jugador;
    private JugadorCPU cpu;
    
    private boolean jugadorDefendiendo = false;
    private boolean cpuDefendiendo = false;

    public ManejoBatalla(Jugador jugador, JugadorCPU cpu) {
        this.jugador = jugador;
        this.cpu = cpu;
    }
  

    //Metodos
    private void reducirCooldowns(Pokemon p) {
        if(p.getCooldownAtaqueEspecial()>0){
            p.setCooldownAtaqueEspecial(
                    p.getCooldownAtaqueEspecial() -1);
        }
        if(p.getCooldownDefensaEspecial()>0){
            p.setCooldownDefensaEspecial(
                    p.getCooldownDefensaEspecial() - 1);
        }
    }
    
    public String turnoJugador(int opcion){
        Pokemon atacante = jugador.getPokemonActivo();
        Pokemon defensor = cpu.getPokemonActivo();
        
        String resultado = "";
        
        if(atacante == null || defensor == null) return resultado;
            
        switch(opcion){
            case 1: //Ataque normal
                if(Math.random()< 0.2){
                    resultado += atacante.getNombre() + " falló el ataque.\n";    
                } else {
                    int danio = atacante.atacar(defensor);
                
                if(cpuDefendiendo){
                    danio = defensor.defender(danio);
                    cpuDefendiendo = false;
                    resultado += "(CPU se defendió) ";
                    
                }
                defensor.recibirDa_io(danio);
                resultado += atacante.getNombre()+ " atacó causando "+ danio + " de daño.\n";
        }
        break;
        case 2: //Ataque especial
        if(atacante.getCooldownAtaqueEspecial() > 0){
        resultado += "Ataque especial en cooldown.\n";
        } else{
        if(Math.random()<0.2){
        resultado+=atacante.getNombre() + " falló el ataque especial.\n";
        } else {
            int danio = atacante.ataqueEspecial(defensor);
            if(cpuDefendiendo){
                    danio = defensor.defender(danio);
                    cpuDefendiendo = false;
                    resultado += "(CPU se defendió) "; 
                }
            defensor.recibirDa_io(danio);
            resultado += atacante.getNombre()+ " atacó causando "+ danio + " de daño.\n";
            atacante.setCooldownAtaqueEspecial(2);
        }
        
    }
    break;
    case 3: //Defensa
        jugadorDefendiendo = true;
        resultado+=atacante.getNombre() + " se está defendiendo.\n";
        break;
    case 4: //Defensa especial
        if(atacante.getCooldownDefensaEspecial()>0){
            resultado += "Defensa especial en cooldown.\n";
        } else {
            jugadorDefendiendo = true;
            resultado += atacante.getNombre() + " activó defensa especial.\n";
            atacante.setCooldownDefensaEspecial(2);
        }
        break;
        
    case 5: //Cambiar 
        resultado+= jugador.cambiarPokemon();
        break;
        
        }
        if(!defensor.estaVivo()){
            cpu.pokemonDerrotado();
            resultado+= defensor.getNombre() + " fue derrotado.\n";
        }
        reducirCooldowns(atacante);
        return resultado;
        
 } 
    public String turnoCPU(){
        Pokemon atacante = cpu.getPokemonActivo();
        Pokemon defensor = jugador.getPokemonActivo();
        
        String resultado = "";
        if(atacante == null || defensor == null) return resultado;
        int opcion = (int)(Math.random()*3)+1;
        
        switch(opcion){
            case 1: //Ataque normal
                if(Math.random()< 0.2){
                    resultado += atacante.getNombre() + " falló el ataque.\n";    
                } else {
                    int danio = atacante.atacar(defensor);
                
                if(jugadorDefendiendo){
                    danio = defensor.defender(danio);
                    jugadorDefendiendo = false;
                    resultado += "(Jugador se defendió) ";
                    
                }
                defensor.recibirDa_io(danio);
                resultado += atacante.getNombre()+ " atacó causando "+ danio + " de daño.\n";
        }
        break;
           case 2:
        if(atacante.getCooldownAtaqueEspecial()==0){
        if(Math.random()<0.2){
        resultado +=atacante.getNombre()+" falló el ataque especial.\n";
        
    } else {
            int danio = atacante.ataqueEspecial(defensor);
            if(jugadorDefendiendo){
                danio = defensor.defender(danio);
                jugadorDefendiendo = false;
                resultado += "(Jugador se defendió) ";
            }
            defensor.recibirDa_io(danio);
            resultado +=atacante.getNombre() + " usó ataque especial causando "+danio+" de daño\n";
            atacante.setCooldownAtaqueEspecial(2);
        } 
        } else {
          resultado += atacante.getNombre() + " no pudo usar ataque especial.\n";
        }
        break;
           case 3: 
               cpuDefendiendo = true;
               resultado += atacante.getNombre() + " se esta defendiendo.\n";
               break;
    }
        if(!defensor.estaVivo()){
            cpu.pokemonDerrotado();
            resultado+= defensor.getNombre() + " fue derrotado.\n";
        }
        reducirCooldowns(atacante);
        return resultado;
    }
    public boolean batallaTerminado(){
        return !jugador.tienePokemonVivos() || !cpu.tienePokemonVivos();
        
    }
    public String obtenerGanador(){
        if(jugador.tienePokemonVivos()){
            return jugador.getNombre();
        } else {
            return cpu.getNombre();
        }
    }
    public Pokemon getPokemonJugador(){
        return jugador.getPokemonActivo();
    }
    public Pokemon getPokemonCPU(){
        return cpu.getPokemonActivo();
    }
}
        
           
   


