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

public class PantallaVictoria extends JFrame {

    private Clip clip;

    public PantallaVictoria(String titulo, String subtitulo) {
        setTitle("Has ganado");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        initComponents(titulo, subtitulo);
        setSize(600, 350);
        setLocationRelativeTo(null);
        reproducirSonido();
    }

    private void initComponents(String titulo, String subtitulo) {
        ImageIcon fondoIcon = new ImageIcon(getClass().getResource("/pokemonproyecto/img/victoria.png"));
        Image fondoImg = fondoIcon.getImage().getScaledInstance(600, 350, Image.SCALE_SMOOTH);
        JLabel fondo = new JLabel(new ImageIcon(fondoImg));
        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);

        JLabel lblTitulo = new JLabel(titulo != null ? titulo : "Has ganado", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        fondo.add(lblTitulo, BorderLayout.NORTH);

        if (subtitulo != null && !subtitulo.trim().isEmpty()) {
            JLabel lblSub = new JLabel(subtitulo, SwingConstants.CENTER);
            lblSub.setFont(new Font("Arial", Font.BOLD, 18));
            lblSub.setForeground(Color.WHITE);
            fondo.add(lblSub, BorderLayout.CENTER);
        }

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(e -> dispose());
        panelBoton.add(btnContinuar);
        fondo.add(panelBoton, BorderLayout.SOUTH);
    }

    private void reproducirSonido() {
        try {
            java.net.URL url = getClass().getResource("/sonido/victoria.wav");
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
