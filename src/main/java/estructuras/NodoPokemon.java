/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelo.Pokemon;
/**
 *
 * @author jimen
 */
public class NodoPokemon {
    public Pokemon pokemon;
    public NodoPokemon siguiente;
    
    public NodoPokemon(Pokemon pokemon){
        this.pokemon = pokemon;
        this.siguiente = null;
    }
}
