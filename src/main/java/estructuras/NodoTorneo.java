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
public class NodoTorneo {
    private ListaPokemon combate;
    NodoTorneo siguiente;

    public NodoTorneo(ListaPokemon combate){
        this.combate = combate;
        this.siguiente = null;
    }

    public ListaPokemon getCombateActual(){
        return combate;
    }

    public NodoTorneo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoTorneo siguiente) {
        this.siguiente = siguiente;
    }
    
    
}

