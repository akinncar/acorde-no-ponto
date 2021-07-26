package br.com.acordenoponto.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import br.com.acordenoponto.R;
import br.com.acordenoponto.adapter.AdapterDestinations;
import br.com.acordenoponto.dto.Destination;
import br.com.acordenoponto.sqlite.DestinationDbHelper;
import br.com.acordenoponto.sqlite.Destinations;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<Destination> destinations;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Destinations List
        DestinationDbHelper dbHelper = new DestinationDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                Destinations.DestinationEntry.COLUMN_NAME_TITLE,
                Destinations.DestinationEntry.COLUMN_NAME_LAT,
                Destinations.DestinationEntry.COLUMN_NAME_LONG
        };

        Cursor cursor = db.query(
            Destinations.DestinationEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        );

        List destinations = new ArrayList<Destination>();
        this.destinations = destinations;
        while(cursor.moveToNext()) {
            Destination destination = new Destination();

            destination.setId(cursor.getLong(
                    cursor.getColumnIndexOrThrow(Destinations.DestinationEntry._ID)));
            destination.setTitle(cursor.getString(
                    cursor.getColumnIndexOrThrow(Destinations.DestinationEntry.COLUMN_NAME_TITLE)));
            destination.setLatitude(cursor.getLong(
                    cursor.getColumnIndexOrThrow(Destinations.DestinationEntry.COLUMN_NAME_LAT)));
            destination.setLongitude(cursor.getLong(
                    cursor.getColumnIndexOrThrow(Destinations.DestinationEntry.COLUMN_NAME_LONG)));

            destinations.add(destination);
        }
        cursor.close();

//        for (int i = 0; i < destinations.size(); i++) {
//            Destination destination = (Destination) destinations.get(i);
//
//            Log.d("listinha", destination.getTitle());
//        }

        if(destinations.size() == 0) {
            final TextView textView = root.findViewById(R.id.text_home);
            homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                textView.setText(s);
                }
            });
        }

        ListView destinationListView = root.findViewById(R.id.list);

        AdapterDestinations adapter = new AdapterDestinations(destinations, this.getActivity());

        destinationListView.setAdapter(adapter);

        return root;
    }
}