package kul.pl.biblioteka.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static boolean isNullOrEmpty(String str){
        return str == null || str.isEmpty();
    }
    public static String getDefaultDateFormat(Date date){
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
      return simpleDateFormat.format(date);
    }
}
