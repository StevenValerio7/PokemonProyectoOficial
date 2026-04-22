package principal;

import gui.SeleccionPokemonGUI;

public class BarraProgreso extends javax.swing.JFrame {

    public BarraProgreso() {
        initComponents();
        setTitle("Cargando Juego");
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        LoadingBar = new javax.swing.JProgressBar();
        LoadingValue = new javax.swing.JLabel();
        LoadingLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(LoadingBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 560, 10));

        LoadingValue.setFont(new java.awt.Font("Segoe UI", 2, 10));
        LoadingValue.setForeground(new java.awt.Color(255, 255, 255));
        LoadingValue.setText("0%");
        getContentPane().add(LoadingValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, -1, -1));

        LoadingLabel.setFont(new java.awt.Font("Segoe UI", 2, 10));
        LoadingLabel.setForeground(new java.awt.Color(255, 255, 255));
        LoadingLabel.setText("Loading");
        getContentPane().add(LoadingLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 170, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemonproyecto/img/Dise\u00F1o_sin_t\u00EDtulo__2_-removebg-preview.png")));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 200, 200));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemonproyecto/img/FondoVerde.jpg")));
        jLabel1.setText("Cargando....");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, 560, 410));

        pack();
    }

    public void iniciarCarga() {
        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(65);
                    LoadingValue.setText(i + "%");

                    if (i == 10) LoadingLabel.setText("Cargando el Sistema");
                    if (i == 20) LoadingLabel.setText("Cargando Gr\u00E1ficos");
                    if (i == 30) LoadingLabel.setText("Cargando los Servidores");
                    if (i == 50) LoadingLabel.setText("Creando Combates");
                    if (i == 60) LoadingLabel.setText("Corriendo el Programa");
                    if (i == 80) LoadingLabel.setText("Actualizando el Sistema");
                    if (i == 90) LoadingLabel.setText("Ya casi est\u00E1 listo");

                    LoadingBar.setValue(i);
                }

                dispose();
                new SeleccionPokemonGUI().setVisible(true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private javax.swing.JProgressBar LoadingBar;
    private javax.swing.JLabel LoadingLabel;
    private javax.swing.JLabel LoadingValue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
}
