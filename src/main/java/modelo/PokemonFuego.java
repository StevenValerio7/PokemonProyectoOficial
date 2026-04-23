package modelo;

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
