package gui;
import Pokemones.*;
import modelo.*;
import estructuras.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
public class SeleccionPokemonGUI extends JFrame {
    private int cantidadSeleccionada = 0;
    private Jugador jugador;
    private NodoBoton botones;

    private JLabel lblMensaje;
    private JLabel seleccion1;
    private JLabel seleccion2;
    private JLabel seleccion3;
    private JLabel seleccion4;
    private JTextField txtNombre;

    private String nombreSeleccion1;
    private String nombreSeleccion2;
    private String nombreSeleccion3;
    private String nombreSeleccion4;

    private Clip clip;

    private static class NodoBoton {
        String nombre;
        JButton boton;
        NodoBoton siguiente;

        NodoBoton(String nombre, JButton boton) {
            this.nombre = nombre;
            this.boton = boton;
            this.siguiente = null;
        }
    }
    
    private ImageIcon cargarImagen(String ruta, int ancho, int alto){
        try{
            java.net.URL url = getClass().getResource(ruta);
            if(url == null){
                return null;
            }
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e){
            return null;
        }
    }
    
    public SeleccionPokemonGUI() {
        jugador = new Jugador("Jugador", new ColaTurnos());

        initComponents();

        setTitle("Selecciona tus Pokémon");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
       
        reproducirMusica("pokemones.wav", true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        //Fondo 
        
        ImageIcon fondoIcon = new ImageIcon(getClass().getResource("/pokemonproyecto/img/FondoPantalla.png"));
        Image fondoImg = fondoIcon.getImage().getScaledInstance(800, 650, Image.SCALE_SMOOTH);
        JLabel fondo = new JLabel(new ImageIcon(fondoImg));
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);
        
        //Titulo
        JLabel lblTitulo = new JLabel("Selecciona 4 Pokémon:", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        JPanel panelNombre = new JPanel();
        panelNombre.setOpaque(false);
        JLabel lblNombre = new JLabel("Tu nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setForeground(Color.WHITE);
        txtNombre = new JTextField(12);
        panelNombre.add(lblNombre);
        panelNombre.add(txtNombre);

        JPanel panelTitulo = new JPanel(new GridLayout(2,1));
        panelTitulo.setOpaque(false);
        panelTitulo.add(lblTitulo);
        panelTitulo.add(panelNombre);
        fondo.add(panelTitulo, BorderLayout.NORTH);
        
        //Panel central 
        JPanel panelCentral = new JPanel(new GridLayout(3,3,5,5));
        panelCentral.setOpaque(false);
        panelCentral.setPreferredSize(new Dimension(400,400));
        
        // Botones de Pokémon
        ListaPokemon catalogo = crearCatalogo();
        int index = 0;
        Pokemon actual = catalogo.obtener(index);
        while(actual != null){
            String nombre = actual.getNombre();

            JButton btnPokemon = new JButton(nombre);
            btnPokemon.setIcon(cargarImagen("/pokemonproyecto/img/" + nombre.toLowerCase() + ".png", 80,80));
            btnPokemon.setPreferredSize(new Dimension(70, 70));
            btnPokemon.setHorizontalTextPosition(SwingConstants.CENTER);
            btnPokemon.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            btnPokemon.setFocusPainted(false);
            btnPokemon.setContentAreaFilled(true);
            btnPokemon.setBackground(Color.WHITE);
            registrarBoton(nombre, btnPokemon);
            
            btnPokemon.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e){
                    btnPokemon.setBackground(new Color(200,200,255));
                    btnPokemon.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e){
                    btnPokemon.setBackground(UIManager.getColor("Button.background"));
                }
            });
            
            btnPokemon.addActionListener(e -> {
                    seleccionarPokemon(nombre);
            });   
           panelCentral.add(btnPokemon);

            index++;
            actual = catalogo.obtener(index);
        }
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setOpaque(false);
        contenedor.add(panelCentral);
        fondo.add(contenedor, BorderLayout.CENTER);

        lblMensaje = new JLabel("0/4 Pokémon seleccionados");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(Color.WHITE);
        
        

        // Panel equipo
        JPanel panelEquipo = new JPanel(new GridLayout(4,1,8,8));
        panelEquipo.setOpaque(false);
        
