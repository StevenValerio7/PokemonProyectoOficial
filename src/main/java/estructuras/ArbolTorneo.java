
package estructuras;
import modelo.Jugador;
/**
 *
 * @author sebas
 */
public class ArbolTorneo {
     public static class NodoTorneo {
        public Jugador jugador;
        public NodoTorneo izquierdo;
        public NodoTorneo derecho;
        
        public NodoTorneo() {
            this.jugador = null;
        }
        
        public NodoTorneo(Jugador jugador) {
            this.jugador = jugador;
        }
    }
    
    public NodoTorneo raiz;
    
    public ArbolTorneo(Jugador j1, Jugador j2, Jugador j3, Jugador j4) {
        // Torneo estructurado en árbol binario (Final -> Semifinales -> Participantes)
        raiz = new NodoTorneo(); // Nodo de la Final
        
        raiz.izquierdo = new NodoTorneo(); // Semifinal 1
        raiz.derecho = new NodoTorneo();   // Semifinal 2
        
        // Participantes 
        raiz.izquierdo.izquierdo = new NodoTorneo(j1);
        raiz.izquierdo.derecho = new NodoTorneo(j2);
        
        raiz.derecho.izquierdo = new NodoTorneo(j3);
        raiz.derecho.derecho = new NodoTorneo(j4);
    }
}
    
    
