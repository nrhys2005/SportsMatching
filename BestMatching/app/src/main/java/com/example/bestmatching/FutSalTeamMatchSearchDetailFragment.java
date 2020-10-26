package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FutSalTeamMatchSearchDetailFragment extends Fragment implements View.OnClickListener {

    private Context context;

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    String now_id = lg.Myid;
    public String team_match_id;

    TextView detail_team_match_title;
    TextView detail_team_match_ground_name;
    TextView detail_team_match_start_time;
    TextView detail_team_match_end_time;
    TextView detail_team_match_cost;
    TextView detail_team_match_participants;
    TextView detail_team_match_max_user;

    Button back_btn;
    Button team_match_participants;
    Button team_match_join;

    public static FutSalTeamMatchSearchDetailFragment newInstance() {
        return new FutSalTeamMatchSearchDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_match_search_item_detail, null);

        context = container.getContext();

        detail_team_match_title = (TextView)view.findViewById(R.id.detail_team_match_title);
        detail_team_match_ground_name = (TextView)view.findViewById(R.id.detail_team_match_ground_name);
        detail_team_match_start_time = (TextView)view.findViewById(R.id.detail_team_match_start_time);
        detail_team_match_end_time = (TextView)view.findViewById(R.id.detail_team_match_end_time);
        detail_team_match_cost = (TextView)view.findViewById(R.id.detail_team_match_cost);
        detail_team_match_participants = (TextView)view.findViewById(R.id.detail_team_match_participants);
        detail_team_match_max_user = (TextView)view.findViewById(R.id.detail_team_match_max_user);

        String title = getArguments().getString("title");
        String ground_name = getArguments().getString("ground_name");
        String start_time = getArguments().getString("start_time");
        String end_time = getArguments().getString("end_time");
        String cost = getArguments().getString("cost");
        String participants = getArguments().getString("participants");
        String max_user = getArguments().getString("max_user");
        team_match_id = getArguments().getString("id");

        String start1 = start_time.substring(0,10);
        String start2 = start_time.substring(11,16);
        String end1 = end_time.substring(0,10);
        String end2 = end_time.substring(11,16);

        detail_team_match_title.setText(title);
        detail_team_match_ground_name.setText(ground_name);
        detail_team_match_start_time.setText(start1 + "   " + start2);
        detail_team_match_end_time.setText(end1 + "   " + end2);
        detail_team_match_cost.setText(cost + "원");
        detail_team_match_participants.setText(participants + "명");
        detail_team_match_max_user.setText(max_user + "명");

        back_btn = (Button)view.findViewById(R.id.back_btn);
        team_match_participants = (Button)view.findViewById(R.id.team_match_participants);
        team_match_join = (Button)view.findViewById(R.id.team_match_join);

        back_btn.setOnClickListener(this);
        team_match_participants.setOnClickListener(this);
        team_match_join.setOnClickListener(this);

        return view;
    }

    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("user_id", now_id);
                jsonObject.put("match_id", team_match_id);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String msg = jsonObject.getString("result");

                if (msg.equals("Success")) {
                    Toast.makeText(context.getApplicationContext(), "팀 매치참여 성공", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMyMatchFragment.newInstance());
                } else {
                    Toast.makeText(context.getApplicationContext(), "팀 매치참여 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a) {
            case R.id.back_btn:
                ((MainActivity)getActivity()).backFragment(FutSalMatchActivity.newInstance(), FutSalTeamMatchSearchFragment.newInstance());
                break;
            case R.id.team_match_participants:
               /* Bundle bundle = new Bundle();
                bundle.putString("id", team_match_id);
                FutSalMatchSearchPartFragment f = new FutSalMatchSearchPartFragment();
                f.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), f);*/
                break;
            case R.id.team_match_join:
               /* new Post().execute(ip + "/match/join");*/
                break;
        }

    }
}
