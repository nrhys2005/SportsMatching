package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class FutSalTeamMatchSearchFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context context;
    private ListView futsal_team_match_search;
    private FutsalTeamMatchSearchAdapter futsalTeamMatchSearchAdapter;

    LoginActivity lg = new LoginActivity();

    EditText team_matchsearch_text;
    Button team_matchsearch_btn;

    String ip = lg.ip;

    private int pos;

    //팀 개수
    private int teammatchSize;

    ArrayList<String> team_match_search_title = new ArrayList<>();
    ArrayList<String> team_match_search_ground = new ArrayList<>();
    ArrayList<String> team_match_search_start_time = new ArrayList<>();
    ArrayList<String> team_match_search_end_time = new ArrayList<>();
    ArrayList<String> team_match_cost = new ArrayList<>();
    ArrayList<String> team_match_search_participants = new ArrayList<>();
    ArrayList<String> team_match_search_max_user = new ArrayList<>();
    ArrayList<String> match_id = new ArrayList<>();

    public static FutSalTeamMatchSearchFragment newInstance() {
        return new FutSalTeamMatchSearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_match_search, container, false); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();
        team_matchsearch_text = (EditText) view.findViewById(R.id.team_matchsearch_text);
        team_matchsearch_btn = (Button) view.findViewById(R.id.team_matchsearch_btn);

        futsalTeamMatchSearchAdapter = new FutsalTeamMatchSearchAdapter();

        futsal_team_match_search = (ListView) view.findViewById(R.id.futsal_team_match_search);
        futsal_team_match_search.setAdapter(futsalTeamMatchSearchAdapter);

        new Get().execute(ip + "/match/search/team_match?team_match_id=none&user_id="+lg.Myid);

        futsalTeamMatchSearchAdapter.notifyDataSetChanged();

        team_matchsearch_text.setOnClickListener(this);
        futsal_team_match_search.setOnItemClickListener(this);

        return view;
    }

    //아이템값 가져오기 및 화면전환
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(view.getContext(), stadium_name.get(position), Toast.LENGTH_SHORT).show();
        pos = position;
        //Toast.makeText(view.getContext(), Integer.toString(pos), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("title", team_match_search_title.get(pos));
        bundle.putString("ground_name", team_match_search_ground.get(pos));
        bundle.putString("start_time", team_match_search_start_time.get(pos));
        bundle.putString("end_time", team_match_search_end_time.get(pos));
        bundle.putString("cost", team_match_cost.get(pos));
        bundle.putString("participants", team_match_search_participants.get(pos));
        bundle.putString("max_user", team_match_search_max_user.get(pos));
        bundle.putString("id", match_id.get(pos));
        //FutSalMatchSearchDetailFragment f = new FutSalMatchSearchDetailFragment();
        //f.setArguments(bundle);
        //((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), f);
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

                        teammatchSize = jsonArray.length();

                        for (int i = 0; i < teammatchSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            team_match_search_title.add(js.getString("title"));
                            team_match_search_ground.add(js.getString("ground_name"));
                            team_match_search_start_time.add(js.getString("start_time"));
                            team_match_search_end_time.add(js.getString("end_time"));
                            team_match_cost.add(js.getString("cost"));
                            team_match_search_participants.add(js.getString("participants"));
                            team_match_search_max_user.add(js.getString("max_user"));
                            match_id.add(js.getString("id"));
                        }
                    } else if (msg.equals("no find")) {
                        teammatchSize = 0;
                    } else {
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

            if (teammatchSize != 0) {
                for (int i = 0; i < teammatchSize; i++) {
                    String start1 = team_match_search_start_time.get(i).substring(0,10);
                    String start2 = team_match_search_start_time.get(i).substring(11,16);

                    String end1 = team_match_search_end_time.get(i).substring(0,10);
                    String end2 = team_match_search_end_time.get(i).substring(11,16);

                    futsalTeamMatchSearchAdapter.addItem(team_match_search_title.get(i), team_match_search_ground.get(i),
                            start1 + "   " + start2, end1 + "   " + end2,
                            team_match_search_participants.get(i) + "명", team_match_search_max_user.get(i) + "명");
                }
                futsalTeamMatchSearchAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "검색결과 없습니다.", Toast.LENGTH_SHORT).show();
            }


            /*for (int i=0; i<matchSize; i++) {
                if (match_search_participants.get(i).equals("0")) {
                    clear(i);
                }
            }*/


        }

    }

    public void clear() {
        team_match_search_title.clear();
        team_match_search_ground.clear();
        team_match_search_start_time.clear();
        team_match_search_end_time.clear();
        team_match_cost.clear();
        team_match_search_participants.clear();
        team_match_search_max_user.clear();
        match_id.clear();
        futsalTeamMatchSearchAdapter.clearItem();
        futsalTeamMatchSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a) {
            case R.id.team_matchsearch_btn:
                String text = team_matchsearch_text.getText().toString();

                if (text.length() > 0) {
                    clear();
                    new Get().execute(ip + "/match/search/" + text);
                } else {
                    Toast.makeText(getActivity(), "검색내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
