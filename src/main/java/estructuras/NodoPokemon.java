package estructuras;
import modelo.Pokemon;
public class NodoPokemon {
    public Pokemon pokemon;
    public NodoPokemon siguiente;
    
    public NodoPokemon(Pokemon pokemon){
        this.pokemon = pokemon;
        this.siguiente = null;
    }
}
