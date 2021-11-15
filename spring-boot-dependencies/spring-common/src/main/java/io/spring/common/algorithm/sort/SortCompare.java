package io.spring.common.algorithm.sort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.springframework.util.StopWatch;

import java.util.Random;

/**
 * @author xiaokexiang
 * @since 2021/11/15
 */
public class SortCompare {

    public static double time(String func, Integer[] a) {
        Stopwatch stopwatch = new Stopwatch();
        if (func.equals("Insertion")) {
            new InsertSort().sort(a, false);
        } else if (func.equals("Selection")) {
            new SelectSort().sort(a, false);
        }
        return stopwatch.elapsedTime();
    }


    public static double timeRandomInput(String alg, int N, int T) {
        double total = 0.0;
        Integer[] a = new Integer[N];
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < N; j++) {
                a[j] = StdRandom.uniform(100);
            }
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {
        String alg1 = "Insertion";
        String alg2 = "Selection";
        int t = 100;
        int n = 1000;
        double t1 = timeRandomInput(alg1, n, t);
        double t2 = timeRandomInput(alg2, n, t);
        StdOut.printf("For %d random Double \n %s is ", n, alg1);
        StdOut.printf("%.1f times faster than %s\n", t2 / t1, alg2);
    }
}
