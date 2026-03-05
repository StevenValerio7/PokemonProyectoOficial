package estructuras;

/**
 *
 * @author sebas
 */
import modelo.Pokemon;


public class Lista {
    private Nodo cabeza;

    public Lista() {
        cabeza = null;
    }

    public void agregar(Pokemon p) {
        Nodo nuevo = new Nodo(p);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    public Pokemon obtener(int indice) {
        Nodo actual = cabeza;
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
        Nodo actual = cabeza;
        int i = 0;
        while (actual != null) {
            System.out.println(i + ". " + actual.pokemon.toString());
            actual = actual.siguiente;
            i++;
        }
    }
}

    

