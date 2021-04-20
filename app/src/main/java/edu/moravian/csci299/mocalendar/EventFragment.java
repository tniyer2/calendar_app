package edu.moravian.csci299.mocalendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

/**
 * The fragment for a single event. It allows editing all of the details of the event, either with
 * text edit boxes (for the name and description) or popup windows (for the date, start time,
 * time and type). The event is not updated in the database until the user leaves this fragment.
 */
public class EventFragment extends Fragment implements TextWatcher, EventTypePickerFragment.Callbacks, DatePickerFragment.Callbacks{

    // fragment initialization parameters
    private static final String ARG_EVENT_ID = "event_id";

    // dialog fragment tags
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final String DIALOG_EVENT_TYPE = "DialogEventType";

    // dialog fragment codes
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_EVENT_TYPE = 2;

    // argument once loaded from database
    private Event event;

    private ImageView icon;
    private EditText descriptionView, nameView;
    private TextView dateText, startTime, endTime;


    /**
     * Use this factory method to create a new instance of this fragment that
     * show the details for the given event.
     * @param event the event to show information about
     * @return a new instance of fragment EventFragment
     */
    public static EventFragment newInstance(Event event) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, event.id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Upon creation load the data. Once the data is loaded, update the UI.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: load the event and update the UI
        if (getArguments() != null && getArguments().containsKey(ARG_EVENT_ID)) {
            UUID id = (UUID)getArguments().getSerializable(ARG_EVENT_ID);
            CalendarRepository.get().getEventById(id).observe(this, event -> {
               this.event = event;
               updateUI();
            });
        }
    }

    /**
     * Create the view from the layout, save references to all of the important
     * views within in, then hook up the listeners.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.fragment_event, container, false);


        nameView = base.findViewById(R.id.event_name);
        icon = base.findViewById(R.id.event_fragment_icon);
        descriptionView = base.findViewById(R.id.description);
        dateText = base.findViewById(R.id.date);
        startTime = base.findViewById(R.id.start_time);
        endTime = base.findViewById(R.id.end_time);





        // TODO

        icon.setOnClickListener(v -> {
            // event type picker fragment
            EventTypePickerFragment fragment = EventTypePickerFragment.newInstance(event.type);
            fragment.setTargetFragment(this, EventFragment.REQUEST_EVENT_TYPE);
            fragment.show(requireFragmentManager(), DIALOG_EVENT_TYPE);
        });

        dateText.setOnClickListener(v -> {
            DatePickerFragment fragment = DatePickerFragment.newInstance(event.startTime);
            fragment.setTargetFragment(this, EventFragment.REQUEST_DATE);
            fragment.show(requireFragmentManager(), DIALOG_DATE);

        });

        startTime.setOnClickListener(v -> {
            // time picker fragment
        });

        endTime.setOnClickListener(v -> {
            // time picker fragment
        });

        // Return the base view
        return base;
    }




    // TODO: save the event to the database at some point

    /** Updates the UI to match the event. */
    private void updateUI() {
        nameView.setText(event.name);
        icon.setImageResource(event.type.iconResourceId);
        descriptionView.setText(event.description);
        dateText.setText(DateUtils.toDateString(event.startTime));
        startTime.setText(DateUtils.toTimeString(event.startTime));
        endTime.setText(DateUtils.toTimeString(event.endTime));
    }

    // TODO: maybe some helpful functions for showing dialogs and the callback functions


    @Override
    public void onTypeSelected(EventType type){
        event.type = type;
        icon.setImageResource(event.type.iconResourceId);
    }


    @Override //
    public void onDateSelected(Date date) {
        event.startTime = date;
        dateText.setText(date.toString());
    }

    /**
     * When an EditText updates we update the corresponding Event field. Need to register this
     * object with the EditText objects with addTextChangedListener(this).
     * @param s the editable object that just updated, equal to some EditText.getText() object
     */

    @Override
    public void afterTextChanged(Editable s) {
        // TODO

    }

    /** Required to be implemented but not needed. */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    /** Required to be implemented but not needed. */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }



}
