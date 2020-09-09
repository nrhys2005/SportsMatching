package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class FutSalTeamSearchActivity extends Fragment implements View.OnClickListener {

    private Context context;
    private ListView futsal_team_search;
    private FutsalTeamSearchAdapter futsalTeamSearchAdapter;

    EditText teamsearch_text;
    Button teamsearch_btn;

    LoginActivity lg = new LoginActivity();

    String ip = lg.ip;

    //팀 개수
    private int teamSize;

    ArrayList<String> team_search_name = new ArrayList<>();
    ArrayList<String> team_search_phone = new ArrayList<>();
    ArrayList<String> team_search_loaction = new ArrayList<>();
    ArrayList<String> team_search_week = new ArrayList<>();

    public static FutSalTeamSearchActivity newInstance() {
        return new FutSalTeamSearchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_team_search, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();
        teamsearch_text = (EditText) view.findViewById(R.id.teamsearch_text);
        teamsearch_btn = (Button) view.findViewById(R.id.teamsearch_btn);

        futsalTeamSearchAdapter = new FutsalTeamSearchAdapter();

        futsal_team_search = (ListView) view.findViewById(R.id.futsal_team_search);
        futsal_team_search.setAdapter(futsalTeamSearchAdapter);


        new Get().execute(ip + "/team/search/none");

        teamsearch_btn.setOnClickListener(this);

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
                        String item = jsonObject.getString("team_info");
                        JSONArray jsonArray = new JSONArray(item);

                        teamSize = jsonArray.length();

                        for (int i = 0; i < teamSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            team_search_name.add(js.getString("team_name"));
                            team_search_phone.add(js.getString("phonenumber"));
                            team_search_loaction.add(js.getString("location"));
                            team_search_week.add(js.getString("week"));
                        }
                    }

                    else if (msg.equals("no find")){
                        teamSize = 0;
                    }

                    else {
                        //Toast.makeText(context.getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
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

            if (teamSize != 0) {
                for (int i = 0; i < teamSize; i++) {
                    futsalTeamSearchAdapter.addItem(team_search_name.get(i).toString(), team_search_phone.get(i).toString(), team_search_loaction.get(i).toString(), team_search_week.get(i).toString());
                }
                futsalTeamSearchAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(getActivity(), "검색결과 없습니다.", Toast.LENGTH_SHORT).show();
            }


        }

    }

    public void clear() {
        team_search_name.clear();
        team_search_phone.clear();
        team_search_loaction.clear();
        team_search_week.clear();
        futsalTeamSearchAdapter.clearItem();
        futsalTeamSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a) {
            case R.id.teamsearch_btn:
                String text = teamsearch_text.getText().toString();

                if (text.length()>0) {
                    clear();
                    new Get().execute(ip + "/team/search/" + text);
                    //Toast.makeText(getActivity(), teamsearch_text.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "검색내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
