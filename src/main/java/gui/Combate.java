package gui;
import estructuras.ArbolTorneo;
import estructuras.ColaTurnos;
import estructuras.ListaPokemon;
import estructuras.NodoPokemon;
import estructuras.NodoTorneo;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import modelo.Jugador;
import modelo.JugadorCPU;
import batalla.ManejoBatalla;
import modelo.Pokemon;
public class Combate extends JFrame {

    private ListaPokemon equipoCPU;
    private ArbolTorneo torneo;
    private Jugador jugadorBatalla;
    private JugadorCPU cpuBatalla;
    private ManejoBatalla manejoBatalla;
    private FadeLabel lblPokemonJugador;
    private FadeLabel lblPokemonCPU;
    private JProgressBar pbVidaJugador;
    private JProgressBar pbVidaCPU;
    private JButton btnAtaqueBasico;
    private JButton btnAtaqueEspecial;
    private JButton btnDefensa;
    private JButton btnDefensaEspecial;
    private JButton btnCambiarPokemon;
    private JButton btnContinuar;
    private JTextPane txtLog;
    private BracketPanel bracketPanel;
    private JPanel panelCombate;
    private JPanel panelBracket;
    private JPanel panelEquipoVivo;
    private JPanel panelBotones;
    private JScrollPane scrollLog;
    private FondoPanel fondo;
    private JLabel lblNombreJugador;
    private JLabel lblNombreCPU;
    private JLabel lblTitulo;
    private JLabel lblEscudoJugador;
    private JLabel lblEscudoCPU;
    private boolean puedeActuar;
    private boolean batallaIniciada;

    private Clip clip;
    private ImageIcon escudoNormalIcon;
    private ImageIcon escudoEspecialIcon;

    private static class SeleccionPokemon {
        String nombre;
        boolean confirmada;
    }

    private static class Contador {
        int valor;
    }

    private static final int BASE_WIDTH = 1200;
    private static final int BASE_HEIGHT = 600;

