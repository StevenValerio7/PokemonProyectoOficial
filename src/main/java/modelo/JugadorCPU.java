/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

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
    public void asignarPokemonAleatorios(Lista catalogo){
        int totalPokemon = contarElementos(catalago);
        while (pokedex.getTamano()<4){
            int indice = (int)(Math.random()*totalPokemon);
            Pokemon original = catalago.obtener(indice);
            
            if(original!= null){
                agregarPokemonCPU(original);
                System.out.println("CPU obtuvo: "+original.getNombre());
            }
        }
    }
            
     
    @Override
    public void cambiarPokemon(){
        System.out.println("CPU no cambia de jugador");
    }
    
    
   
    
    
}
