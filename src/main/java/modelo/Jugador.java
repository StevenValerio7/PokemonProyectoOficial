/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import estructuras.NodoPokemon;
import estructuras.ColaTurnos;
import estructuras.ListaPokemon;
/**
 *
 * @author jimen
 */
public class Jugador {
    private String nombre;
    protected ColaTurnos pokedex;

    public Jugador(String nombre, ColaTurnos pokedex) {
        this.nombre = nombre;
        this.pokedex = pokedex;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim(); //trim para quitar espacios
        }
    }
    private int contarRepetidos(String nombre){
        int contador=0;
        ColaTurnos temporal = new ColaTurnos();
        while(!pokedex.estaVacia()){
            NodoPokemon nodo = pokedex.desencolar();
            Pokemon actual = nodo.pokemon;
            if(actual.getNombre().equals(nombre)){
                contador++;
            }
            temporal.encolar(nodo);
        }    
        while(!temporal.estaVacia()){
            pokedex.encolar(temporal.desencolar());
        }
        return contador;
    }
    public String agregarPokemon(Pokemon p){
        if (pokedex.getTamano() >= 4) {
        return "El equipo ya tiene 4 Pokemon.";
        
    }
    if (contarRepetidos(p.getNombre())>0) {
        return "No se permiten Pokemon repetidos.";
      
    }
    pokedex.encolar(new NodoPokemon(p));
    return p.getNombre() + " fue agregado al equipo.";
        
    }
    public void agregarPokemonCPU(Pokemon p){
        if(pokedex.getTamano()>=4){
            return;
        }
        if(contarRepetidos(p.getNombre())>=2){
            return; //maximo dos iguales 
        }
        pokedex.encolar(new NodoPokemon(p));
    }
    public boolean eliminarPokemon(String nombre){
        if(pokedex.estaVacia()){
            return false;
        }
        ColaTurnos temporal = new ColaTurnos();
        boolean eliminado = false;
        while(!pokedex.estaVacia()){
            NodoPokemon nodo = pokedex.desencolar();
            if(!eliminado && nodo.pokemon.getNombre().equals(nombre)){
                eliminado = true;
            } else {
                temporal.encolar(nodo);
            }
        }
        while(!temporal.estaVacia()){
            pokedex.encolar(temporal.desencolar());
        }
        return eliminado;
    }
    
      public boolean cambiarPokemonA(String nombrePokemon){
        if(pokedex.estaVacia()){
            return false;
        }
        int vueltas = pokedex.getTamano();
        for(int i = 0; i < vueltas; i++){
            if(pokedex.verFrente().pokemon.getNombre().equals(nombrePokemon)){
                return true;
            }
            pokedex.cambiarPokemonActivo();
        }
        return false;
    }
      public String cambiarPokemonB(){
        if(pokedex.getTamano()>1){
            Pokemon actual = pokedex.verFrente().pokemon;
            pokedex.cambiarPokemonActivo();
            return nombre+ "retira a "+actual.getNombre() +
                    "Adelante "+pokedex.verFrente().pokemon.getNombre();
        } else {
           return "No hay otros Pokemon disponibles";
        }  
    }

    
    public Pokemon getPokemonActivo(){
        if(pokedex.verFrente()==null) return null;
        return pokedex.verFrente().pokemon;
    }
    
    public boolean tienePokemonVivos(){
        return !pokedex.estaVacia();
    }
    
    public String pokemonDerrotado(){
        NodoPokemon nodo = pokedex.desencolar();
        if(nodo !=null){
            Pokemon derrotado = nodo.pokemon;
            if(pokedex.verFrente()!= null){
            return derrotado.getNombre() + " ha sido derrotado. Adelante " +
                    pokedex.verFrente().pokemon.getNombre(); 
            
        } else {
            return derrotado.getNombre() + " ha sido derrotado. No quedan Pokemon.";
        }
            
        }  
        return "No hay Pokemon.";
    } 
    public ListaPokemon obtenerEquipo(){
        ListaPokemon lista = new ListaPokemon();
        ColaTurnos temporal = new ColaTurnos();
        while(!pokedex.estaVacia()){
            NodoPokemon nodo = pokedex.desencolar();
            lista.agregar(nodo.pokemon);
            temporal.encolar(nodo);
        }
        while(!temporal.estaVacia()){
            pokedex.encolar(temporal.desencolar());
        }
        return lista;
    }
     
    public void mostrarEquipo() {
        pokedex.mostrarCola();
    }
    
}
