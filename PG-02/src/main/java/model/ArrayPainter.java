package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.SearchResult;

/**
 * Dibuja el arreglo como barras verticales en un Canvas.
 * Colorea según el estado de cada celda en el paso actual.
 */
public class ArrayPainter {

    // ── Paleta UCR ────────────────────────────────────────────────────────────
    private static final Color COL_DEFAULT   = Color.web("#1F3868");   // azul UCR
    private static final Color COL_ACTIVE    = Color.web("#E74C3C");   // rojo – comparando ahora
    private static final Color COL_RANGE_LO  = Color.web("#4A90D9");   // azul claro – dentro del rango
    private static final Color COL_FOUND     = Color.web("#1A8C7B");   // verde – encontrado
    private static final Color COL_VISITED   = Color.web("#8896A5");   // gris – ya visitado
    private static final Color COL_OUT       = Color.web("#D0D6E0");   // muy claro – fuera del rango
    private static final Color COL_TEXT      = Color.WHITE;
    private static final Color COL_IDX       = Color.web("#8896A5");
    private static final Color COL_BG        = Color.web("#F4F6FA");
    private static final Color COL_POINTER   = Color.web("#E8A020");   // ámbar UCR – puntero

    /** Estado de pintura para una celda. */
    public enum CellState {
        DEFAULT, ACTIVE, IN_RANGE, FOUND, VISITED, OUT_OF_RANGE
    }

    /**
     * Pinta el arreglo completo en el canvas.
     *
     * @param step      paso actual (null = estado inicial)
     * @param visited   bitmask de índices ya visitados
     * @param foundIdx  índice encontrado (-1 = no encontrado todavía)
     */
    public void paint(Canvas canvas, int[] array, SearchResult.Step step,
                      boolean[] visited, int foundIdx) {
        if (array == null || array.length == 0) return;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        double W = canvas.getWidth();
        double H = canvas.getHeight();

        // Fondo
        gc.setFill(COL_BG);
        gc.fillRect(0, 0, W, H);

        int n = array.length;
        double padding = 10;
        double totalW  = W - padding * 2;
        double barW    = Math.max(4, totalW / n - 2);
        double gapW    = Math.max(1, (totalW - barW * n) / Math.max(1, n - 1));
        double maxVal  = 0;
        for (int v : array) maxVal = Math.max(maxVal, v);

        double areaH     = H - 60;   // dejar espacio para índices y valores
        double barMaxH   = areaH - 20;

        // Determinar rango activo
        int lo = (step != null) ? step.lo : 0;
        int hi = (step != null) ? step.hi : n - 1;
        int activeIdx = (step != null) ? step.index : -1;

        for (int i = 0; i < n; i++) {
            double x   = padding + i * (barW + gapW);
            double bH  = (maxVal > 0) ? barMaxH * array[i] / maxVal : barMaxH / 2;
            double y   = areaH - bH;

            // Determinar color
            Color fill;
            if (foundIdx == i) {
                fill = COL_FOUND;
            } else if (i == activeIdx) {
                fill = COL_ACTIVE;
            } else if (visited != null && visited[i]) {
                fill = COL_VISITED;
            } else if (step != null && i >= lo && i <= hi) {
                fill = COL_RANGE_LO;
            } else if (step != null) {
                fill = COL_OUT;
            } else {
                fill = COL_DEFAULT;
            }

            // Sombra suave
            gc.setFill(Color.color(0, 0, 0, 0.08));
            gc.fillRoundRect(x + 2, y + 2, barW, bH, 3, 3);

            // Barra
            gc.setFill(fill);
            gc.fillRoundRect(x, y, barW, bH, 3, 3);

            // Valor dentro de la barra (si cabe)
            if (barW >= 18 && bH >= 16) {
                gc.setFill(COL_TEXT);
                gc.setFont(Font.font("Calibri", FontWeight.BOLD,
                           Math.min(11, barW * 0.55)));
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText(String.valueOf(array[i]), x + barW / 2, y + bH - 5);
            }

            // Índice debajo
            if (barW >= 12) {
                gc.setFill(COL_IDX);
                gc.setFont(Font.font("Calibri", 9));
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText(String.valueOf(i), x + barW / 2, areaH + 13);
            }

            // Puntero ámbar para elemento activo
            if (i == activeIdx) {
                gc.setFill(COL_POINTER);
                double px = x + barW / 2;
                gc.fillPolygon(
                    new double[]{px - 5, px + 5, px},
                    new double[]{areaH + 18, areaH + 18, areaH + 26},
                    3
                );
            }

            // Corona verde para encontrado
            if (foundIdx == i) {
                gc.setFill(Color.web("#1A8C7B", 0.3));
                gc.fillRoundRect(x - 2, y - 2, barW + 4, bH + 4, 5, 5);
                gc.setStroke(COL_FOUND);
                gc.setLineWidth(2.5);
                gc.strokeRoundRect(x - 2, y - 2, barW + 4, bH + 4, 5, 5);
            }
        }

        // Leyenda de rango [lo, hi] con corchetes
        if (step != null && lo >= 0 && hi < n && lo <= hi) {
            double xLo = padding + lo * (barW + gapW) - 2;
            double xHi = padding + hi * (barW + gapW) + barW + 2;
            gc.setStroke(COL_RANGE_LO);
            gc.setLineWidth(1.5);
            // Corchete izquierdo
            gc.strokeLine(xLo, areaH - barMaxH - 4, xLo, areaH);
            gc.strokeLine(xLo, areaH - barMaxH - 4, xLo + 6, areaH - barMaxH - 4);
            // Corchete derecho
            gc.strokeLine(xHi, areaH - barMaxH - 4, xHi, areaH);
            gc.strokeLine(xHi, areaH - barMaxH - 4, xHi - 6, areaH - barMaxH - 4);
        }
    }

    /** Pinta el estado "vacío" con texto guía. */
    public void paintEmpty(Canvas canvas, String message) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double W = canvas.getWidth(), H = canvas.getHeight();
        gc.setFill(COL_BG);
        gc.fillRect(0, 0, W, H);
        gc.setFill(Color.web("#8896A5"));
        gc.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(message, W / 2, H / 2);
    }
}
