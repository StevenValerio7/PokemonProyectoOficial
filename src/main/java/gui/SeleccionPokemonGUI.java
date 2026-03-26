/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import Pokemones.*;
import modelo.*;
import estructuras.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author jimen
 */
public class SeleccionPokemonGUI extends JFrame {
    private int cantidadSeleccionada = 0;
    private Jugador jugador;

    private JLabel lblMensaje;
    private JLabel seleccion1;
    private JLabel seleccion2;
    private JLabel seleccion3;
    private JLabel seleccion4;

    private String nombreSeleccion1;
    private String nombreSeleccion2;
    private String nombreSeleccion3;
    private String nombreSeleccion4;

    private Clip clip;
    
    private ImageIcon cargarImagen(String ruta, int ancho, int alto){
        try{
            java.net.URL url = getClass().getResource(ruta);
            if(url == null){
                System.out.println("No se encontro: "+ ruta);
                return null;
            }
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e){
            e.printStackTrace();
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
        System.out.println(getClass().getResource("/pokemonproyecto/img/snorlax.png"));
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
        fondo.add(lblTitulo, BorderLayout.NORTH);
        
        //Panel central 
        JPanel panelCentral = new JPanel(new GridLayout(3,3,5,5));
        panelCentral.setOpaque(false);
        panelCentral.setPreferredSize(new Dimension(400,400));
        
        // Botones de Pokémon
        String[] nombres = {"Snorlax","Meowth","Pidgey","Charmander","Growlithe","Magmar","Squirtle","Psyduck","Poliwag"};

        int index = 0;
        for(String nombre : nombres){

            JButton btnPokemon = new JButton(nombre);
            btnPokemon.setIcon(cargarImagen("/pokemonproyecto/img/" + nombre.toLowerCase() + ".png", 80,80));
            btnPokemon.setPreferredSize(new Dimension(70, 70));
            btnPokemon.setHorizontalTextPosition(SwingConstants.CENTER);
            btnPokemon.setVerticalTextPosition(SwingConstants.BOTTOM);
            btnPokemon.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            btnPokemon.setFocusPainted(false);
            btnPokemon.setContentAreaFilled(true);
            btnPokemon.setBackground(Color.WHITE);
            
            btnPokemon.addMouseListener(new MouseAdapter(){
                public void MouseEntered(MouseEvent e){
                    btnPokemon.setBackground(new Color(200,200,255));
                    btnPokemon.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e){
                    btnPokemon.setBackground(UIManager.getColor("Button.background"));
                }
            });
            
            btnPokemon.addActionListener(e -> {
                    seleccionarPokemon(nombre);
            btnPokemon.setBorder(BorderFactory.createLineBorder(Color.YELLOW,3));
            });   
           panelCentral.add(btnPokemon);
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

            if(!resultado.contains("No se permiten")){

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
        cantidadSeleccionada--;

        switch(posicion){
            case 1: seleccion1.setIcon(null); break;
            case 2: seleccion2.setIcon(null); break;
            case 3: seleccion3.setIcon(null); break;
            case 4: seleccion4.setIcon(null); break;
        }

        lblMensaje.setText(cantidadSeleccionada + "/4 Pokémon seleccionados");
    }

    private void comenzarBatalla(){
        if(cantidadSeleccionada == 4){
            //CPU vacio
            JugadorCPU cpu = new JugadorCPU("CPU", new ColaTurnos());
            
            //Crear torneo
            ArbolTorneo torneo = new ArbolTorneo();
            
            //Obtener primer equipo del torneo 
            ListaPokemon equipoCPU = torneo.getEquipoActualCPU();
            
            //Pokemones a CPU
            NodoPokemon actual = equipoCPU.getCabeza();
            while(actual != null){
                cpu.agregarPokemonCPU(actual.pokemon);
                actual = actual.siguiente;
            }
            // Crear combate 
            Combate combate = new Combate(jugador,cpu);
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
            e.printStackTrace();
        }
    }

    private void detenerMusica(){
        if(clip != null){
            clip.stop();
            clip.close();
        }
    }

    public static void main(String[] args){
        new SeleccionPokemonGUI().setVisible(true);
    }
}

