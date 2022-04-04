package ru.vsu.cs.buchnev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.vsu.cs.util.ArrayUtils;
public class Main {

    public static List<SortState> sort(int[] data) {
        List<SortState> listState = new ArrayList<>();
        int size = data.length;
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }
            // обмер элементов [i] и [minIndex]
            // (можно добавить дополнительную проверку, что minIndex != i)
            int tmp = data[i];
            data[i] = data[minIndex];
            data[minIndex] = tmp;
            int[] dataKopy = kopyArr(data);
            listState.add(new SortState(dataKopy));
        }
        return listState;
    }
    public static int[] kopyArr(int[] arr){
        int[] arrRes = new int[arr.length];
        for(int i = 0; i<arr.length;i++){
            arrRes[i] = arr[i];
        }
        return arrRes;
    }
}
