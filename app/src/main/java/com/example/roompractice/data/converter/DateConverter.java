package com.example.roompractice.data.converter;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter  {

    //used when reading from the database
    @TypeConverter
    public static Date toDate (Long timestamp) {
        return new Date(timestamp);
    }

    //used when inserting/writing to room
    @TypeConverter
    public static Long fromDate (Date date) {
        return date == null ? null : date.getTime();
    }

}
