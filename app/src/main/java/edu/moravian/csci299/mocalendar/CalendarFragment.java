package edu.moravian.csci299.mocalendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

/**
 * A fragment that displays a calendar. When a day within the calendar is clicked, a callback method
 * is called to inform the hosting activity. This calendar auto-updates its arguments with the
 * last highlighted day so that when it is rotated the same day is still highlighted.
 *
 * NOTE: this is the easiest of the core fragments to complete
 */
public class CalendarFragment extends Fragment {
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
        // The date to initially highlight
        assert getArguments() != null;
        Date date = DateUtils.useDateOrNow((Date) getArguments().getSerializable(ARG_DATE));
        callbacks.onDayChanged(date);

        // Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.fragment_calendar, container, false);

        // TODO: Setup the calendar

        // Return the base view
        return base;
    }
    
    // TODO: get (and clear) the callbacks object as appropriate
}
