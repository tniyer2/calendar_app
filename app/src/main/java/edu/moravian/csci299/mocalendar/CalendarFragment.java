package edu.moravian.csci299.mocalendar;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A fragment that displays a calendar. When a day within the calendar is clicked, a callback method
 * is called to inform the hosting activity. This calendar auto-updates its arguments with the
 * last highlighted day so that when it is rotated the same day is still highlighted.
 *
 * NOTE: this is the easiest of the core fragments to complete
 */
public class CalendarFragment extends Fragment implements CalendarView.OnDateChangeListener {
    /**
     * The callbacks that can be called by this fragment on the hosting Activity.
     */
    public interface Callbacks {

        /**
         * Called whenever a day is changed on the calendar.
         * @param date the day clicked
         */
        void onDayChanged(Date date);
    }
    // fragment initialization parameters

    private static final String ARG_DATE = "data";
    // the hosting activity callbacks

    private Callbacks callbacks;
    /**
     * Use this factory method to create a new instance of this fragment that
     * highlights today initially.
     * @return a new instance of fragment CalendarFragment.
     */
    public static CalendarFragment newInstance() {
        return newInstance(new Date());
    }

    /**
     * Use this factory method to create a new instance of this fragment that
     * highlights the given day initially.
     * @param date the date to highlight initially.
     * @return a new instance of fragment CalendarFragment.
     */
    public static CalendarFragment newInstance(Date date) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Create the view of this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        // The date to initially highlight
        Date date = DateUtils.useDateOrNow((Date)getArguments().getSerializable(ARG_DATE));
        callbacks.onDayChanged(date);

        // Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendar = base.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(this);
        calendar.setDate(date.getTime()); //milliseconds since jan 1, 1970

        // Return the base view
        return base;
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        GregorianCalendar c = new GregorianCalendar();
        c.set(year, month, dayOfMonth-1);
        callbacks.onDayChanged(c.getTime());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (Callbacks)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }
}
