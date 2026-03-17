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
        
        if(opcion == 1){ //Ataque normal
            double prob = Math.random();
            if(prob<0.2){
                resultado+= atacante.getNombre()+" fallo el ataque.\n";
                
            } else{
                int danio = atacante.atacar(defensor);
                defensor.recibirDa_io(danio);
                resultado+= atacante.getNombre()+ 
                        "atacó causando" + danio + " de daño.}n";
            }
        }
        else if(opcion == 2){ //Ataque especial
            if(atacante.getCooldownAtaqueEspecial()>0){
                resultado += "Ataque especial en cooldown.\n";
            } else{
                double prob = Math.random();
                
                if(prob<0.2){
                    resultado += atacante.getNombre() + "fallo el atque especial.\n";
                }else{
                    int danio = atacante.ataqueEspecial(defensor);
                    defensor.recibirDa_io(danio);
                    resultado+= atacante.getNombre() +
                            "uso ataque especial causando "+ danio + " de dano.\n";
                    atacante.setCooldownAtaqueEspecial(2);
                }
            }
        }
        else if(opcion == 3){//Defensa normal
            resultado+= atacante.getNombre() + "se preparo para defender.\n";
            
        }
        
        else if (opcion == 4){ // defensa especial
            if(atacante.getCooldownDefensaEspecial()>0){
                resultado += "Defensa especial en cooldown.\n";
            }else{
                resultado += atacante.getNombre()+" activo defensa especial.\n";
                atacante.setCooldownDefensaEspecial(2);
            }
        }
        else if(opcion == 5){ //Cambiar Pokemon
            resultado+= jugador.cambiarPokemon();
        }
        //Verificar si defensor murio
        if(!defensor.estaVivo()){
            cpu.pokemonDerrotado();
            resultado += defensor.getNombre() + " fue derrotado.\n";
            
        }
        reducirCooldowns(atacante);
        return resultado;
    }
    
    //Turno auto de la CPU
    public String turnoCPU(){
    Pokemon atacante = cpu.getPokemonActivo();
    Pokemon defensor = jugador.getPokemonActivo();
    
    String resultado = "";
    
    int opcion = (int)(Math.random()* 2)+ 1; // esto es 1 o 2
    
    if(opcion == 1){
        int danio = atacante.atacar(defensor);
        defensor.recibirDa_io(danio);
        
        resultado += atacante.getNombre() +
                " ataco a "+ defensor.getNombre() +
                " causando "+ danio + "de dano.\n";
    } else {
        if(atacante.getCooldownAtaqueEspecial() == 0){
            int danio = atacante.ataqueEspecial(defensor);
            defensor.recibirDa_io(danio);
            
            resultado += atacante.getNombre() +
                    "uso ataque especial causando " + danio +
                    "de dano.\n";
            atacante.setCooldownAtaqueEspecial(2);
        }else{
            resultado += atacante.getNombre() + "no pudo usar ataque especial";
        }
    }
    //Verificar si el denfensor murio
    if(!defensor.estaVivo()){
        jugador.pokemonDerrotado();
        resultado += defensor.getNombre() + " fue derrotado.\n";
    }
        reducirCooldowns(atacante);
        return resultado;
}
    //Verificar si la batalla termino
    public boolean batallaTerminado(){
        return !jugador.tienePokemonVivos() || !cpu.tienePokemonVivos();
    }
    
    //Obtener ganador
    public String obtenerGanador(){
        if(jugador.tienePokemonVivos()){
            return jugador.getNombre();
        } else{
            return cpu.getNombre();
        }
    }
    // Getters para la GUI
    public Pokemon getPokemonJugador(){
        return jugador.getPokemonActivo();
    }
    public Pokemon getPokemonCPU(){
        return cpu.getPokemonActivo();
    }
   
}
