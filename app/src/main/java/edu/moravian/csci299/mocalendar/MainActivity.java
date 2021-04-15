package edu.moravian.csci299.mocalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import java.util.Date;
import java.util.UUID;

/**
 * The main (and only) activity for the application that hosts all of the fragments.
 *
 * It starts out with a calendar and list fragment (if vertical then they are above/below each
 * other, if horizontal then they are left/right of each other). When a day is clicked in the
 * calendar, the list shows all events for that day.
 *
 * When an event is being edited/viewed (because it was clicked in the list or a new event is being
 * added) then the fragments are replaced with an event fragment which shows the details for a
 * specific event and allows editing.
 *
 * NOTE: This Activity is the bare-bones, empty, Activity. Work will be definitely needed in
 * onCreate() along with implementing some callbacks.
 */
public class MainActivity extends AppCompatActivity implements CalendarFragment.Callbacks, ListFragment.Callbacks {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FragmentManager fm = getSupportFragmentManager();
        Fragment parent = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (parent == null) {
            // If no fragment is being currently displayed add one via a transaction
            CalendarFragment fragment = CalendarFragment.newInstance();
            ListFragment listFragment = ListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .add(R.id.fragment_container, listFragment)
                    .commit();

        }
    }

    @Override
    public void onDayChanged(Date date) {
        Log.d("MainActivity", "date: " + date.toString());
    }

    @Override
    public void getEventById(UUID uuid) {
        Log.d("MainActivity", "uuid: " + uuid);
    }
}
