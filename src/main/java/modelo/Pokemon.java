/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author jimen
 */
public abstract class Pokemon {
     private String nombre;
    private String tipo;
    private int hp;
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    
    private int cooldownAtaqueEspecial; // es solo un dato porque el paquete ManejoBatalla lo controla

    public Pokemon(String nombre, String tipo, int hp, int ataque, int defensa, int ataqueEspecial, int defensaEspecial) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.hp = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.cooldownAtaqueEspecial = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getHp() {
        return hp;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }

    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    public int getCooldownAtaqueEspecial() {
        return cooldownAtaqueEspecial;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setCooldownAtaqueEspecial(int cooldownAtaqueEspecial) {
        this.cooldownAtaqueEspecial = cooldownAtaqueEspecial;
    }
    
    //Metodos 
    public void recibirDa_io(int danio){
        hp -= danio;
        if(hp<0){
            hp =0;
        }
    }
    public boolean estaVivo(){
        return hp>0;
    }
    public abstract int atacar(Pokemon enemigo);
    public abstract int ataqueEspecial(Pokemon enemigo);
    public abstract int defensaEspecial(Pokemon enemigo);
            
    
}
