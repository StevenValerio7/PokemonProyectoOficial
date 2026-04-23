package batalla;

public class GeneradorAleatorio {
    public int siguiente(int limite) {
        if (limite <= 0) {
            return 0;
        }
        return (int) (Math.random() * limite);
    }
}
