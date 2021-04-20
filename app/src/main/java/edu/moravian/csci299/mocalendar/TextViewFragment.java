package edu.moravian.csci299.mocalendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Date;

public class TextViewFragment extends Fragment {
    TextView currentDateTextView;

    public TextViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.fragment_text_view, container, false);

        // TODO

        // return the base view
        currentDateTextView = base.findViewById(R.id.currentDateText);
        setCurrentDateTextView(new Date());

        return base;
    }

    public void setCurrentDateTextView(Date date){
        currentDateTextView.setText(date.toString());
    }

}
