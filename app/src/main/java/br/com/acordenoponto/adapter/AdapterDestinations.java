package br.com.acordenoponto.adapter;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import br.com.acordenoponto.R;
import br.com.acordenoponto.dto.Destination;
import br.com.acordenoponto.sqlite.DestinationDbHelper;
import br.com.acordenoponto.sqlite.Destinations;

public class AdapterDestinations extends BaseAdapter {

    private final List<Destination> destinations;
    private final Activity act;

    public AdapterDestinations(List<Destination> destinations, Activity act) {
        this.destinations = destinations;
        this.act = act;
    }

    @Override public int getCount() { return destinations.size(); }

    @Override public Object getItem(int position) {
        return destinations.get(position);
    }

    @Override public long getItemId(int position) { return 0; }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.fragment_home, parent, false);
        Destination destination = destinations.get(position);

        TextView title = (TextView) view.findViewById(R.id.list_item_title);
        TextView descricao = (TextView) view.findViewById(R.id.list_item_lat_long);
        TextView btnStart = (TextView) view.findViewById(R.id.list_item_button_start);
        TextView btnDelete = (TextView) view.findViewById(R.id.list_item_button_delete);

        String latitude = String.valueOf(destination.getLatitude());
        String longitude = String.valueOf(destination.getLongitude());

        title.setText(destination.getTitle());
        descricao.setText(latitude + ", " + longitude);
        btnStart.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.VISIBLE);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Destination destination = destinations.get(position);

                DestinationDbHelper dbHelper = new DestinationDbHelper(act);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String selection = Destinations.DestinationEntry._ID + " = ?";
                String[] selectionArgs = { String.valueOf(destination.getId()) };
                db.delete(Destinations.DestinationEntry.TABLE_NAME, selection, selectionArgs);

//                YourAdapterName.notifyDataSetChanged();
            }
        });

        return view;
    }
}
