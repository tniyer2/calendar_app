package edu.moravian.csci299.mocalendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
public class EventFragment extends Fragment implements TextWatcher, EventTypePickerFragment.Callbacks, DatePickerFragment.Callbacks, TimePickerFragment.Callbacks {
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
    private TextView dateText, startTime, endTime, simpleDash;

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
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the base view.
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
        simpleDash = base.findViewById(R.id.simple_dash);

        nameView.addTextChangedListener(this);
        descriptionView.addTextChangedListener(this);

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
            TimePickerFragment fragment = TimePickerFragment.newInstance(true, event.startTime);
            fragment.setTargetFragment(this, EventFragment.REQUEST_TIME);
            fragment.show(requireFragmentManager(), DIALOG_TIME);
        });

        endTime.setOnClickListener(v -> {
            TimePickerFragment fragment = TimePickerFragment.newInstance(false, event.endTime);
            fragment.setTargetFragment(this, EventFragment.REQUEST_TIME);
            fragment.show(requireFragmentManager(), DIALOG_TIME);
        });

        // Return the base view
        return base;
    }

    /**
     * Updates the UI to match the event.
     */
    private void updateUI() {
        nameView.setText(event.name);
        icon.setImageResource(event.type.iconResourceId);
        descriptionView.setText(event.description);
        dateText.setText(DateUtils.toFullDateString(event.startTime));
        startTime.setText(DateUtils.toTimeString(event.startTime));

        //for if the event is an assignment or not. Assignments do not display an endTime unless changed
        if(event.startTime.toString().equals(event.endTime.toString())) {
            Log.d("same", DateUtils.toFullDateString(event.startTime) + " " + DateUtils.toFullDateString(event.endTime));
            endTime.setText("");
            simpleDash.setText("");

        }
        else{
            endTime.setText(DateUtils.toTimeString(event.endTime));}

    }

    /**
     * updates the database with the edited event.
     */
    @Override
    public void onStop() {
        super.onStop();
        icon.setImageResource(event.type.iconResourceId);
        CalendarRepository.get().updateItem(event);
    }

    /**
     * Sets event.type and the icon.
     * @param type the event type that was picked
     */
    @Override
    public void onTypeSelected(EventType type){
        event.type = type;
        icon.setImageResource(event.type.iconResourceId);
    }

    /**
     * Sets event's time after date is picked.
     * @param date the date that was picked
     */
    @Override
    public void onDateSelected(Date date) {
        event.startTime = DateUtils.combineDateAndTime(date, event.startTime);
        if (event.endTime != null)
        {
            event.endTime = DateUtils.combineDateAndTime(date, event.endTime);
        }
        updateUI();
    }

    /**
     * When an EditText updates we update the corresponding Event field. Need to register this
     * object with the EditText objects with addTextChangedListener(this).
     * @param s the editable object that just updated, equal to some EditText.getText() object
     */
    @Override
    public void afterTextChanged(Editable s) {
        if(s == this.nameView.getText()){
            this.event.name = s.toString();
        }
        else if(s == this.descriptionView.getText()){
            this.event.description = s.toString();
        }
    }

    /** Required to be implemented but not needed. */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    /** Required to be implemented but not needed. */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    /**
     * Sets either the startTime or endTime of the event when a time is selected.
     * @param date the date that will be used for the time.
     * @param isStartTime determines whether the time selected is a start time or not
     */
    @Override
    public void onTimeSelected(Date date, Boolean isStartTime) {
        if (isStartTime)
        {
            event.startTime = date;
            startTime.setText(DateUtils.toTimeString(event.startTime));
        }
        else
        {
            event.endTime = date;
            endTime.setText(DateUtils.toTimeString(event.endTime));
        }
    }
}
