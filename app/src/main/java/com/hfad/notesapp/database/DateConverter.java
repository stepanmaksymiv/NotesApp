package com.hfad.notesapp.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long time){
        if (time == null){
            return null;
        }else {
            return new Date(time);
        }
//        return time == null ? null : new Date(time);
    }

    @TypeConverter
    public static Long toTime(Date date){
        return date == null ? null : date.getTime();
    }
}
