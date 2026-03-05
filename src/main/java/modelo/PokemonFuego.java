/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author jimen
 */
public class PokemonFuego extends Pokemon {

    public PokemonFuego(String nombre, String tipo, int hp, int ataque, int defensa, int ataqueEspecial, int defensaEspecial) {
        super(nombre, "Fuego", 115, 70, 45, 85, 70);
    }

    @Override
    public int atacar(Pokemon enemigo) {
        int danio = getAtaque();
        if(enemigo.getTipo().equals("Normal")){
            danio *= 2; //Fuerte contra normal
        } else if (enemigo.getTipo().equals("Agua")){
            danio/=2; //debil contra agua
        }
        System.out.println(getNombre()+"usa Lanzallamas!"); 
        return danio;

    }

    @Override
    public int ataqueEspecial(Pokemon enemigo) {
        int danio = getAtaqueEspecial(); // ataque especial inicial
        if (enemigo.getTipo().equals("Normal")){
            danio = 85;
        }
        System.out.println(getNombre()+ "usa Ataque Especial");
        return danio;
    }

    @Override
    public int defensaEspecial(Pokemon enemigo) {
         int defensa = getDefensaEspecial(); // defensa especial inicial
        if(enemigo.getTipo().equals("Agua")){
            defensa = 75;
        }
        return defensa;
    }
    
}
