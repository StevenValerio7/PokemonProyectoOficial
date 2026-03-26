package gui;

import javax.swing.*;
import java.awt.*;
import estructuras.*;

public class BracketPanel extends JPanel {

    private ArbolTorneo torneo;

    public BracketPanel(ArbolTorneo torneo) {
        this.torneo = torneo;
        setOpaque(false);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        Color colorEquipos = Color.BLACK;
        Color colorResultados = new Color(46, 139, 87);
        Color colorRondas = new Color(0, 51, 102);
        Color colorGranFinal = new Color(255, 215, 0);

        dibujarBracket(g2d, torneo.getEquiposJugador(), true, colorEquipos, colorResultados, colorRondas);

        dibujarBracket(g2d, torneo.getEquiposNPC(), false, colorEquipos, colorResultados, colorRondas);

        if (torneo.getRondaActual() >= 4) {
            int xGranFinal = 525;
            int yGranFinal = 250;

            String ganadorJugador = getGanadorFinal(true);
            String ganadorNPC = getGanadorFinal(false);
            

            g2d.setColor(Color.WHITE);
            g2d.fillRect(xGranFinal, yGranFinal, 150, 40);

            g2d.setColor(colorGranFinal);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(xGranFinal, yGranFinal, 150, 40);

            String texto = ganadorJugador + " vs " + ganadorNPC;
            g2d.setColor(colorEquipos);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(texto);
            int xText = xGranFinal + (150 - textWidth) / 2;
            g2d.drawString(texto, xText, yGranFinal + 28);

            g2d.setColor(colorRondas);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("Gran Final", xGranFinal + 45, yGranFinal - 10);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawLine(450 + 150, 150 + 20, xGranFinal, yGranFinal + 20);
            g2d.drawLine(750, 150 + 20, xGranFinal + 150, yGranFinal + 20);
            
            if (torneo.getRondaActual() >= 4) {
    String ganadorFinal = torneo.getGanadorFinal();
    if (ganadorFinal != null) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Ganador: " + ganadorFinal, xGranFinal + 20, yGranFinal + 60);
    }
}

        }
    }

    private String getGanadorCuartos(int partido, ListaEquipos equipos, boolean esBracketJugador) {
        int idx1 = partido * 2;
        int idx2 = partido * 2 + 1;
        String nombre1 = equipos.getNombre(idx1);
        String nombre2 = equipos.getNombre(idx2);
        if (esBracketJugador && idx1 == 0) {
            nombre1 = "Jugador";
            if (torneo.getRondaActual() > 1 && partido == 0) {
                return "Jugador";
            }
        }

        if (esBracketJugador) {
            if (partido == 0) {
                return nombre2; // Equipo 2
            }
            if (partido == 1) {
                return nombre1; // Equipo 3
            }
            if (partido == 2) {
                return nombre2; // Equipo 6 
            }
            return nombre1; // Equipo 7 
        }

        if (partido == 0) {
            return nombre2; // Equipo 10
        }
        if (partido == 1) {
            return nombre1; // Equipo 11
        }
        if (partido == 2) {
            return nombre1; // Equipo 13
        }
        return nombre2; // Equipo 16
    }

    private String getGanadorSemifinal(int partido, boolean esBracketJugador) {
        ListaEquipos equipos = esBracketJugador ? torneo.getEquiposJugador() : torneo.getEquiposNPC();
        String ganador1;
        String ganador2;
        if (esBracketJugador) {
            if (partido == 0) {
                ganador1 = getGanadorCuartos(0, equipos, esBracketJugador);
                ganador2 = equipos.getNombre(1);
            } else {
                ganador1 = getGanadorCuartos(1, equipos, esBracketJugador);
                ganador2 = equipos.getNombre(3);
            }
        } else {
            if (partido == 0) {
                ganador1 = getGanadorCuartos(0, equipos, esBracketJugador);
                ganador2 = getGanadorCuartos(1, equipos, esBracketJugador);
            } else {
                ganador1 = getGanadorCuartos(2, equipos, esBracketJugador);
                ganador2 = getGanadorCuartos(3, equipos, esBracketJugador);
            }
        }

        if (partido == 0) {
            return esBracketJugador ? ganador1 : ganador2;
        }
        return esBracketJugador ? ganador2 : ganador1;
    }

    private String getGanadorFinal(boolean esBracketJugador) {
        String ganadorSemi1 = getGanadorSemifinal(0, esBracketJugador);
        String ganadorSemi2 = getGanadorSemifinal(1, esBracketJugador);

        return esBracketJugador ? ganadorSemi1 : ganadorSemi2;
    }

    private void dibujarBracket(Graphics2D g2d, ListaEquipos equipos, boolean esBracketJugador, Color colorEquipos, Color colorResultados, Color colorRondas) {
        int rondaActual = torneo.getRondaActual();

        int panelWidth = 150;
        int panelHeight = 40;
        int yCuartos = 50;
        int ySpacingCuartos = 100;
        int ySemis = 100;
        int ySpacingSemis = 200;
        int yFinal = 150;

        int xCuartos = esBracketJugador ? 50 : 1200 - 50 - panelWidth;
        int xSemis = esBracketJugador ? 250 : 1200 - 250 - panelWidth;
        int xFinal = esBracketJugador ? 450 : 1200 - 450 - panelWidth;

        g2d.setColor(colorRondas);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Cuartos de Final", xCuartos, yCuartos - 20);
        if (rondaActual > 1) {
            g2d.drawString("Semifinales", xSemis, ySemis - 20);
        }
        if (rondaActual > 2) {
            g2d.drawString("Final", xFinal, yFinal - 20);
        }

        for (int i = 0; i < 4; i++) {
            int y = yCuartos + i * ySpacingCuartos;
            int idx1 = i * 2;
            int idx2 = i * 2 + 1;

            String nombre1 = equipos.getNombre(idx1);
            String nombre2 = equipos.getNombre(idx2);
            if (esBracketJugador && idx1 == 0) {
                nombre1 = "Jugador";
            }

            g2d.setColor(Color.WHITE);
            g2d.fillRect(xCuartos, y, panelWidth, panelHeight);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(xCuartos, y, panelWidth, panelHeight);

            String texto = nombre1 + " vs " + nombre2;
            g2d.setColor(colorEquipos);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(texto);
            int xText = xCuartos + (panelWidth - textWidth) / 2;
            g2d.drawString(texto, xText, y + 28);

            if (i % 2 == 0 && rondaActual > 1) {
                int yNext = ySemis + (i / 2) * ySpacingSemis;
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
                if (esBracketJugador) {
                    g2d.drawLine(xCuartos + panelWidth, y + panelHeight / 2, xSemis, y + panelHeight / 2);
                    g2d.drawLine(xSemis, y + panelHeight / 2, xSemis, yNext + panelHeight / 2);
                } else {
                    g2d.drawLine(xCuartos, y + panelHeight / 2, xSemis + panelWidth, y + panelHeight / 2);
                    g2d.drawLine(xSemis + panelWidth, y + panelHeight / 2, xSemis + panelWidth, yNext + panelHeight / 2);
                }
            }
        }

        if (rondaActual > 1) {
            for (int i = 0; i < 2; i++) {
                int y = ySemis + i * ySpacingSemis;
                String texto;
                if (esBracketJugador) {
                    if (i == 0) {
                        String ganadorCuartos0 = getGanadorCuartos(0, equipos, esBracketJugador);
                        String equipo2 = equipos.getNombre(1);
                        texto = ganadorCuartos0 + " vs " + equipo2;
                    } else {
                        String equipo3 = equipos.getNombre(2);
                        String equipo4 = equipos.getNombre(3);
                        texto = equipo3 + " vs " + equipo4;
                    }
                } else {
                    if (i == 0) {
                        String ganadorCuartos0 = getGanadorCuartos(0, equipos, esBracketJugador);
                        String ganadorCuartos1 = getGanadorCuartos(1, equipos, esBracketJugador);
                        texto = ganadorCuartos0 + " vs " + ganadorCuartos1;
                    } else {
                        String ganadorCuartos2 = getGanadorCuartos(2, equipos, esBracketJugador);
                        String ganadorCuartos3 = getGanadorCuartos(3, equipos, esBracketJugador);
                        texto = ganadorCuartos2 + " vs " + ganadorCuartos3;
                    }
                }

                g2d.setColor(Color.WHITE);
                g2d.fillRect(xSemis, y, panelWidth, panelHeight);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(xSemis, y, panelWidth, panelHeight);

                g2d.setColor(colorEquipos);
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(texto);
                int xText = xSemis + (panelWidth - textWidth) / 2;
                g2d.drawString(texto, xText, y + 28);

                if (i == 0 && rondaActual > 2) {
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(1));
                    if (esBracketJugador) {
                        g2d.drawLine(xSemis + panelWidth, y + panelHeight / 2, xFinal, y + panelHeight / 2);
                        g2d.drawLine(xFinal, y + panelHeight / 2, xFinal, yFinal + panelHeight / 2);
                    } else {
                        g2d.drawLine(xSemis, y + panelHeight / 2, xFinal + panelWidth, y + panelHeight / 2);
                        g2d.drawLine(xFinal + panelWidth, y + panelHeight / 2, xFinal + panelWidth, yFinal + panelHeight / 2);
                    }
                }
            }
        }

        if (rondaActual > 2) {
            String ganadorSemi1 = getGanadorSemifinal(0, esBracketJugador);
            String ganadorSemi2 = getGanadorSemifinal(1, esBracketJugador);
            String texto = ganadorSemi1 + " vs " + ganadorSemi2;

            g2d.setColor(Color.WHITE);
            g2d.fillRect(xFinal, yFinal, panelWidth, panelHeight);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(xFinal, yFinal, panelWidth, panelHeight);

            g2d.setColor(colorEquipos);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(texto);
            int xText = xFinal + (panelWidth - textWidth) / 2;
            g2d.drawString(texto, xText, yFinal + 28);
        }

        if (esBracketJugador && rondaActual <= 4) {
            int xIndicador = 0;
            int yIndicador = 0;
            if (rondaActual == 1) {
                xIndicador = xCuartos + panelWidth + 10;
                yIndicador = yCuartos + (torneo.getCombateEnRonda() - 1) * ySpacingCuartos + 28;
            } else if (rondaActual == 2) {
                xIndicador = xSemis + panelWidth + 10;
                yIndicador = ySemis + (torneo.getCombateEnRonda() - 1) * ySpacingSemis + 28;
            } else if (rondaActual == 3) {
                xIndicador = xFinal + panelWidth + 10;
                yIndicador = yFinal + 28;
            } else if (rondaActual == 4) {
                xIndicador = 525 + panelWidth + 10;
                yIndicador = 250 + 28;
            }
            g2d.setColor(colorResultados);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("-> Combate Actual", xIndicador, yIndicador);
        }
    }
}
