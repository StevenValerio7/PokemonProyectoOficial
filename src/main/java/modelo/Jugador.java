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
    public void agregarPokemon(Pokemon p){
        if (pokedex.getTamano() >= 4) {
        System.out.println("El equipo ya tiene 4 Pokemon.");
        return;
    }
    if (contarRepetidos(p.getNombre())>0) {
        System.out.println("No se permiten Pokemon repetidos.");
        return;
    }
    pokedex.encolar(p);
        
    }
    public void agregarPokemonCPU(Pokemon p){
        if(pokedex.getTamano()>=4){
            return;
        }
        if(contarRepetidos(p.getNombre())>=2){
            return; //maximo dos iguales 
        }
    }
    
    
    public Pokemon getPokemonActivo(){
        return pokedex.verFrente();
    }
    public boolean tienePokemonVivos(){
        return !pokedex.estaVacia();
    }
    public void pokemonDerrotado(){
        Pokemon derrotado = pokedex.desencolar();
        if(derrotado !=null){
            System.out.println(derrotado.getNombre()+"ha sido derrotado!");
        }
        if(!pokedex.estaVacia()){
            System.out.println("Adelante "+pokedex.verFrente().getNombre());
        }
    }
    public void cambiarPokemon(){
        if(pokedex.getTamano()>1){
            Pokemon actual = pokedex.verFrente();
            System.out.println(nombre+ "retira a "+actual.getNombre());
            pokedex.cambiarPokemonActivo();
            System.out.println("Adelante "+pokedex.verFrente().getNombre());
        } else {
            System.out.println("No hay otros Pokemon disponibles");
        }  
    }
    public void mostrarEquipo() {
        System.out.print("Equipo de " + nombre + ": ");
        pokedex.mostrarCola();
    }
    
}
