package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de resultado de una búsqueda.
 * Captura cada paso (comparación) para la animación posterior.
 */
public class SearchResult {

    /** Un paso = un índice que fue comparado en esa iteración. */
    public static class Step {
        public final int   index;       // posición en el arreglo comparada
        public final int   lo;          // rango actual (binaria / interpolada)
        public final int   hi;
        public final int   mid;         // índice pivote calculado
        public final String description;
        public final boolean isHit;     // ¿fue el elemento buscado?

        public Step(int index, int lo, int hi, int mid, String desc, boolean hit) {
            this.index       = index;
            this.lo          = lo;
            this.hi          = hi;
            this.mid         = mid;
            this.description = desc;
            this.isHit       = hit;
        }
    }

    // -- Datos del resultado --
    public final int[]          array;
    public final int            target;
    public final int            foundIndex;   // -1 si no encontrado
    public final List<Step>     steps    = new ArrayList<>();
    public final long           nanoTime;     // tiempo real de ejecución
    public final int            comparisons;  // total de comparaciones

    public SearchResult(int[] array, int target,
                        int foundIndex, long nanoTime) {
        this.array      = array;
        this.target     = target;
        this.foundIndex = foundIndex;
        this.nanoTime   = nanoTime;
        this.comparisons = 0; // se asigna después via builder
    }

    // Constructor completo
    public SearchResult(int[] array, int target,
                        int foundIndex, long nanoTime, int comparisons) {
        this.array       = array;
        this.target      = target;
        this.foundIndex  = foundIndex;
        this.nanoTime    = nanoTime;
        this.comparisons = comparisons;
    }

    public boolean isFound() { return foundIndex >= 0; }

    public String complexityLabel() {
        return "O(log n)";
    }
}
