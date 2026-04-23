package estructuras;

import Pokemones.*;
import modelo.Pokemon;


public class ListaPokemon {
    private NodoPokemon cabeza;

    public ListaPokemon() {
        cabeza = null;
    }

    public NodoPokemon getCabeza() {
        return cabeza;
    }

    public void agregar(Pokemon pokemon) {
        NodoPokemon nuevo = new NodoPokemon(pokemon);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoPokemon actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    public Pokemon obtener(int indice) {
        NodoPokemon actual = cabeza;
        int contador = 0;
        while (actual != null) {
            if (contador == indice) {
                return actual.pokemon;
            }
            actual = actual.siguiente;
            contador++;
        }
        return null;
    }

    public void mostrarCatalogo() {
        NodoPokemon actual = cabeza;
        int i = 0;
        while (actual != null) {
            actual = actual.siguiente;
            i++;
        }
    }

   
}

    


