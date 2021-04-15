package edu.moravian.csci299.mocalendar;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Event.class}, version = 1)
@TypeConverters(EventTypeConverter.class)
public abstract class MyDatabase extends RoomDatabase{
    public abstract CalendarDao calendarDAO();
}
