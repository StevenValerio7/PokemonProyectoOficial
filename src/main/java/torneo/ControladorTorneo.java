/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package torneo;

/**
 *
 * @author steve
 */
import estructuras.ArbolTorneo;
import batalla.ManejoBatalla;
import java.io.IOException;

public class ControladorTorneo {

    public void iniciarTorneo(ArbolTorneo torneo) throws IOException {

        System.out.println("\n=================================");
        System.out.println("  ¡BIENVENIDO AL TORNEO POKÉMON!  ");
        System.out.println("=================================");

        
        System.out.println("\n--- SEMIFINAL 1 ---");

        torneo.raiz.izquierdo.jugador =
            ManejoBatalla.iniciarBatalla(
                torneo.raiz.izquierdo.izquierdo.jugador,
                torneo.raiz.izquierdo.derecho.jugador
            );

        
        System.out.println("\n--- SEMIFINAL 2 ---");

        torneo.raiz.derecho.jugador =
            ManejoBatalla.iniciarBatalla(
                torneo.raiz.derecho.izquierdo.jugador,
                torneo.raiz.derecho.derecho.jugador
            );

        
        System.out.println("\n=================================");
        System.out.println("          ¡LA GRAN FINAL!        ");
        System.out.println("=================================");

        torneo.raiz.jugador =
            ManejoBatalla.iniciarBatalla(
                torneo.raiz.izquierdo.jugador,
                torneo.raiz.derecho.jugador
            );

        System.out.println("\n=================================");
        System.out.println("¡EL CAMPEÓN DEL TORNEO ES "
                + torneo.raiz.jugador.getNombre().toUpperCase() + "!");
        System.out.println("=================================");
    }
}
