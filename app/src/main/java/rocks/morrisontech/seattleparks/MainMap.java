package rocks.morrisontech.seattleparks;

import android.app.DialogFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toast toast = Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_LONG);
        toast.show();



    }

    protected void onStart() {
        super.onStart();

        isConnectedToInternet();

        Toast toast = Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * showNoInternetDialogFragment prompts user to check Internet settings and connection
     * called by isConnectedToInternet() when there is no connection
     */

    protected void isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnected()) {
            Toast toast = Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            // display error and offer to open settings
            Toast toast = Toast.makeText(getApplicationContext(), "not connected to network", Toast.LENGTH_LONG);
            toast.show();

            DialogFragment settingsDialog = NoInternetDialogFragment.newInstance(R.string.no_internet_dialog);
            settingsDialog.show(getFragmentManager(), "message");
        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final double seattleLat = 47.608;
        final double seattleLng = -122.335;
        LatLng seattle = new LatLng(seattleLat, seattleLng);
        mMap.addMarker(new MarkerOptions().position(seattle).title("Seattle"));


        CameraPosition defaultCameraPosition = new CameraPosition.Builder()
                .target(seattle)
                .zoom(12)
                .tilt(37)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(defaultCameraPosition));
    }
}
