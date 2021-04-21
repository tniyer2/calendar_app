package edu.moravian.csci299.mocalendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Date;

public class TextViewFragment extends Fragment {
    private static final String ARG_DATE = "date";
    TextView currentDateTextView;
    Date date;

    public interface Callbacks {

        /**
         * Called whenever a day is changed on the calendar.
         * @param date the day clicked
         */
        void updateDateTextView(Date date);
    }

    private Callbacks callbacks;

    public static TextViewFragment newInstance() {
        return newInstance(new Date());
    }


    public static TextViewFragment newInstance(Date date) {
        TextViewFragment fragment = new TextViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.fragment_text_view, container, false);
        // return the base view
        currentDateTextView = base.findViewById(R.id.currentDateText);
        setCurrentDateTextView(new Date());

        return base;
    }

    public void setCurrentDateTextView(Date date){
        currentDateTextView.setText(DateUtils.toDateString(date));
        callbacks.updateDateTextView(date);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (TextViewFragment.Callbacks)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

}
