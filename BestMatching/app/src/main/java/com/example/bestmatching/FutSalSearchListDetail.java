package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class FutSalSearchListDetail extends Fragment implements View.OnClickListener {

    private FutsalSearchListAdapter futsalSearchListAdapter;
    private Context context;


    TextView test1;
    TextView test2;

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;

    FutSalSearchListActivity fsla = new FutSalSearchListActivity();


    //구장 개수
    private int stadiumSize;

    private ArrayList<String> stadium_name = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();

    public static FutSalSearchListDetail newInstance() {
        return new FutSalSearchListDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_search_list_item_detail, null);

        context = container.getContext();
        futsalSearchListAdapter = new FutsalSearchListAdapter();

        test1 = (TextView)view.findViewById(R.id.test1);
        test2 = (TextView)view.findViewById(R.id.test2);

        new Get().execute(ip + "/ground/search");
        Toast.makeText(getActivity(),Integer.toString(fsla.pos),Toast.LENGTH_SHORT).show();

        return view;
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
           /* test1.setText(stadium_name.get(fsla.pos));
            test2.setText(price.get(fsla.pos));*/
        }

    }

    @Override
    public void onClick(View v) {

    }
}
