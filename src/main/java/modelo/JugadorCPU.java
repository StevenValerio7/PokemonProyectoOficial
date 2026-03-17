/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import estructuras.Cola;
import estructuras.Lista;

/**
 *
 * @author jimen
 */
public class JugadorCPU extends Jugador {

    public JugadorCPU(String nombre, Cola pokedex) {
        super(nombre, pokedex);
    }
    
    private int contarElementos(Lista catalogo){
        int contador = 0;
        while(catalogo.obtener(contador)!=null){
            contador++;
        }
        return contador;
    }
    public String asignarPokemonAleatorios(Lista catalogo){
        int totalPokemon = contarElementos(catalogo);
        String resultado = "";
        while (pokedex.getTamano()<4){
            int indice = (int)(Math.random()*totalPokemon);
            Pokemon original = catalogo.obtener(indice);
            
            if(original!= null){
                agregarPokemonCPU(original);
                 resultado += "CPU obtuvo: "+original.getNombre() + "\n";
            }
        }
        return resultado;
    }
            
     
    @Override
    public String cambiarPokemon(){
        return "CPU no cambia de jugador";
    }
    
    
   
    
    
}
