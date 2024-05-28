package com.example.retry_recover;

public class Practice {
    public static void main(String[] args) {
        loop1:
        for (int j = 0; j < 2; j++) {
            loop:
            for (int i = 0; i < 6; i++) {
                if(i==2){
                    break loop1;
                }
                System.out.println(j + " " + i);
            }
        }

    }
}
