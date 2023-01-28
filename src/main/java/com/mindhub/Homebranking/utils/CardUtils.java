package com.mindhub.Homebranking.utils;

public class CardUtils {

    public static int getRandomNumber ( int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }


    public static int getNumberCVV(){
        int cardCVV = getRandomNumber(100, 999);
        return cardCVV;
    }


    public static String getCardNumber () {
        String randomCardNumber = getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999) + "-" + getRandomNumber(1000, 9999);
        return randomCardNumber;
    }
}
