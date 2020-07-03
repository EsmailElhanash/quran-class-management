package app.islammedia.halaqatalquran.Database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static ArrayList<Integer> ArrayListOfIntegersFromString(String value) {
        Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String StringFromArrayListOfInteger(ArrayList<Integer> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static ArrayList<String> ArrayListOfStringsFromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String StringFromArrayListOfStrings(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static Calendar fromLong(Long l) {
        if (l==null)
            return null;

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(l));
        return c;
    }

    @TypeConverter
    public static Long fromCalendar(Calendar c) {
        if (c==null)
            return null;

        return c.getTimeInMillis();
    }
}
