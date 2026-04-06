package model;

import java.util.ArrayList;
import java.util.List;

public class Search {


    public static List<String> steps = new ArrayList<>();

    /**
     * int value = 10:
     * int pos = binarySearch(sortedArray, value, 0, sortedArray.length - 1);
     * @param sortedArray
     * @param value
     * @param low
     * @param high
     * @return
     */

    public static int binarySearch(int[] sortedArray, int value, int low, int high){

        if (low>high){
            steps.add("Valor no encontrado: ");
            return -1;
        }
        int mid = low + (high - low) / 2;

        steps.add("Rango ["+ low + ", " + high + "], -->mid= " + mid
                +"(sortedArray[mid] =" + sortedArray[mid] + "]");

        if (value == sortedArray[mid]){
            steps.add("Valor encontrado en la posición: " + mid);
            return mid;

        } else if (value < sortedArray[mid]){
            return binarySearch(sortedArray, value, low, mid - 1);
        } else {
            return binarySearch(sortedArray, value, mid + 1, high);
        }
    }
}
