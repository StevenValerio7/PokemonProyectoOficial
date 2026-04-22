package gui;

import javax.swing.*;
import java.awt.*;
import estructuras.*;

public class BracketPanel extends JPanel {

    private ArbolTorneo torneo;
    private static final int BASE_WIDTH = 1200;
    private static final int BASE_HEIGHT = 600;

    public BracketPanel(ArbolTorneo torneo) {
        this.torneo = torneo;
        setOpaque(false);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double sx = getWidth() / (double) BASE_WIDTH;
        double sy = getHeight() / (double) BASE_HEIGHT;
        double scale = Math.min(sx, sy);
        int offsetX = (int) Math.round((getWidth() - (BASE_WIDTH * scale)) / 2.0);
        int offsetY = (int) Math.round((getHeight() - (BASE_HEIGHT * scale)) / 2.0);
        g2d.translate(offsetX, offsetY);
        g2d.scale(scale, scale);

        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        Color colorEquipos = Color.WHITE;
        Color colorResultados = new Color(46, 139, 87);
        Color colorRondas = new Color(120, 200, 255);

        dibujarBracket(g2d, torneo.getEquiposJugador(), colorEquipos, colorResultados, colorRondas);

        if (torneo.isTorneoTerminado()) {
            String ganadorFinal = torneo.getGanadorFinal();
            if (ganadorFinal != null) {
                dibujarGanador(g2d, ganadorFinal);
            }
        }

        g2d.dispose();
    }

    private String nombreEquipo(ListaEquipos equipos, int idx) {
        String nombre = equipos.getNombre(idx);
        if (nombre == null || nombre.trim().isEmpty()) {
            return idx == 0 ? "Jugador" : "";
        }
        return nombre;
    }

    private int ganadorCuartosIdx(int partido) {
        if (partido == 0) return torneo.getGanadorCuartos1();
        if (partido == 1) return torneo.getGanadorCuartos2();
        if (partido == 2) return torneo.getGanadorCuartos3();
        if (partido == 3) return torneo.getGanadorCuartos4();
        return -1;
    }

    private int ganadorSemifinalIdx(int partido) {
        if (partido == 0) return torneo.getGanadorSemis1();
        if (partido == 1) return torneo.getGanadorSemis2();
        return -1;
    }

