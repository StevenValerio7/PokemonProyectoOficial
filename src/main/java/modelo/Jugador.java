/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import estructuras.Cola;
/**
 *
 * @author jimen
 */
public class Jugador {
    private String nombre;
    protected Cola pokedex;

    public Jugador(String nombre, Cola pokedex) {
        this.nombre = nombre;
        this.pokedex = new Cola();
    }

    public String getNombre() {
        return nombre;
    }
    private int contarRepetidos(String nombre){
        int contador=0;
        Cola temporal = new Cola();
        while(!pokedex.estaVacia()){
            Pokemon actual = pokedex.desencolar();
            if(actual.getNombre().equals(nombre)){
                contador++;
            }
            temporal.encolar(actual);
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
    pokedex.encolar(p);
    return p.getNombre() + " fue agregado al equipo.";
        
    }
    public void agregarPokemonCPU(Pokemon p){
        if(pokedex.getTamano()>=4){
            return;
        }
        if(contarRepetidos(p.getNombre())>=2){
            return; //maximo dos iguales 
        }
        pokedex.encolar(p);
    }
    
    
    public Pokemon getPokemonActivo(){
        return pokedex.verFrente();
    }
    public boolean tienePokemonVivos(){
        return !pokedex.estaVacia();
    }
    public String pokemonDerrotado(){
        Pokemon derrotado = pokedex.desencolar();
        if(derrotado !=null){
            return derrotado.getNombre() + " ha sido derrotado. Adelante " +
                    pokedex.verFrente().getNombre(); 
        } else {
            return derrotado.getNombre() + " ha sido derrotado. No quedan Pokemon.";
        }
        
    } 
    public String cambiarPokemon(){
        if(pokedex.getTamano()>1){
            Pokemon actual = pokedex.verFrente();
            pokedex.cambiarPokemonActivo();
            return nombre+ "retira a "+actual.getNombre() +
                    "Adelante "+pokedex.verFrente().getNombre();
        } else {
           return "No hay otros Pokemon disponibles";
        }  
    }
    public void mostrarEquipo() {
        pokedex.mostrarCola();
    }
    
}
