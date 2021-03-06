package task2.task2;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String EXTRA_LONGITUDE = "task2.task2.longitude";
    public static final String EXTRA_LATITUDE = "task2.task2.latitude";

    private final int ZOOM_LEVEL = 10;
    private double mLatitude = 0;
    private double mLongitude = 0;

    public static Intent newIntent(Context packageContext, double latitude, double longitude){
        Intent intent = new Intent(packageContext, MapsActivity.class);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        return intent;
    }

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mLatitude = extras.getDouble(EXTRA_LATITUDE);
            mLongitude = extras.getDouble(EXTRA_LONGITUDE);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pos = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(new MarkerOptions().position(pos).title(getString(R.string.location_title)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));


        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, ZOOM_LEVEL);
        mMap.animateCamera(update);

    }
}

