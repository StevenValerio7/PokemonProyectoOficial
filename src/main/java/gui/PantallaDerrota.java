package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PantallaDerrota extends JFrame {

    private Clip clip;

    public PantallaDerrota() {
        this(null);
    }

    public PantallaDerrota(String ganador) {
        setTitle("Has perdido");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        initComponents(ganador);
        setSize(600, 350);
        setLocationRelativeTo(null);
        reproducirSonido();
    }

    private void initComponents(String ganador) {
        ImageIcon fondoIcon = new ImageIcon(getClass().getResource("/pokemonproyecto/img/derrota.jpg"));
        Image fondoImg = fondoIcon.getImage().getScaledInstance(600, 350, Image.SCALE_SMOOTH);
        JLabel fondo = new JLabel(new ImageIcon(fondoImg));
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);

        JLabel lblTitulo = new JLabel("Has perdido!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        fondo.add(lblTitulo, BorderLayout.NORTH);

        if (ganador != null && !ganador.trim().isEmpty()) {
            JLabel lblGanador = new JLabel("Ganador del torneo: " + ganador, SwingConstants.CENTER);
            lblGanador.setFont(new Font("Arial", Font.BOLD, 16));
            lblGanador.setForeground(Color.WHITE);
            fondo.add(lblGanador, BorderLayout.CENTER);
        }

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        JButton btnVer = new JButton("Ver torneo");
        btnVer.addActionListener(e -> dispose());
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        panelBoton.add(btnVer);
        panelBoton.add(btnSalir);
        fondo.add(panelBoton, BorderLayout.SOUTH);
    }

    private void reproducirSonido() {
        try {
            java.net.URL url = getClass().getResource("/sonido/derrota.wav");
            if (url == null) {
                return;
            }
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception e) {
        }
    }

    @Override
    public void dispose() {
        try {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        } catch (Exception e) {
        }
        super.dispose();
    }
}
