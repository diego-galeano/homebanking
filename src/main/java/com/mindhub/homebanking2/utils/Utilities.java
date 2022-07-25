package com.mindhub.homebanking2.utils;


public final class Utilities {

        public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


}
