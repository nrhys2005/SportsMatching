package com.example.bestmatching;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FutSalSearchMapActivity extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private MapView mapView = null;
    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;

    private Context context;
    private GoogleMap mMap;
    private int groundSize;


    LocationManager lm;
    Location myLocation;

    ArrayList<Integer> ground_id = new ArrayList<>();
    ArrayList<String> stadium_name = new ArrayList<>();
    ArrayList<Double> lat = new ArrayList<>();
    ArrayList<Double> lon = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();

    public static FutSalSearchMapActivity newInstance() {
        return new FutSalSearchMapActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_futsal_search_map, container, false);

        context = container.getContext();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMISSION_REQUEST_CODE);

        }
        lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
/*       lat.add(36.378633);
        lat.add(35.88812);

        lon.add(128.148226);
        lon.add(128.606546);*/
        Log.i("현재 위치1", myLocation.getLongitude()+","+myLocation.getLatitude());
        //new Get().execute(ip + "/ground");

        mapView = (MapView) view.findViewById(R.id.mapsearch);
        mapView.getMapAsync(this);
        new Get().execute(ip + "/ground/search?"+"latitude="+myLocation.getLatitude()+"&"+"longtitude="+myLocation.getLongitude());
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("현재 위치23", myLocation.getLongitude()+","+myLocation.getLatitude());
        LatLng SANGJU = new LatLng(36.378399, 128.147967);
        /*MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(SANGJU)
                .title(stadium_name.get(0).toString());
        googleMap.addMarker(markerOptions);*/
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLocation.getLatitude(),myLocation.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        MarkerOptions markerOptions = new MarkerOptions();

        for (int i=0; i<groundSize; i++) {
            googleMap.addMarker(markerOptions
                    .title(stadium_name.get(i))
                    //.position(SANGJU)
                    .position(new LatLng(lat.get(i),lon.get(i)))
                    .snippet(price.get(i) + "원"));
        }


        if (groundSize>0) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat.get(0),lon.get(0))));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat.get(0),lon.get(0)), 16));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        }

        //mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    //마커 눌렀을때
    @Override
    public boolean onMarkerClick(Marker marker) {
        //Toast.makeText(context, marker.getTitle()+ "\n"+ marker.getSnippet(), Toast.LENGTH_SHORT).show();
       /* Bundle bundle = new Bundle();
        bundle.putString("name", marker.getTitle());
        bundle.putString("price", marker.getSnippet());
        FutSalSearchListDetail f = new FutSalSearchListDetail();
        f.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(), f);*/
        return true;
    }

    //마커 정보 눌렀을때
    @Override
    public void onInfoWindowClick(Marker marker) {
        Bundle bundle = new Bundle();
        //Toast.makeText(context, marker.getId().substring(1)+1,Toast.LENGTH_SHORT).show();
        bundle.putInt("id", Integer.valueOf(marker.getId().substring(1))+1);
        bundle.putString("name", marker.getTitle());

        //마지막 "원" 이라는 문자열 지우기위함//
        String p = marker.getSnippet();
        p = p.substring(0,p.length()-1);
        bundle.putString("price", p);

        FutSalSearchListDetail f = new FutSalSearchListDetail();
        f.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(), f);
    }

    // 노드js에서 안스로 데이터 받는 부분
    public class Get extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            String url = "";
            InputStream is = null;
            try {
                is = new URL(urls[0]).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String str;
                StringBuffer buffer = new StringBuffer();
                while ((str = rd.readLine()) != null) {
                    buffer.append(str);
                }
                //URL 내용들
                String receiveMsg = buffer.toString();

                try {
                    JSONObject jsonObject = new JSONObject(receiveMsg);
                    String msg = jsonObject.getString("result");

                    if (msg.equals("Success")) {
                        String item = jsonObject.getString("ground_info");
                        JSONArray jsonArray = new JSONArray(item);

                        groundSize = jsonArray.length();

                        for (int i = 0; i < groundSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            ground_id.add(js.getInt("id"));
                            stadium_name.add(js.getString("name"));
                            lat.add(js.getDouble("latitude"));
                            lon.add(js.getDouble("longtitude"));
                            price.add(js.getString("price"));
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Toast.makeText(context, lat.get(0).toString(),Toast.LENGTH_SHORT).show();

        }
    }


}