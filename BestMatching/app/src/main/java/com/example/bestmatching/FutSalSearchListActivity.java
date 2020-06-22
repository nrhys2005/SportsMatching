package com.example.bestmatching;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FutSalSearchListActivity extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private ListView futsal_search_list;
    private FutsalSearchListAdapter futsalSearchListAdapter;

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;

    private Context context;

    public int pos;

    //구장 개수
    private int stadiumSize;

    private ArrayList<String> stadium_name = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    LocationManager lm;
    Location myLocation;

    public static FutSalSearchListActivity newInstance() {
        return new FutSalSearchListActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_futsal_search_list, container, false);

        context = container.getContext();
        futsalSearchListAdapter = new FutsalSearchListAdapter();


        futsal_search_list = (ListView) view.findViewById(R.id.futsal_search_list);
        futsal_search_list.setAdapter(futsalSearchListAdapter);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMISSION_REQUEST_CODE);

        }

        lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        new Get().execute(ip + "/ground/search?"+"latitude="+myLocation.getLatitude()+"&"+"longtitude="+myLocation.getLongitude());
        futsalSearchListAdapter.notifyDataSetChanged();
        futsal_search_list.setOnItemClickListener(this);

        /*futsal_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), stadium_name.get(position), Toast.LENGTH_SHORT).show();
                FutSalSearchListDetail f = new FutSalSearchListDetail();
                Bundle bundle = new Bundle();
                bundle.putString("text", stadium_name.get(position));
                f.setArguments(bundle);

                ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(), FutSalSearchListDetail.newInstance());
            }
        });*/

        return view;

    }

    //아이템값 가져오기 및 화면전환
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(view.getContext(), stadium_name.get(position), Toast.LENGTH_SHORT).show();
        pos = position;
        //Toast.makeText(view.getContext(), Integer.toString(pos), Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(), FutSalSearchListDetail.newInstance());
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

                        // 구장개수
                        stadiumSize = jsonArray.length();

                        for (int i = 0; i < stadiumSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            stadium_name.add(js.getString("name"));
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

            //futsalTeamSearchAdapter.addItem(team_search_name.get(0).toString(), team_search_phone.get(0).toString(), team_search_loaction.get(0).toString(), team_search_week.get(0).toString() );
            for (int i = 0; i < stadiumSize; i++) {
                futsalSearchListAdapter.addItem(stadium_name.get(i).toString(),price.get(i)+"원");
            }
            futsalSearchListAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a){
        }
    }
}