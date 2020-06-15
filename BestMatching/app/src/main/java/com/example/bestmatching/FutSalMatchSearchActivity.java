package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class FutSalMatchSearchActivity extends Fragment implements View.OnClickListener {

    private Context context;
    private ListView futsal_match_search;
    private FutsalMatchSearchAdapter futsalMatchSearchAdapter;

    LoginActivity lg = new LoginActivity();

    String ip = lg.ip;

    //팀 개수
    private int matchSize;

    ArrayList<String> match_search_title = new ArrayList<>();
    ArrayList<String> match_search_ground = new ArrayList<>();
    ArrayList<String> match_search_date = new ArrayList<>();
    ArrayList<String> match_search_start_time = new ArrayList<>();
    ArrayList<String> match_search_end_time = new ArrayList<>();

    public static FutSalMatchSearchActivity newInstance() {
        return new FutSalMatchSearchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_match_search, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        futsalMatchSearchAdapter = new FutsalMatchSearchAdapter();

        futsal_match_search = (ListView)view.findViewById(R.id.futsal_match_search);
        futsal_match_search.setAdapter(futsalMatchSearchAdapter);

        new Get().execute(ip + "/match/search/none");
        //futsalMatchSearchAdapter.addItem("제목1", "내용1","1","1","1");

        futsalMatchSearchAdapter.notifyDataSetChanged();

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
                        String item = jsonObject.getString("match_info");
                        JSONArray jsonArray = new JSONArray(item);

                        matchSize = jsonArray.length();

                        for (int i = 0; i < matchSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            match_search_title.add(js.getString("title"));
                            match_search_ground.add(js.getString("ground_name"));
                            match_search_date.add(js.getString("date"));
                            match_search_start_time.add(js.getString("start_time"));
                            match_search_end_time.add(js.getString("end_time"));
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

            for (int i = 0; i < matchSize; i++) {
                futsalMatchSearchAdapter.addItem(match_search_title.get(i), match_search_ground.get(i), match_search_date.get(i), match_search_start_time.get(i), match_search_end_time.get(i));
            }
            futsalMatchSearchAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onClick(View v) {
        int a = v.getId();

    }
}
