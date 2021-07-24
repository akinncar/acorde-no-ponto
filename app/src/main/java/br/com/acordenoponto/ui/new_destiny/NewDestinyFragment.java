package br.com.acordenoponto.ui.new_destiny;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.location.LocationManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import br.com.acordenoponto.R;

public class NewDestinyFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private boolean showUserLocation = false;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng selectedLatLng;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_destiny, container, false);

        final Button buttonAddDestiny = root.findViewById(R.id.addDestiny);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getContext());

        mapView = root.findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng (-23.5489, -46.6388)));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng));
                selectedLatLng = latLng;

                Button btnAddDestiny = getActivity().findViewById(R.id.addDestiny);

                if(selectedLatLng != null) {
                    btnAddDestiny.setVisibility(View.VISIBLE);
                } else {
                    btnAddDestiny.setVisibility(View.INVISIBLE);
                }
            }
        });

        Log.e("hellooo0", String.valueOf(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)));

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e("hellooo1", "hello 1");

            if (this.googleMap != null) {
                this.googleMap.setMyLocationEnabled(this.showUserLocation);

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener((Activity) this.getContext(), new OnSuccessListener<Location>() {
                            public void onSuccess(Location location) {
                                LatLng latLng = (new LatLng(location.getLatitude(), location.getLongitude()));
                                Log.e("hellooo2", "hello 2");
                                Log.e("latLng", latLng.toString());
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                if (location != null) {
                                    // Logic to handle location object
                                }
                            }
                        });

            }
        } else {
            Log.e("helloelse", "elkse");
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    99
            );
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
}