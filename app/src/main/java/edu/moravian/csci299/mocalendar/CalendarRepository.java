package edu.moravian.csci299.mocalendar;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CalendarRepository {
    private final CalendarDao calendarDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private CalendarRepository(Context context) {
        // Internal singleton fields of the repository
        MyDatabase database = Room.databaseBuilder(
            context.getApplicationContext(),
            MyDatabase.class,
            "calendar_database").build();
        calendarDao = database.calendarDAO();
    }

    // The public methods that simply call the DAO methods
    public LiveData<List<Event>> getAllEvents() {
        return calendarDao.getAllEvents();
    }
    public LiveData<Event> getEventById(UUID id) {
        return calendarDao.getEventById(id);
    }
    public LiveData<List<Event>> getEventsBetween(Date start, Date end) {
        return calendarDao.getEventsBetween(start, end);
    }
    public LiveData<List<Event>> getEventsOnDay(Date date){
        return calendarDao.getEventsOnDay(date);
    }

    // Insert and update methods
    public void addItem(Event item) {
        executor.execute(() -> {
            calendarDao.addEvent(item);
        });
    }

    public void removeItem(Event item) {
        executor.execute(() -> {
            calendarDao.removeEvent(item);
        });
    }

    public void updateItem(Event item) {
        executor.execute(() -> {
            calendarDao.updateEvent(item);
        });
    }

    // The single instance of the repository
    private static CalendarRepository INSTANCE;
    public static CalendarRepository get() {
        if (INSTANCE == null) { throw new IllegalStateException("CalendarRepository must be initialized"); }
        return INSTANCE;
    }
    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CalendarRepository(context);
        }
    }
}
