package estructuras;
import modelo.Pokemon;
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