    private void dibujarBracket(Graphics2D g2d, ListaEquipos equipos, Color colorEquipos, Color colorResultados, Color colorRondas) {
        int rondaActual = torneo.getRondaActual();

        int areaX = 60;
        int areaY = 40;
        int areaW = 1080;
        int areaH = 520;

        drawPanelFondo(g2d, areaX, areaY, areaW, areaH);
        drawHeader(g2d, areaX, areaY, areaW);

        int panelWidth = 200;
        int panelHeight = 46;

        int yCuartos = areaY + 110;
        int ySpacingCuartos = 90;
        int ySemis = yCuartos + (ySpacingCuartos / 2);
        int ySpacingSemis = ySpacingCuartos * 2;
        int yFinal = ySemis + (ySpacingSemis / 2);

        int margenX = areaX + 30;
        int xCuartos = margenX;
        int xFinal = areaX + areaW - panelWidth - 30;
        int xSemis = areaX + (areaW - panelWidth) / 2;

        g2d.setColor(colorRondas);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        drawColumnTitle(g2d, "Cuartos de Final", xCuartos, yCuartos - 20, panelWidth);
        drawColumnTitle(g2d, "Semifinales", xSemis, ySemis - 20, panelWidth);
        drawColumnTitle(g2d, "Final", xFinal, yFinal - 20, panelWidth);

        boolean mostrarSemis = (rondaActual >= 2);
        boolean mostrarFinal = (rondaActual >= 3);

        for (int i = 0; i < 4; i++) {
            int y = yCuartos + i * ySpacingCuartos;
            int idx1 = i * 2;
            int idx2 = i * 2 + 1;
            String nombre1 = nombreEquipo(equipos, idx1);
            String nombre2 = nombreEquipo(equipos, idx2);

            boolean highlight = (rondaActual == 1 && torneo.getCombateEnRonda() == (i + 1));
            String texto = nombre1 + " vs " + nombre2;
            drawMatchBox(g2d, xCuartos, y, panelWidth, panelHeight, texto, new Color(40, 180, 160), colorEquipos, highlight);
        }

        for (int i = 0; i < 2; i++) {
            int y = ySemis + i * ySpacingSemis;
            int g1 = mostrarSemis ? ganadorCuartosIdx(i * 2) : -1;
            int g2 = mostrarSemis ? ganadorCuartosIdx(i * 2 + 1) : -1;
            String texto;
            if (g1 < 0 || g2 < 0) {
                texto = "Por definir";
            } else {
                texto = nombreEquipo(equipos, g1) + " vs " + nombreEquipo(equipos, g2);
            }
            boolean highlight = (rondaActual == 2 && torneo.getCombateEnRonda() == (i + 1));
            drawMatchBox(g2d, xSemis, y, panelWidth, panelHeight, texto, new Color(240, 160, 40), colorEquipos, highlight);

        }

        {
            int g1 = mostrarFinal ? ganadorSemifinalIdx(0) : -1;
            int g2 = mostrarFinal ? ganadorSemifinalIdx(1) : -1;
            String texto;
            if (g1 < 0 || g2 < 0) {
                texto = "Por definir";
            } else {
                texto = nombreEquipo(equipos, g1) + " vs " + nombreEquipo(equipos, g2);
            }
            boolean highlight = (rondaActual == 3);
            drawMatchBox(g2d, xFinal, yFinal, panelWidth, panelHeight, texto, new Color(220, 70, 80), colorEquipos, highlight);
        }

        int midXCuartos = xCuartos + panelWidth + ((xSemis - (xCuartos + panelWidth)) / 2);
        int midXSemis = xSemis + panelWidth + ((xFinal - (xSemis + panelWidth)) / 2);

        int q1Center = yCuartos + panelHeight / 2;
        int q2Center = yCuartos + ySpacingCuartos + panelHeight / 2;
        int q3Center = yCuartos + (ySpacingCuartos * 2) + panelHeight / 2;
        int q4Center = yCuartos + (ySpacingCuartos * 3) + panelHeight / 2;

        int semi1Center = ySemis + panelHeight / 2;
        int semi2Center = ySemis + ySpacingSemis + panelHeight / 2;
        int finalCenter = yFinal + panelHeight / 2;

        boolean q1Dec = mostrarSemis && (ganadorCuartosIdx(0) >= 0);
        boolean q2Dec = mostrarSemis && (ganadorCuartosIdx(1) >= 0);
        boolean q3Dec = mostrarSemis && (ganadorCuartosIdx(2) >= 0);
        boolean q4Dec = mostrarSemis && (ganadorCuartosIdx(3) >= 0);

        boolean semi1Dec = mostrarFinal && (ganadorSemifinalIdx(0) >= 0);
        boolean semi2Dec = mostrarFinal && (ganadorSemifinalIdx(1) >= 0);

        boolean pair1Dec = q1Dec && q2Dec;
        boolean pair2Dec = q3Dec && q4Dec;
        boolean finalDec = semi1Dec && semi2Dec;

        drawConnector(g2d, xCuartos + panelWidth, q1Center, midXCuartos, q1Center, q1Dec);
        drawConnector(g2d, xCuartos + panelWidth, q2Center, midXCuartos, q2Center, q2Dec);
        drawConnector(g2d, midXCuartos, q1Center, midXCuartos, q2Center, pair1Dec);
        drawConnector(g2d, midXCuartos, semi1Center, xSemis, semi1Center, pair1Dec);

        drawConnector(g2d, xCuartos + panelWidth, q3Center, midXCuartos, q3Center, q3Dec);
        drawConnector(g2d, xCuartos + panelWidth, q4Center, midXCuartos, q4Center, q4Dec);
        drawConnector(g2d, midXCuartos, q3Center, midXCuartos, q4Center, pair2Dec);
        drawConnector(g2d, midXCuartos, semi2Center, xSemis, semi2Center, pair2Dec);

        drawConnector(g2d, xSemis + panelWidth, semi1Center, midXSemis, semi1Center, semi1Dec);
        drawConnector(g2d, xSemis + panelWidth, semi2Center, midXSemis, semi2Center, semi2Dec);
        drawConnector(g2d, midXSemis, semi1Center, midXSemis, semi2Center, finalDec);
        drawConnector(g2d, midXSemis, finalCenter, xFinal, finalCenter, finalDec);
    }

    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        // Conector simple sin flechas (estilo bracket)
        g2d.drawLine(x1, y1, x2, y2);
    }

    private void drawPanelFondo(Graphics2D g2d, int x, int y, int w, int h) {
        Color sombra = new Color(0, 0, 0, 120);
        Color fondo = new Color(12, 16, 24, 150);
        g2d.setColor(sombra);
        g2d.fillRoundRect(x + 4, y + 6, w, h, 26, 26);
        g2d.setColor(fondo);
        g2d.fillRoundRect(x, y, w, h, 26, 26);
        g2d.setColor(new Color(120, 200, 255, 120));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(x, y, w, h, 26, 26);
    }

    private void drawHeader(Graphics2D g2d, int x, int y, int w) {
        int pillW = 360;
        int pillH = 36;
        int px = x + (w - pillW) / 2;
        int py = y + 10;
        g2d.setColor(new Color(0, 0, 0, 140));
        g2d.fillRoundRect(px + 3, py + 3, pillW, pillH, 20, 20);
        g2d.setColor(new Color(30, 50, 70));
        g2d.fillRoundRect(px, py, pillW, pillH, 20, 20);
        g2d.setColor(new Color(120, 200, 255));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(px, py, pillW, pillH, 20, 20);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        String titulo = "TORNEO POKEMON";
        FontMetrics fm = g2d.getFontMetrics();
        int tx = px + (pillW - fm.stringWidth(titulo)) / 2;
        int ty = py + 23;
        g2d.drawString(titulo, tx, ty);
    }

    private void drawMatchBox(Graphics2D g2d, int x, int y, int w, int h, String texto, Color base, Color textoColor, boolean highlight) {
        Color sombra = new Color(0, 0, 0, 120);
        g2d.setColor(sombra);
        g2d.fillRoundRect(x + 4, y + 4, w, h, 12, 12);

        GradientPaint grad = new GradientPaint(x, y, base.brighter(), x, y + h, base.darker());
        g2d.setPaint(grad);
        g2d.fillRoundRect(x, y, w, h, 12, 12);

        g2d.setColor(highlight ? Color.WHITE : base.darker().darker());
        g2d.setStroke(new BasicStroke(highlight ? 3f : 2f));
        g2d.drawRoundRect(x, y, w, h, 12, 12);

        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        if ("Por definir".equals(texto)) {
            g2d.setColor(new Color(240, 240, 240));
        } else {
            g2d.setColor(textoColor);
        }
        FontMetrics fm = g2d.getFontMetrics();
        if (!"Por definir".equals(texto)) {
            int idx = texto.indexOf(" vs ");
            if (idx > 0) {
                String p1 = texto.substring(0, idx);
                String p2 = texto.substring(idx + 4);
                String nombre1Rec = recortarTexto(p1, fm, (w - 20) / 2);
                String nombre2Rec = recortarTexto(p2, fm, (w - 20) / 2);
                texto = nombre1Rec + " vs " + nombre2Rec;
            }
        }
        int textWidth = fm.stringWidth(texto);
        int xText = x + (w - textWidth) / 2;
        int yText = y + (h + fm.getAscent()) / 2 - 3;
        g2d.drawString(texto, xText, yText);
    }

    private void drawColumnTitle(Graphics2D g2d, String texto, int x, int y, int width) {
        FontMetrics fm = g2d.getFontMetrics();
        int tx = x + (width - fm.stringWidth(texto)) / 2;
        g2d.drawString(texto, tx, y);
    }

    private void drawConnector(Graphics2D g2d, int x1, int y1, int x2, int y2, boolean decidido) {
        if (decidido) {
            g2d.setColor(new Color(80, 220, 140));
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        } else {
            g2d.setColor(new Color(200, 200, 200, 160));
            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        drawArrow(g2d, x1, y1, x2, y2);
    }

    private void dibujarGanador(Graphics2D g2d, String ganador) {
        String texto = "Ganador del torneo: " + ganador;
        int w = 360;
        int h = 40;
        int x = (BASE_WIDTH - w) / 2;
        int y = 520;
        g2d.setColor(new Color(0, 0, 0, 140));
        g2d.fillRoundRect(x + 3, y + 3, w, h, 16, 16);
        g2d.setColor(new Color(40, 160, 90));
        g2d.fillRoundRect(x, y, w, h, 16, 16);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(x, y, w, h, 16, 16);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        int tx = x + (w - fm.stringWidth(texto)) / 2;
        int ty = y + 25;
        g2d.drawString(texto, tx, ty);
    }

    private String recortarTexto(String texto, FontMetrics fm, int maxWidth) {
        if (texto == null) {
            return "";
        }
        if (fm.stringWidth(texto) <= maxWidth) {
            return texto;
        }
        String ellipsis = "...";
        int max = maxWidth - fm.stringWidth(ellipsis);
        if (max <= 0) {
            return ellipsis;
        }
        int len = texto.length();
        while (len > 0 && fm.stringWidth(texto.substring(0, len)) > max) {
            len--;
        }
        return texto.substring(0, len) + ellipsis;
    }
}

