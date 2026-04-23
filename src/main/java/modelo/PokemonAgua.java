package modelo;

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
