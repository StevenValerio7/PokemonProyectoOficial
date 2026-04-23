/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelo.Pokemon;
/**
 *
 * @author jimen
 */
public class ColaTurnos {
  private Nodo frente;
  private Nodo fin;
  
  public ColaTurnos(){
      frente = null;
      fin = null;
  }
  private static class Nodo{
      NodoPokemon nodoPokemon;
      Nodo siguiente;

        public Nodo(NodoPokemon nodoPokemon) {
            this.nodoPokemon = nodoPokemon;
            this.siguiente = null;
        } 
  }
  public void encolar(NodoPokemon nodoPokemon){
      Nodo nuevo = new Nodo(nodoPokemon);
      if(frente == null){
          frente = nuevo;
          fin = nuevo;
      } else {
          fin.siguiente = nuevo;
          fin = nuevo;
      }
  }
      public NodoPokemon desencolar(){
          if(frente == null){
              return null;
          }
          NodoPokemon nodoPokemon = frente.nodoPokemon;
          frente = frente.siguiente;
          if(frente == null){
              fin = null;
      }
         return nodoPokemon; 
      
  }
      public NodoPokemon verFrente(){
          if(frente == null){
              return null;
          }
          return frente.nodoPokemon;
      }
      public String mostrarCola(){
          String resultado="";
          Nodo actual = frente;
          while(actual != null){
              resultado += actual.nodoPokemon.pokemon.getNombre() + "\n";
              actual = actual.siguiente;
          }
          return resultado;
      }
      public void cambiarPokemonActivo(){
          if(frente == null || frente.siguiente == null){
              return;
          }
          NodoPokemon primero = desencolar();
          encolar(primero);
      }
      public int getTamano(){
          int contador = 0;
          Nodo actual = frente;
          while(actual!= null){
          contador++;
          actual = actual.siguiente;
          
      }
          return contador;
      }
      public boolean estaVacia(){
          return frente == null;
      }
  
  
 
         
}
