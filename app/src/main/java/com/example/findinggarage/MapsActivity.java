package com.example.findinggarage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.findinggarage.model.Locations;
import com.example.findinggarage.model.ModelCar;
import com.example.findinggarage.model.ModelMoto;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class MapsActivity<reff, builder, cu, bounds> extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private Button nava, ad;
    private Button moto, car;


    ModelCar c;
    ModelMoto m;
    DatabaseReference reff;
    Button buttonNormal;
    Button buttonSatellite;
    Button buttonHybrid;

    ArrayList<ModelCar> cars = new ArrayList<ModelCar>();
    ArrayList<ModelMoto> motos = new ArrayList<ModelMoto>();
    private double Latitude;
    private double Longtitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buttonNormal = (Button) findViewById(R.id.buttonNormal);
        buttonSatellite = (Button) findViewById(R.id.buttonSatellite);
        buttonHybrid = (Button) findViewById(R.id.buttonHybrid);
        nava = (Button) findViewById(R.id.buttonNavigate);
        moto = (Button) findViewById(R.id.buttonMoto);
        car = (Button) findViewById(R.id.buttonCar);
        ad = (Button) findViewById(R.id.butAD);
        reff = FirebaseDatabase.getInstance().getReference("location");

    }

    public void getDataM(){
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child("/Moto").getChildren()) {
                    m = ds.getValue(ModelMoto.class);
                    motos.add(m);

                }

                try {
                    mMap.clear();
                    for (int i = 0; i < motos.size(); i++) {

                        Latitude = Double.parseDouble(String.valueOf(motos.get(i).getLati()));
                        Longtitude = Double.parseDouble(String.valueOf(motos.get(i).getLongti()));

                        String Name = motos.get(i).getName();
                        String cont = motos.get(i).getContact();

                        LatLng latLng = new LatLng(Latitude, Longtitude);
                        mMap.addMarker(new MarkerOptions().position(latLng)
                                .title(Name).snippet(cont).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

                    }
                    Toast.makeText(MapsActivity.this, "Show Moto Garage", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(MapsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getDataC() {

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child("/Car").getChildren()) {
                    c = ds.getValue(ModelCar.class);
                    cars.add(c);
                }
                try {
                    mMap.clear();
                    for (int i = 0; i < cars.size(); i++) {

                        Latitude = Double.parseDouble(String.valueOf(cars.get(i).getLati()));
                        Longtitude = Double.parseDouble(String.valueOf(cars.get(i).getLongti()));

                        String Name = cars.get(i).getName();
                        String cont = cars.get(i).getContact();


                        LatLng sydney = new LatLng(Latitude, Longtitude);
                        mMap.addMarker(new MarkerOptions().position(sydney)
                                .title(Name).snippet(cont).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
                    }
                    Toast.makeText(MapsActivity.this, "Show Car Garage", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(MapsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this, "Error Connect FireBase", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void openGoogleMap(LatLng src, LatLng dest) {
        String url = "http://maps.google.com/maps?saddr=" + src.latitude + "," + src.longitude + "&daddr=" + dest.latitude + "," + dest.longitude + "&mode=driving";
        Uri gmmIntentUri = Uri.parse(url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, AD_Login_Activity.class);
                startActivity(i);
            }
        });

        nava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        });

        buttonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        buttonHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
        buttonSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataM();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataC();
            }
        });
        // 13.776170, 100.508381 ssru


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }


            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents, null);


                TextView title = ((TextView) infoWindow.findViewById(R.id.textViewName));
                title.setText(marker.getTitle());


                TextView snippet = ((TextView) infoWindow.findViewById(R.id.textViewSnippet));
                snippet.setText(marker.getSnippet());

                ImageView imageView = (ImageView) infoWindow.findViewById(R.id.imageView);

                if ("You're Here".equals(marker.getTitle())) {
                    imageView.setImageResource(R.drawable.you);
                }

                return infoWindow;
            }

        });

    }

    public boolean checkUserLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);

            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
                }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        lastlocation = location;

        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You're Here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        currentUserLocationMarker = mMap.addMarker(markerOptions);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


}