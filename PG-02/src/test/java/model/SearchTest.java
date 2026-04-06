package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;


class SearchTest {

    @Test
    void searchTest() {

        int[] arr = util.Utility.generateSorted(20, 50);
        int value = new Random().nextInt(50);
        int pos = Search.binarySearch(arr, value, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
        System.out.println("pos: " + pos + " value: " + value);

        for (String s: Search.steps) {
            System.out.println(s);
        }

    }

}