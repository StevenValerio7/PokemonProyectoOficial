/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author jimen
 */
public class PokemonNormal extends Pokemon {

    public PokemonNormal(String nombre) {
        super(nombre, "Normal", 150, 50, 35, 70, 60);
    }

    @Override
    public int atacar(Pokemon enemigo) {
        int danio = getAtaque();
        if(enemigo.getTipo().equals("Agua")){
            danio *= 2; //Fuerte contra agua 
        } else if (enemigo.getTipo().equals("Fuego")){
            danio/=2; //debil contra fuego
        }
        
        return danio;
    }

    @Override
    public int ataqueEspecial(Pokemon enemigo) {
        int danio = getAtaqueEspecial(); // ataque especial inicial
        if (enemigo.getTipo().equals("Agua")){
            danio = 75;
        }
        
        return danio;
    }

    @Override
    public int defensaEspecial(Pokemon enemigo) {
        int defensa = getDefensaEspecial(); // defensa especial inicial
        if(enemigo.getTipo().equals("Agua")){
            defensa = 65;
        }
        return defensa;
    }

    
}

    
    

