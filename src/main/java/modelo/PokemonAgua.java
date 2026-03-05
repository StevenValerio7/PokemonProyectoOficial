/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author jimen
 */
public class PokemonAgua extends Pokemon {

    public PokemonAgua(String nombre, String tipo, int hp, int ataque, int defensa, int ataqueEspecial, int defensaEspecial) {
        super(nombre, "Agua", 190, 55, 45, 75, 65);
    }

    @Override
    public int atacar(Pokemon enemigo) {
        int danio = getAtaque();
        if(enemigo.getTipo().equals("Fuego")){
            danio *= 2; //Fuerte contra fuego 
        } else if (enemigo.getTipo().equals("Normal")){
            danio/=2; //debil contra normal
        }
        System.out.println(getNombre()+"usa Pistola Agua!"); 
        return danio;
    }

    @Override
    public int ataqueEspecial(Pokemon enemigo) {
        int danio = getAtaqueEspecial(); // ataque especial inicial
        if (enemigo.getTipo().equals("Fuego")){
            danio = 85;
        }
        System.out.println(getNombre()+ "usa Ataque Especial");
        return danio;
    }

    @Override
    public int defensaEspecial(Pokemon enemigo) {
         int defensa = getDefensaEspecial(); // defensa especial inicial
        if(enemigo.getTipo().equals("Fuego")){
            defensa = 75;
        }
        return defensa;
    }
    
}
