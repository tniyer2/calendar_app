package edu.moravian.csci299.mocalendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import java.util.Date;

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

public class MainActivity extends AppCompatActivity implements CalendarFragment.Callbacks, ListFragment.Callbacks, TextViewFragment.Callbacks{
//    TextView currentDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment parent = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (parent == null) {
            // If no fragment is being currently displayed add one via a transaction
            CalendarFragment fragment = CalendarFragment.newInstance();
            ListFragment listFragment = ListFragment.newInstance();

            TextViewFragment textViewFragment = TextViewFragment.newInstance();


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .add(R.id.fragment_container, textViewFragment)
                    .add(R.id.fragment_container, listFragment)
                    .commit();
        }
    }

    public void onDayChanged(Date date) {
        ListFragment fragment  = (ListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        //TextViewFragment textViewFragment = TextViewFragment.newInstance(date);
        fragment.setDay(date);

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, )
    }


    @Override
    public void showEventById(Event event) {

        EventFragment eventFragment = EventFragment.newInstance(event);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, eventFragment)
                .addToBackStack(null)
                .commit();
    }



    @Override
    public void updateDateTextView(Date date) {
//        TextViewFragment textViewFragment = TextViewFragment.newInstance(date);
////        textViewFragment.setCurrentDateTextView(date);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, textViewFragment)
//                .commit();
    }

}
