package com.xueyiche.zjyk.xueyiche.newdriverschool.coach.myhttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;

public class GsonBuilderUtil {

    public static Gson create() {
        GsonBuilder gb = new GsonBuilder();
       // gb.registerTypeAdapter(String.class, new StringConverter());
        gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
        gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
        Gson gson = gb.create();
        return gson;
    }
}


