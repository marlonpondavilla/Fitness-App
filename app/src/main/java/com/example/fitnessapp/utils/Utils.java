package com.example.fitnessapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public String setDate(long currentTimeMillis) {

        // Create a Date object from current time in milliseconds
        Date date = new Date(currentTimeMillis);

        // Define the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Format the date into the specified format and return as a String
        return dateFormat.format(date);
    }
}
