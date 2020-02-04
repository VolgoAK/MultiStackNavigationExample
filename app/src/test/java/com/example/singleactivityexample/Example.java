package com.example.singleactivityexample;

public class Example {
    public static void main(String[] args) {
        System.out.println("Hello");
        int time = 0;
        while (true) {
            time++;
            System.out.println("Time" + time);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
