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
public class Nodo {
    Pokemon pokemon;
    Nodo siguiente;

    public Nodo(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.siguiente = null;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }
    
}

