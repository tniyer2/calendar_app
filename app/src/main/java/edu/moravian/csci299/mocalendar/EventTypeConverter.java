package edu.moravian.csci299.mocalendar;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.UUID;

public class EventTypeConverter {
    /**
     * Supports converting Date to/from Long and Quality to/from String.
     */
    @TypeConverter
    public Long fromDate(Date date) {
        if (date == null) { return null; }
        return date.getTime();
    }

    @TypeConverter
    public Date toDate(Long ms) {
        if (ms == null) { return null; }
        return new Date(ms);
    }

    @TypeConverter
    public String fromUUID(UUID id) {
        return id.toString();
    }

    @TypeConverter
    public UUID toUUID(String id) {
        return UUID.fromString(id);
    }

    @TypeConverter
    public EventType toEventType(String name){ return EventType.valueOf(name); }

    @TypeConverter
    public String fromEventType(EventType eventType){
        return eventType.name();
    }
}
