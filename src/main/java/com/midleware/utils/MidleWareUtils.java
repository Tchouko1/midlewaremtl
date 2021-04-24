package com.midleware.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MidleWareUtils {
    public static String SEARCH_ADDRESS = "search-address";
    public static String SEARCH_POLLUANT = "search-polluant";
    public static String SEARCH_CALCUL = "search-calcul";


    public static Long formatDateToLong (String pDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = sdf.parse(pDate);
        long millis = date.getTime();

        return millis;

    }
}