    public Combate(Jugador jugador, JugadorCPU cpu) {
    this.jugadorBatalla = jugador;
    this.cpuBatalla = cpu;
    this.manejoBatalla = new ManejoBatalla(jugadorBatalla, cpuBatalla);

    this.torneo = new ArbolTorneo(jugadorBatalla.getNombre());
    this.batallaIniciada = false;
    this.puedeActuar = false;
    

    initComponents();    
    torneo.limpiarGanador();
    if (btnContinuar != null) {
        btnContinuar.setEnabled(true);
        btnContinuar.setText("Continuar");
    }
    addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            ajustarLayout();
        }
    });
    ajustarLayout();
    mostrarBracket(true);
    actualizarVistaCombate();
    actualizarEquipoVivo();

    setTitle("Combate Pokémon");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1200, 600);
    setLocationRelativeTo(null);
}

    public Combate(Jugador jugador, JugadorCPU cpu, ArbolTorneo torneo) {
        this.jugadorBatalla = jugador;
        this.cpuBatalla = cpu;
        this.manejoBatalla = new ManejoBatalla(jugadorBatalla, cpuBatalla);
        this.torneo = torneo != null ? torneo : new ArbolTorneo(jugadorBatalla.getNombre());
        this.batallaIniciada = false;
        this.puedeActuar = false;

        initComponents();
        this.torneo.limpiarGanador();
        if (btnContinuar != null) {
            btnContinuar.setEnabled(true);
            btnContinuar.setText("Continuar");
        }
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarLayout();
            }
        });
        ajustarLayout();
        mostrarBracket(true);
        actualizarVistaCombate();
        actualizarEquipoVivo();

        setTitle("Combate Pok\u00E9mon");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
    }

    private ColaTurnos convertirListaACola(ListaPokemon lista) {
        ColaTurnos cola = new ColaTurnos();
        if (lista == null) {
            return cola;
        }

        int i = 0;
        Pokemon actual = lista.obtener(i);
        while (actual != null) {
            cola.encolar(new NodoPokemon(actual));
            i++;
            actual = lista.obtener(i);
        }
        return cola;
    }

    private int obtenerHpMaximo(Pokemon pokemon) {
        if (pokemon == null) {
            return 1;
        }
        int maximo = pokemon.getHpMaximo();
        return maximo > 0 ? maximo : pokemon.getHp();
    }

    private void initComponents() {
        fondo = new FondoPanel();
        fondo.setLayout(null);
        setContentPane(fondo);

        panelBracket = new JPanel(null);
        panelBracket.setOpaque(false);
        panelBracket.setBounds(0, 0, 1200, 600);

        bracketPanel = new BracketPanel(torneo);
        bracketPanel.setOpaque(false);
        bracketPanel.setBounds(0, 0, 1200, 600);
        panelBracket.add(bracketPanel);
        fondo.add(panelBracket);

        btnContinuar = new JButton("Continuar");
        btnContinuar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnContinuar.setBackground(new Color(40, 167, 69));
        btnContinuar.setForeground(Color.WHITE);
        btnContinuar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnContinuar.setOpaque(true);
        btnContinuar.setBounds(540, 500, 120, 40);
        btnContinuar.addActionListener(e -> iniciarBatalla());
        fondo.add(btnContinuar);

        panelCombate = new JPanel(null);
        panelCombate.setOpaque(false);
        panelCombate.setBounds(0, 0, 1200, 600);
        panelCombate.setVisible(false);
        fondo.add(panelCombate);

        lblTitulo = new JLabel("¡Combate Pokémon!");
        lblTitulo.setBounds(500, 100, 200, 20);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelCombate.add(lblTitulo);

        Pokemon pj = manejoBatalla.getPokemonJugador();
        Pokemon pc = manejoBatalla.getPokemonCPU();

        lblPokemonJugador = new FadeLabel(cargarImagenPokemon(pj != null ? pj.getNombre() : "default"));
        lblPokemonJugador.setBounds(100, 150, 132, 132);
        panelCombate.add(lblPokemonJugador);

        pbVidaJugador = new JProgressBar(0, obtenerHpMaximo(pj));
        pbVidaJugador.setValue(pj != null ? pj.getHp() : 0);
        pbVidaJugador.setStringPainted(true);
        pbVidaJugador.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pbVidaJugador.setForeground(new Color(0, 255, 127));
        pbVidaJugador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pbVidaJugador.setBounds(100, 300, 132, 20);
        if (pj != null) {
            actualizarTextoBarra(pbVidaJugador, pj);
        }
        panelCombate.add(pbVidaJugador);

        lblPokemonCPU = new FadeLabel(cargarImagenPokemon(pc != null ? pc.getNombre() : "default"));
        lblPokemonCPU.setBounds(900, 150, 132, 132);
        panelCombate.add(lblPokemonCPU);

        pbVidaCPU = new JProgressBar(0, obtenerHpMaximo(pc));
        pbVidaCPU.setValue(pc != null ? pc.getHp() : 0);
        pbVidaCPU.setStringPainted(true);
        pbVidaCPU.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pbVidaCPU.setForeground(new Color(0, 255, 127));
        pbVidaCPU.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pbVidaCPU.setBounds(900, 300, 132, 20);
        if (pc != null) {
            actualizarTextoBarra(pbVidaCPU, pc);
        }
        panelCombate.add(pbVidaCPU);

        lblNombreJugador = new JLabel(pj != null ? pj.getNombre() : "");
        lblNombreJugador.setBounds(100, 330, 132, 20);
        lblNombreJugador.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreJugador.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombreJugador.setForeground(Color.WHITE);
        lblNombreJugador.setOpaque(true);
        lblNombreJugador.setBackground(new Color(0, 0, 0, 150));
        lblNombreJugador.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        panelCombate.add(lblNombreJugador);

        lblNombreCPU = new JLabel(pc != null ? pc.getNombre() : "");
        lblNombreCPU.setBounds(900, 330, 132, 20);
        lblNombreCPU.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreCPU.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombreCPU.setForeground(Color.WHITE);
        lblNombreCPU.setOpaque(true);
        lblNombreCPU.setBackground(new Color(0, 0, 0, 150));
        lblNombreCPU.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        panelCombate.add(lblNombreCPU);

        lblEscudoJugador = new JLabel();
        lblEscudoJugador.setBounds(232, 330, 20, 20);
        panelCombate.add(lblEscudoJugador);

        lblEscudoCPU = new JLabel();
        lblEscudoCPU.setBounds(1032, 330, 20, 20);
        panelCombate.add(lblEscudoCPU);

        escudoNormalIcon = cargarIcono("/pokemonproyecto/img/defensa.png", 18, 18);
        escudoEspecialIcon = cargarIcono("/pokemonproyecto/img/defensaespecial.png", 18, 18);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setOpaque(false);
        panelBotones.setBounds(0, 450, 1200, 100);
        panelCombate.add(panelBotones);

        btnAtaqueBasico = new JButton("Ataque");
        configurarBoton(btnAtaqueBasico, new Color(255, 69, 0), new Color(255, 140, 0));
        btnAtaqueBasico.setEnabled(false);
        btnAtaqueBasico.addActionListener(e -> realizarAccion("ataque"));
        panelBotones.add(btnAtaqueBasico);

        btnAtaqueEspecial = new JButton("Ataque Especial");
        configurarBoton(btnAtaqueEspecial, new Color(220, 20, 60), new Color(255, 99, 71));
        btnAtaqueEspecial.setEnabled(false);
        btnAtaqueEspecial.addActionListener(e -> {
            Pokemon activo = manejoBatalla.getPokemonJugador();
            if (activo != null && activo.getCooldownAtaqueEspecial() == 0) {
                realizarAccion("ataqueEspecial");
            } else if (activo != null) {
                appendLog("Ataque especial en cooldown: " + activo.getCooldownAtaqueEspecial() + " turnos.\n");
            }
        });
        panelBotones.add(btnAtaqueEspecial);

        btnDefensa = new JButton("Defensa");
        configurarBoton(btnDefensa, new Color(70, 130, 180), new Color(135, 206, 250));
        btnDefensa.setEnabled(false);
        btnDefensa.addActionListener(e -> realizarAccion("defensa"));
        panelBotones.add(btnDefensa);

        btnDefensaEspecial = new JButton("Defensa Especial");
        configurarBoton(btnDefensaEspecial, new Color(30, 144, 255), new Color(100, 149, 237));
        btnDefensaEspecial.setEnabled(false);
        btnDefensaEspecial.addActionListener(e -> {
            Pokemon activo = manejoBatalla.getPokemonJugador();
            if (activo != null && activo.getCooldownDefensaEspecial() == 0) {
                realizarAccion("defensaEspecial");
            } else if (activo != null) {
                appendLog("Defensa especial en cooldown: " + activo.getCooldownDefensaEspecial() + " turnos.\n");
            }
        });
        panelBotones.add(btnDefensaEspecial);

        btnCambiarPokemon = new JButton("Cambiar Pokémon");
        configurarBoton(btnCambiarPokemon, new Color(50, 205, 50), new Color(144, 238, 144));
        btnCambiarPokemon.setEnabled(false);
        btnCambiarPokemon.addActionListener(e -> realizarAccion("cambiar"));
        panelBotones.add(btnCambiarPokemon);

        txtLog = new JTextPane();
        txtLog.setEditable(false);
        txtLog.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txtLog.setForeground(Color.BLACK);
        txtLog.setBackground(new Color(255, 255, 255, 200));
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        txtLog.setParagraphAttributes(center, true);
        scrollLog = new JScrollPane(txtLog);
        scrollLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollLog.setBounds(0, 0, 1200, 100);
        panelCombate.add(scrollLog);

        panelEquipoVivo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panelEquipoVivo.setOpaque(false);
        panelEquipoVivo.setBounds(1000, 450, 150, 100);
        panelCombate.add(panelEquipoVivo);
    }

    private void configurarBoton(JButton boton, Color colorNormal, Color colorHover) {
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorNormal);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        Dimension originalSize = new Dimension(140, 40);
        Dimension hoverSize = new Dimension(147, 42);
        boton.setPreferredSize(originalSize);
        boton.setFocusPainted(false);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(colorHover);
                    boton.setPreferredSize(hoverSize);
                    boton.revalidate();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorNormal);
                boton.setPreferredSize(originalSize);
                boton.revalidate();
            }
        });
    }

    private void iniciarBatalla() {
        batallaIniciada = true;
        puedeActuar = true;
   
        panelBracket.setVisible(false);
        btnContinuar.setVisible(false);
        panelCombate.setVisible(true);

        fondo.setImagen("ArenaDeBatallaSoleado.jpg");
        animarPulsacion(lblTitulo);
        appendLog("¡Empieza el combate!\n");

        reproducirMusica("combate.wav", true);
        actualizarVistaCombate();
        actualizarEquipoVivo();
        
    }

    private void ajustarLayout() {
        int w = getContentPane().getWidth();
        int h = getContentPane().getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }

        if (panelBracket != null) {
            panelBracket.setBounds(0, 0, w, h);
        }
        if (bracketPanel != null) {
            bracketPanel.setBounds(0, 0, w, h);
        }
        if (panelCombate != null) {
            panelCombate.setBounds(0, 0, w, h);
        }

        double scale = Math.min(w / (double) BASE_WIDTH, h / (double) BASE_HEIGHT);
        int offsetX = (int) Math.round((w - (BASE_WIDTH * scale)) / 2.0);
        int offsetY = (int) Math.round((h - (BASE_HEIGHT * scale)) / 2.0);

        setScaledBounds(btnContinuar, 540, 500, 120, 40, scale, offsetX, offsetY);

        setScaledBounds(lblTitulo, 500, 100, 200, 20, scale, offsetX, offsetY);
        setScaledBounds(lblPokemonJugador, 100, 150, 132, 132, scale, offsetX, offsetY);
        setScaledBounds(pbVidaJugador, 100, 300, 132, 20, scale, offsetX, offsetY);
        setScaledBounds(lblNombreJugador, 100, 330, 132, 20, scale, offsetX, offsetY);
        setScaledBounds(lblEscudoJugador, 232, 330, 20, 20, scale, offsetX, offsetY);

        setScaledBounds(lblPokemonCPU, 900, 150, 132, 132, scale, offsetX, offsetY);
        setScaledBounds(pbVidaCPU, 900, 300, 132, 20, scale, offsetX, offsetY);
        setScaledBounds(lblNombreCPU, 900, 330, 132, 20, scale, offsetX, offsetY);
        setScaledBounds(lblEscudoCPU, 1032, 330, 20, 20, scale, offsetX, offsetY);

        setScaledBounds(panelBotones, 0, 450, 1200, 100, scale, offsetX, offsetY);
        setScaledBounds(panelEquipoVivo, 1000, 450, 150, 100, scale, offsetX, offsetY);
        setScaledBounds(scrollLog, 0, 0, 1200, 100, scale, offsetX, offsetY);

        if (panelBracket != null) {
            panelBracket.revalidate();
            panelBracket.repaint();
        }
        if (bracketPanel != null) {
            bracketPanel.revalidate();
            bracketPanel.repaint();
        }
    }

    private void setScaledBounds(JComponent comp, int x, int y, int w, int h, double scale, int offsetX, int offsetY) {
        if (comp == null) {
            return;
        }
        int nx = (int) Math.round(x * scale) + offsetX;
        int ny = (int) Math.round(y * scale) + offsetY;
        int nw = (int) Math.round(w * scale);
        int nh = (int) Math.round(h * scale);
        comp.setBounds(nx, ny, nw, nh);
    }

    private void mostrarBracket(boolean reproducirSonido) {
        batallaIniciada = false;
        puedeActuar = false;
        habilitarBotones(false);

        panelCombate.setVisible(false);
        panelBracket.setVisible(true);
        btnContinuar.setVisible(true);
        if (!torneo.isTorneoTerminado()) {
            btnContinuar.setEnabled(true);
            btnContinuar.setText("Continuar");
        }

        fondo.setImagen("torneo.jpg");
        bracketPanel.repaint();
        fondo.revalidate();
        fondo.repaint();

        detenerMusica();
        if (reproducirSonido) {
            reproducirMusica("bienvenida.wav", true);
        }
    }

    private void realizarAccion(String accion) {
        if (!batallaIniciada || !puedeActuar) {
            return;
        }

        if ("cambiar".equals(accion)) {
            puedeActuar = false;
            habilitarBotones(false);
            manejarCambioPokemon();
            return;
        }

        puedeActuar = false;
        habilitarBotones(false);

        int opcion;
        switch (accion) {
            case "ataque":
                opcion = 1;
                break;
            case "ataqueEspecial":
                opcion = 2;
                break;
            case "defensa":
                opcion = 3;
                break;
            case "defensaEspecial":
                opcion = 4;
                break;
            case "cambiar":
                opcion = 5;
                break;
            default:
                return;
        }

        String resultado = manejoBatalla.turnoJugador(opcion);
        appendLog(resultado);
        ejecutarAnimacionPorAccion(true, manejoBatalla.getUltimaAccionJugador());
        actualizarVistaCombate();
        if(manejoBatalla.batallaTerminado()){
            finalizarBatalla();
            return;
        }
        Timer t = new Timer (800, e -> {
            ((Timer) e.getSource()).stop();
            turnoCPU();
        });
        t.setRepeats(false);
        t.start();
       
    }
    private void turnoCPU(){
        if(!batallaIniciada)return;
        
        String resultado = manejoBatalla.turnoCPU();
        appendLog(resultado);
        ejecutarAnimacionPorAccion(false, manejoBatalla.getUltimaAccionCPU());
        actualizarVistaCombate();
        if(manejoBatalla.batallaTerminado()){
            finalizarBatalla();
            return;
        }
        puedeActuar = true;
        actualizarVistaCombate();
        appendLog("Turno del jugador\n");
    }

    private void finalizarBatalla() {
        if (!jugadorBatalla.tienePokemonVivos()) {
            detenerMusica();
            torneo.finalizarTorneoPorDerrota(cpuBatalla.getNombre());
            mostrarBracket(false);
            btnContinuar.setEnabled(false);
            btnContinuar.setText("Torneo finalizado");
            bracketPanel.repaint();
            PantallaDerrota derrota = new PantallaDerrota(torneo.getGanadorFinal());
            derrota.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    reproducirMusica("bienvenida.wav", true);
                }
            });
            derrota.setVisible(true);
            return;
        }
        String ganador = manejoBatalla.obtenerGanador();
    //mostrar resultado en el log
    appendLog("\n==============================\n");
    appendLog("La batalla terminó.\n");
    appendLog("Ganador: " + ganador + "\n");
    appendLog("==============================\n");

    //avanzar torneo
    NodoTorneo siguiente = torneo.getSiguienteCombate();
    boolean torneoFinalizado = (siguiente == null);

    if (!torneoFinalizado) {

        ListaPokemon nuevoEquipoCPU = torneo.getEquipoActualCPU();

        if (nuevoEquipoCPU != null) {

            // actualizar equipo CPU
            this.equipoCPU = nuevoEquipoCPU;

            // recrear combate
            ColaTurnos nuevaColaCPU = convertirListaACola(equipoCPU);
            this.cpuBatalla = new JugadorCPU("CPU",nuevaColaCPU);
       
            this.manejoBatalla = new ManejoBatalla(jugadorBatalla, cpuBatalla);
            
            puedeActuar = true;
            batallaIniciada = false;
            actualizarVistaCombate();
            
            appendLog("\n--- Nueva batalla (Ronda " + torneo.getRondaActual() + ") ---\n");
            appendLog("Presiona 'Continuar' para seguir.\n");

            // volver al bracket
            mostrarBracket(false);

        } else {
            appendLog("Error: no se pudo cargar el siguiente combate.\n");
        }

    }  if (torneoFinalizado){
        //  torneo terminado
        
    String ganadorFinal = manejoBatalla.obtenerGanador();
    appendLog("¡TORNEO FINALIZADO! Ganador: " + ganadorFinal + "\n");
    torneo.setGanadorFinal(ganadorFinal); // método sencillo en ArbolTorneo
    mostrarBracket(false);
    btnContinuar.setEnabled(false);
    btnContinuar.setText("Torneo finalizado");  
    }

    // refrescar vista del bracket
    bracketPanel.repaint();

    detenerMusica();
    PantallaVictoria victoria;
    if (torneoFinalizado) {
        victoria = new PantallaVictoria("Has ganado el torneo", "Eres el Maestro Pokemon");
    } else {
        victoria = new PantallaVictoria("Has ganado la batalla", null);
    }
    victoria.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            reproducirMusica("bienvenida.wav", true);
        }
    });
    victoria.setVisible(true);
 
    }

    private void actualizarVistaCombate() {
        Pokemon pokemonJugador = manejoBatalla.getPokemonJugador();
        Pokemon pokemonCPU = manejoBatalla.getPokemonCPU();

        if (pokemonJugador != null) {
            lblNombreJugador.setText(pokemonJugador.getNombre());
            lblPokemonJugador.setIcon(cargarImagenPokemon(pokemonJugador.getNombre()));
            lblPokemonJugador.setOpacity(1.0f);

            pbVidaJugador.setMaximum(obtenerHpMaximo(pokemonJugador));
            pbVidaJugador.setValue(pokemonJugador.getHp());
            actualizarTextoBarra(pbVidaJugador, pokemonJugador);
        } else {
            lblNombreJugador.setText("");
            lblPokemonJugador.setIcon(new ImageIcon());
            pbVidaJugador.setValue(0);
            pbVidaJugador.setString("0%");
        }

        if (pokemonCPU != null) {
            lblNombreCPU.setText(pokemonCPU.getNombre());
            lblPokemonCPU.setIcon(cargarImagenPokemon(pokemonCPU.getNombre()));
            lblPokemonCPU.setOpacity(1.0f);

            pbVidaCPU.setMaximum(obtenerHpMaximo(pokemonCPU));
            pbVidaCPU.setValue(pokemonCPU.getHp());
            actualizarTextoBarra(pbVidaCPU, pokemonCPU);
        } else {
            lblNombreCPU.setText("");
            lblPokemonCPU.setIcon(new ImageIcon());
            pbVidaCPU.setValue(0);
            pbVidaCPU.setString("0%");
        }

        btnAtaqueBasico.setEnabled(batallaIniciada && puedeActuar && pokemonJugador != null);
        btnDefensa.setEnabled(batallaIniciada && puedeActuar && pokemonJugador != null);
        btnCambiarPokemon.setEnabled(batallaIniciada && puedeActuar && pokemonJugador != null);

        btnAtaqueEspecial.setEnabled(
                batallaIniciada && puedeActuar && pokemonJugador != null
                && pokemonJugador.getCooldownAtaqueEspecial() == 0
        );

        btnDefensaEspecial.setEnabled(
                batallaIniciada && puedeActuar && pokemonJugador != null
                && pokemonJugador.getCooldownDefensaEspecial() == 0
        );

        actualizarEscudos();
        actualizarEquipoVivo();
        panelCombate.revalidate();
        panelCombate.repaint();
    }

    private void actualizarTextoBarra(JProgressBar bar, Pokemon pokemon) {
        int hpActual = pokemon.getHp();
        int hpMaximo = obtenerHpMaximo(pokemon);
        int porcentaje = hpMaximo > 0 ? (hpActual * 100) / hpMaximo : 0;
        bar.setString(String.format("%d%% (%d/%d)", porcentaje, hpActual, hpMaximo));
    }

    private ImageIcon cargarImagenPokemon(String nombrePokemon) {
        return cargarImagenPokemon(nombrePokemon, 132, 132);
    }

    private ImageIcon cargarImagenPokemon(String nombrePokemon, int ancho, int alto) {
        String ruta = "/pokemonproyecto/img/" + nombrePokemon.toLowerCase() + ".png";
        java.net.URL url = getClass().getResource(ruta);
        if(url == null){
            BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            g2d.setColor(new Color(30,30,30,180));
            g2d.fillRoundRect(0, 0, ancho, alto, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif",Font.BOLD,12));
            g2d.drawString(nombrePokemon, 10, alto / 2);
            g2d.dispose();
            return new ImageIcon(img);
        }
        ImageIcon icon = new ImageIcon(url);
        Image imagenEscalada = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    private ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        java.net.URL url = getClass().getResource(ruta);
        if (url == null) {
            return null;
        }
        ImageIcon icono = new ImageIcon(url);
        Image imagenEscalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    private void actualizarEscudos() {
        if (manejoBatalla.isJugadorDefendiendoEspecial()) {
            lblEscudoJugador.setIcon(escudoEspecialIcon);
        } else if (manejoBatalla.isJugadorDefendiendo()) {
            lblEscudoJugador.setIcon(escudoNormalIcon);
        } else {
            lblEscudoJugador.setIcon(null);
        }

        if (manejoBatalla.isCpuDefendiendoEspecial()) {
            lblEscudoCPU.setIcon(escudoEspecialIcon);
        } else if (manejoBatalla.isCpuDefendiendo()) {
            lblEscudoCPU.setIcon(escudoNormalIcon);
        } else {
            lblEscudoCPU.setIcon(null);
        }
    }

    private void ejecutarAnimacionPorAccion(boolean esJugador, int accion) {
        FadeLabel atacante = esJugador ? lblPokemonJugador : lblPokemonCPU;
        FadeLabel defensor = esJugador ? lblPokemonCPU : lblPokemonJugador;
        int desplazamiento = esJugador ? 40 : -40;
        if (accion == 1) {
            animarAtaque(atacante, defensor, desplazamiento, new Color(255, 69, 0));
        } else if (accion == 2) {
            animarAtaque(atacante, defensor, desplazamiento, new Color(255, 140, 0));
        }
    }

    private void animarAtaque(FadeLabel atacante, FadeLabel defensor, int desplazamiento, Color colorEfecto) {
        if (atacante == null || defensor == null) {
            return;
        }
        int xInicial = atacante.getX();
        int pasos = 10;
        final Contador paso = new Contador();

        Timer timer = new Timer(25, null);
        timer.addActionListener(e -> {
            int mitad = pasos / 2;
            if (paso.valor < mitad) {
                atacante.setLocation(xInicial + (desplazamiento * (paso.valor + 1)) / mitad, atacante.getY());
            } else if (paso.valor == mitad) {
                aplicarEfectoImpacto(defensor, colorEfecto);
            } else if (paso.valor < pasos) {
                int retroceso = pasos - paso.valor;
                atacante.setLocation(xInicial + (desplazamiento * retroceso) / mitad, atacante.getY());
            }
            paso.valor++;
            if (paso.valor >= pasos) {
                atacante.setLocation(xInicial, atacante.getY());
                timer.stop();
            }
        });
        timer.start();
    }

    private void aplicarEfectoImpacto(JLabel defensor, Color color) {
        if (defensor == null) {
            return;
        }
        defensor.setBorder(BorderFactory.createLineBorder(color, 3));
        Timer t = new Timer(150, e -> {
            defensor.setBorder(null);
            ((Timer) e.getSource()).stop();
        });
        t.setRepeats(false);
        t.start();
    }

    private void manejarCambioPokemon() {
        String seleccionado = mostrarSelectorPokemon();
        if (seleccionado == null) {
            puedeActuar = true;
            habilitarBotones(true);
            return;
        }

        String resultado = manejoBatalla.cambiarPokemonJugador(seleccionado);
        appendLog(resultado);
        actualizarVistaCombate();
        if (manejoBatalla.batallaTerminado()) {
            finalizarBatalla();
            return;
        }

        Timer t = new Timer(800, e -> {
            ((Timer) e.getSource()).stop();
            turnoCPU();
        });
        t.setRepeats(false);
        t.start();
    }

    private String mostrarSelectorPokemon() {
        SeleccionPokemon seleccion = new SeleccionPokemon();
        JDialog dialogo = new JDialog(this, "Elige tu Pokémon", true);
        dialogo.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        ListaPokemon equipo = jugadorBatalla.obtenerEquipo();
        int i = 0;
        Pokemon actual = equipo.obtener(i);
        Pokemon activo = manejoBatalla.getPokemonJugador();
        while (actual != null) {
            Pokemon pokemonBtn = actual;
            JButton btn = new JButton(pokemonBtn.getNombre());
            btn.setIcon(cargarImagenPokemon(pokemonBtn.getNombre(), 60, 60));
            if (activo != null && pokemonBtn.getNombre().equals(activo.getNombre())) {
                btn.setEnabled(false);
                btn.setText(pokemonBtn.getNombre() + " (Activo)");
            }
            btn.addActionListener(e -> {
                seleccion.nombre = pokemonBtn.getNombre();
                seleccion.confirmada = true;
                dialogo.dispose();
            });
            panel.add(btn);
            i++;
            actual = equipo.obtener(i);
        }

        JPanel panelBotones = new JPanel();
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialogo.dispose());
        panelBotones.add(btnCancelar);

        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);

        return seleccion.confirmada ? seleccion.nombre : null;
    }

    private void actualizarEquipoVivo() {
        panelEquipoVivo.removeAll();

        Pokemon actual = manejoBatalla.getPokemonJugador();
        if (actual != null) {
            JLabel lbl = new JLabel("Activo: " + actual.getNombre());
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            panelEquipoVivo.add(lbl);
        }

        panelEquipoVivo.revalidate();
        panelEquipoVivo.repaint();
    }

    private void appendLog(String texto) {
        txtLog.setText(txtLog.getText() + texto);
    }

    private void habilitarBotones(boolean estado) {
        btnAtaqueBasico.setEnabled(estado);
        btnAtaqueEspecial.setEnabled(estado);
        btnDefensa.setEnabled(estado);
        btnDefensaEspecial.setEnabled(estado);
        btnCambiarPokemon.setEnabled(estado);
    }

    private void animarPulsacion(JLabel label) {
        Timer timer = new Timer(100, new ActionListener() {
            int frame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                frame++;
                if (frame == 1) {
                    label.setFont(new Font("SansSerif", Font.BOLD, 17));
                } else if (frame == 2) {
                    label.setFont(new Font("SansSerif", Font.BOLD, 16));
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void reproducirMusica(String archivo, boolean loop) {
        try {
            detenerMusica();
            java.net.URL url = getClass().getResource("/sonido/"+ archivo);
            if(url == null){
                return;
            }
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (Exception e) {
        }
    }

    private void detenerMusica() {
        try {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        } catch (Exception e) {
        }
    }

    private class FondoPanel extends JPanel {
        private String imagenActual = "torneo.jpg";

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                BufferedImage image = ImageIO.read(getClass().getResource("/pokemonproyecto/img/" + imagenActual));
                int panelW = getWidth();
                int panelH = getHeight();
                int imgW = image.getWidth();
                int imgH = image.getHeight();
                double scale = Math.max(panelW / (double) imgW, panelH / (double) imgH);
                int drawW = (int) Math.round(imgW * scale);
                int drawH = (int) Math.round(imgH * scale);
                int x = (panelW - drawW) / 2;
                int y = (panelH - drawH) / 2;
                Image scaledImage = image.getScaledInstance(drawW, drawH, Image.SCALE_SMOOTH);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2d.drawImage(scaledImage, x, y, null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            } catch (IOException e) {
            }
        }

        public void setImagen(String imagen) {
            this.imagenActual = imagen;
            repaint();
        }
    }

    private class FadeLabel extends JLabel {
        private float opacity = 1.0f;

        public FadeLabel(ImageIcon icon) {
            super(icon);
            setOpaque(false);
        }

        public void setOpacity(float opacity) {
            this.opacity = opacity;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paintComponent(g2d);
        }
    }

}

