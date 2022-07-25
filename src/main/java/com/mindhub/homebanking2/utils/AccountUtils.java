package com.mindhub.homebanking2.utils;

public class AccountUtils {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getRandomAccountNumber(){
        return "VIN" +  getRandomNumber(10_000_000, 100_000_000);
    }

}
