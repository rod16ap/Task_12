package com.company;

public class Main {

    private static final int SIZE = 10000000;
    private static final int HALF = SIZE / 2;

    public float[] arrayCalculator(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        return arr;
    }

    public void oneThread() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        arrayCalculator(arr);
        System.out.println("Время однопоточного метода составило: " + (System.currentTimeMillis() - a) + "мс");
    }

    public void twoThreads() {
        float[] arr = new float[SIZE];
        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);


        new Thread() {
            public void run() {
                float[] a1 = arrayCalculator(arr1);
                System.arraycopy(a1, 0, arr1, 0, a1.length);
            }
        }.start();

        new Thread() {
            public void run() {
                float[] a2 = arrayCalculator(arr2);
                System.arraycopy(a2, 0, arr2, 0, a2.length);
            }
        }.start();

        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);
        System.out.println("Время двупоточного метода составило: " + (System.currentTimeMillis() - a) + "мс");
    }

    public static void main(String[] arr) {
        Main s = new Main();
        s.oneThread();
        s.twoThreads();
    }
}
