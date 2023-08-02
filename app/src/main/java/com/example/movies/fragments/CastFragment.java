package com.example.movies.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.movies.R;

public class CastFragment extends Fragment {

    // private TextView overviewTextView;

    public CastFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cast, container, false);

        // overviewTextView = rootView.findViewById(R.id.overviewTextView);

        Log.d("AboutMovieFragment.java", "The onCreateView method called");

        return rootView;

    }

    /*
    public void setOverviewTextView(String s) {
        overviewTextView.setText(s);
        Log.d("AboutMovieFragment.java", "The setOverviewTextView method called");
    }
    */
}
