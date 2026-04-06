package util;

import java.text.DecimalFormat;
import java.util.Random;

public class Utility {

    public static String format(long value) {
        return new DecimalFormat("#,###,###").format(value);
    }

    public static int[] generateSorted(int size, int maxValue) {

        int [] arr = new Random().ints(size,1,maxValue+1)
                .limit(size).sorted().toArray();

        return arr;
    }
}