        panelEquipo.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE,2),"Tú equipo",
                0,0, new Font("Arial",Font.BOLD, 16), Color.WHITE
        ));

        seleccion1 = crearLabelSeleccion(1);
        seleccion2 = crearLabelSeleccion(2);
        seleccion3 = crearLabelSeleccion(3);
        seleccion4 = crearLabelSeleccion(4);

        panelEquipo.add(seleccion1);
        panelEquipo.add(seleccion2);
        panelEquipo.add(seleccion3);
        panelEquipo.add(seleccion4);
        
        fondo.add(panelEquipo,BorderLayout.EAST);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        JButton btnComenzar = new JButton("Comenzar Batalla");
        btnComenzar.addActionListener(e -> comenzarBatalla());

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> {
            detenerMusica();
            System.exit(0);
        });

        panelBotones.add(btnComenzar);
        panelBotones.add(btnSalir);

        JPanel sur = new JPanel(new BorderLayout());
        sur.setOpaque(false);
        sur.add(lblMensaje, BorderLayout.NORTH);
        sur.add(panelBotones, BorderLayout.SOUTH);
        fondo.add(sur,BorderLayout.SOUTH);
    }

    private ListaPokemon crearCatalogo(){
        ListaPokemon lista = new ListaPokemon();
        lista.agregar(new Snorlax());
        lista.agregar(new Meowth());
        lista.agregar(new Pidgey());
        lista.agregar(new Charmander());
        lista.agregar(new Growlithe());
        lista.agregar(new Magmar());
        lista.agregar(new Squirtle());
        lista.agregar(new Psyduck());
        lista.agregar(new Poliwag());
        return lista;
    }

    private void registrarBoton(String nombre, JButton boton) {
        NodoBoton nuevo = new NodoBoton(nombre, boton);
        if (botones == null) {
            botones = nuevo;
        } else {
            NodoBoton actual = botones;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    private JButton buscarBoton(String nombre) {
        NodoBoton actual = botones;
        while (actual != null) {
            if (actual.nombre.equals(nombre)) {
                return actual.boton;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    private void setBotonHabilitado(String nombre, boolean habilitado) {
        JButton boton = buscarBoton(nombre);
        if (boton != null) {
            boton.setEnabled(habilitado);
            if (habilitado) {
                boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            } else {
                boton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            }
        }
    }

    private JLabel crearLabelSeleccion(int posicion){
        JLabel lbl = new JLabel();
        lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        lbl.setPreferredSize(new Dimension(85,85));
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(Color.WHITE);

        lbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                deseleccionarPokemon(posicion);
            }
        });

        return lbl;
    }

    private void seleccionarPokemon(String nombre){

        if(cantidadSeleccionada < 4){

            Pokemon nuevoPokemon = crearPokemon(nombre);

            String resultado = jugador.agregarPokemon(nuevoPokemon);

            boolean agregado = resultado.contains("fue agregado");
            if(agregado){

                cantidadSeleccionada++;
                ImageIcon icon = cargarImagen("/pokemonproyecto/img/"+nombre.toLowerCase()+".png",80,80);

                if(seleccion1.getIcon() == null){
                    seleccion1.setIcon(icon);
                    nombreSeleccion1 = nombre;
                } else if(seleccion2.getIcon() == null){
                    seleccion2.setIcon(icon);
                    nombreSeleccion2 = nombre;
                } else if(seleccion3.getIcon() == null){
                    seleccion3.setIcon(icon);
                    nombreSeleccion3 = nombre;
                } else if(seleccion4.getIcon() == null){
                    seleccion4.setIcon(icon);
                    nombreSeleccion4 = nombre;
                }
                setBotonHabilitado(nombre, false);
            }

            lblMensaje.setText(resultado);

        } else {
            lblMensaje.setText("Ya seleccionasté 4 Pokémon");
        }
    }

    private Pokemon crearPokemon(String nombre){
        switch(nombre){
            case "Snorlax": return new Snorlax();
            case "Meowth": return new Meowth();
            case "Pidgey": return new Pidgey();
            case "Charmander": return new Charmander();
            case "Growlithe": return new Growlithe();
            case "Magmar": return new Magmar();
            case "Squirtle": return new Squirtle();
            case "Psyduck": return new Psyduck();
            case "Poliwag": return new Poliwag();
        }
        return null;
    }

    private void deseleccionarPokemon(int posicion){
        String nombre = null;
        switch(posicion){
            case 1:
                if(seleccion1.getIcon() == null){ return; }
                nombre = nombreSeleccion1;
                seleccion1.setIcon(null);
                nombreSeleccion1 = null;
                break;
            case 2:
                if(seleccion2.getIcon() == null){ return; }
                nombre = nombreSeleccion2;
                seleccion2.setIcon(null);
                nombreSeleccion2 = null;
                break;
            case 3:
                if(seleccion3.getIcon() == null){ return; }
                nombre = nombreSeleccion3;
                seleccion3.setIcon(null);
                nombreSeleccion3 = null;
                break;
            case 4:
                if(seleccion4.getIcon() == null){ return; }
                nombre = nombreSeleccion4;
                seleccion4.setIcon(null);
                nombreSeleccion4 = null;
                break;
            default:
                return;
        }

        if (nombre != null) {
            boolean eliminado = jugador.eliminarPokemon(nombre);
            setBotonHabilitado(nombre, true);
            if (eliminado && cantidadSeleccionada > 0) {
                cantidadSeleccionada--;
            }
        }

        lblMensaje.setText(cantidadSeleccionada + "/4 Pokémon seleccionados");
    }

    private void comenzarBatalla(){
        String nombreJugador = txtNombre != null ? txtNombre.getText().trim() : "";
        if(nombreJugador.isEmpty()){
            lblMensaje.setText("Debes escribir tu nombre");
            return;
        }
        jugador.setNombre(nombreJugador);
        if(cantidadSeleccionada == 4){
            detenerMusica();
            JugadorCPU cpu = new JugadorCPU("CPU", new ColaTurnos());

            ArbolTorneo torneo = new ArbolTorneo(jugador.getNombre());
            ListaPokemon equipoCPU = torneo.getEquipoActualCPU();

            NodoPokemon actual = equipoCPU.getCabeza();
            while(actual != null){
                cpu.agregarPokemonCPU(actual.pokemon);
                actual = actual.siguiente;
            }
            Combate combate = new Combate(jugador, cpu, torneo);
            combate.setVisible(true);
            dispose();
            
        } else {
            lblMensaje.setText("Debes seleccionar 4 Pokemon");
        
    }
    }

    private void reproducirMusica(String archivo, boolean loop){
        try{
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("/sonido/"+archivo));
            clip = AudioSystem.getClip();
            clip.open(audio);
        if(loop){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } 
        clip.start();
        }catch(Exception e){
        }
    }

    private void detenerMusica(){
        if(clip != null){
            clip.stop();
            clip.close();
        }
    }

}




