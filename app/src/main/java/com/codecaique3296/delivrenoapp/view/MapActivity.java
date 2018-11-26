package com.codecaique3296.delivrenoapp.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.models.object.Resturant;
import com.codecaique3296.delivrenoapp.models.response.ResturantsJson;
import com.codecaique3296.delivrenoapp.network.ApiClient;
import com.codecaique3296.delivrenoapp.network.ApiInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Resturant> resturants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        getResturants();
    }

    public void getResturants() {
        ApiInterface apiService1 =
                ApiClient.getClient().create(ApiInterface.class);
        resturants = new ArrayList<>();
        Call<ResturantsJson> call1 = apiService1.getResturants("1");
        call1.enqueue(new Callback<ResturantsJson>() {
            @Override
            public void onResponse(Call<ResturantsJson> call, retrofit2.Response<ResturantsJson> response) {
                int statusCode = response.code();
                Toast.makeText(MapActivity.this, response.body().getAck(), Toast.LENGTH_SHORT).show();
                resturants = (ArrayList<Resturant>) response.body().getResturants();
                for (int i = 0; i < resturants.size(); i++) {
//                    createMarker(Double.valueOf(resturants.get(i).getLatitude()), Double.valueOf(resturants.get(i).getLongitude()),
//                            resturants.get(i).getName(), resturants.get(i).getAddress(), R.drawable.restmarker);
                    Log.e("Resturants" , resturants.get(i).toString());
                }
            }
            @Override
            public void onFailure(Call<ResturantsJson> call, Throwable t) {
                Toast.makeText(MapActivity.this, "failer", Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  startActivity(new Intent(this , ADashboardActivity.class));
    }
}
