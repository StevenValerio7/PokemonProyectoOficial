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
public class Cola {
    private Nodo frente;
    private Nodo fin;
    private int tamano;

    public Cola() {
        frente = null;
        fin = null;
        tamano = 0;
    }

    public void encolar(Pokemon p) {
        Nodo nuevo = new Nodo(p);
        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamano++;
    }

    public Pokemon desencolar() {
        if (estaVacia()) return null;
        Pokemon p = frente.pokemon;
        frente = frente.siguiente;
        if (frente == null) fin = null;
        tamano--;
        return p;
    }

    public Pokemon verFrente() {
        if (estaVacia()) return null;
        return frente.pokemon;
    }

    public boolean estaVacia() {
        return frente == null;
    }
    
    public int getTamano() {
        return tamano;
    }
    
    // Método para cambiar el Pokémon activo por otro de la cola (comportamiento rotativo)
    public void cambiarPokemonActivo() {
        if (tamano > 1) {
            Pokemon p = desencolar();
            encolar(p); // Lo mandamos al final de la cola
        }
    }
    
    public void mostrarCola() {
        Nodo actual = frente;
        while (actual != null) {
            System.out.print("[" + actual.pokemon.getNombre() + "] ");
            actual = actual.siguiente;
        }
        System.out.println();
    }
}
