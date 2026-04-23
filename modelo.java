package modelo;

public abstract class Pokemon {
    private String nombre;
    private String tipo;
    private int hp;
    private int hpMaximo;
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    
    private int cooldownAtaqueEspecial; // es solo un dato porque el paquete ManejoBatalla lo controla
    private int cooldownDefensaEspecial;

    public Pokemon(String nombre, String tipo, int hp, int ataque, int defensa, int ataqueEspecial, int defensaEspecial) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.hp = hp;
        this.hpMaximo = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        
        this.cooldownAtaqueEspecial = 0;
        this.cooldownDefensaEspecial = 0;
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

    public int getHpMaximo() {
        return hpMaximo;
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

    public int getCooldownDefensaEspecial() {
        return cooldownDefensaEspecial;
    }
    

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setCooldownAtaqueEspecial(int cooldownAtaqueEspecial) {
        this.cooldownAtaqueEspecial = cooldownAtaqueEspecial;
    }

    public void setCooldownDefensaEspecial(int cooldownDefensaEspecial) {
        this.cooldownDefensaEspecial = cooldownDefensaEspecial;
    }
     
    //Metodos 
    public int defender(int danio){
        int danioReducido = danio - defensa;
        
        if(danioReducido < 0){
           danioReducido = 0; 
        }
        return danioReducido;
    }
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

public class PokemonAgua extends Pokemon {

    public PokemonAgua(String nombre) {
        super(nombre, "Agua", 190, 55, 45, 75, 65);
    }

    @Override
    public int atacar(Pokemon enemigo) {
        int danio = getAtaque();
        if(enemigo.getTipo().equals("Fuego")){
            danio *= 2; //Fuerte contra fuego 
        } else if (enemigo.getTipo().equals("Normal")){
            danio/=2; //debil contra normal
        }
        
        return danio;
    }

    @Override
    public int ataqueEspecial(Pokemon enemigo) {
        int danio = getAtaqueEspecial(); // ataque especial inicial
        if (enemigo.getTipo().equals("Fuego")){
            danio = 85;
        }
        
        return danio;
    }

    @Override
    public int defensaEspecial(Pokemon enemigo) {
         int defensa = getDefensaEspecial(); // defensa especial inicial
        if(enemigo.getTipo().equals("Fuego")){
            defensa = 75;
        }
        return defensa;
    }
    
}

public class PokemonFuego extends Pokemon {

    public PokemonFuego(String nombre) {
        super(nombre, "Fuego", 115, 70, 45, 80, 60);
    }

    @Override
    public int atacar(Pokemon enemigo) {
        int danio = getAtaque();
        if(enemigo.getTipo().equals("Normal")){
            danio *= 2; //Fuerte contra normal
        } else if (enemigo.getTipo().equals("Agua")){
            danio/=2; //debil contra agua
        }
        
        return danio;

    }

    @Override
    public int ataqueEspecial(Pokemon enemigo) {
        int danio = getAtaqueEspecial(); // ataque especial inicial (80)
        if (enemigo.getTipo().equals("Normal")) {
            danio = 85;
        }
        return danio;
    }

    @Override
    public int defensaEspecial(Pokemon enemigo) {
        int defensa = getDefensaEspecial(); // defensa especial inicial (60)
        if (enemigo.getTipo().equals("Normal")) {
            defensa = 70;
        }
        return defensa;
    }
    
}

public class PokemonNormal extends Pokemon {

    public PokemonNormal(String nombre) {
        super(nombre, "Normal", 150, 50, 35, 70, 60);
    }

    @Override
    public int atacar(Pokemon enemigo) {
        int danio = getAtaque();
        if(enemigo.getTipo().equals("Agua")){
            danio *= 2; //Fuerte contra agua 
        } else if (enemigo.getTipo().equals("Fuego")){
            danio/=2; //debil contra fuego
        }
        
        return danio;
    }

    @Override
    public int ataqueEspecial(Pokemon enemigo) {
        int danio = getAtaqueEspecial(); // ataque especial inicial
        if (enemigo.getTipo().equals("Agua")){
            danio = 75;
        }
        
        return danio;
    }

    @Override
    public int defensaEspecial(Pokemon enemigo) {
        int defensa = getDefensaEspecial(); // defensa especial inicial
        if(enemigo.getTipo().equals("Agua")){
            defensa = 65;
        }
        return defensa;
    }
    
}
