package kul.pl.biblioteka.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPicker {
    //Fisher–Yates shuffle
    public static <E> List<E> pick(List<E> list, int n, Random r) {
        int length = list.size();

        if (length < n) return null;

        for (int i = length - 1; i >= length - n; --i)
        {
            Collections.swap(list, i , r.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    public static <E> List<E> pick(List<E> list, int n) {
        return pick(list, n, ThreadLocalRandom.current());
    }
}
