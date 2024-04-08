package com.seu.tms_mobile_banking.util;

import jakarta.persistence.GeneratedValue;

import java.util.Random;

public class RandomUtil {
    public static String generateNineDigits(){
        Random random = new Random();
        int min = 100000000; // Smallest 9-digit number
        int max = 999999999; // Largest 9-digit number
        int randomNum = random.nextInt((max - min) + 1) + min;
        return String.valueOf(randomNum);
    }
}
