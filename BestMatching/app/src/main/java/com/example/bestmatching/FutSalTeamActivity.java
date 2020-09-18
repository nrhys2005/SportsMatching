package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class FutSalTeamActivity extends Fragment implements View.OnClickListener {

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    String send_id=lg.Myid;

    private Context context;

    Button team_info;
    Button team_create;
    Button team_search;

    public static String team_name="";
    public static String get_master_id="";
    public static String get_id="";

    //팀에 소속되었는지 판단
    private static int in_team=0;
    //팀장인지 판단
    public static int Team_Master=0;

    public static FutSalTeamActivity newInstance() {
        return new FutSalTeamActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        View view = inflater.inflate(R.layout.activity_futsal_team_main, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.
        context = container.getContext();
        team_info = (Button)view.findViewById(R.id.team_info);
        team_create = (Button)view.findViewById(R.id.team_create);
        team_search = (Button)view.findViewById(R.id.team_search);

        team_info.setOnClickListener(this);
        team_create.setOnClickListener(this);
        team_search.setOnClickListener(this);

        new Get().execute(ip + "/team/team?id="+send_id);

        return view;
    }

    @Override
    public void onClick(View v) {

        int a = v.getId();
        switch (a){
            case R.id.team_info:
                switch (in_team){
                    case 0:
                        Toast.makeText(context.getApplicationContext(), "소속된 팀이 없습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context.getApplicationContext(), "소속된 팀이 있습니다.", Toast.LENGTH_SHORT).show();
                        switch (Team_Master){
                            case 0://팀원
                                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamInfoFragment.newInstance());
                                break;
                            case 1://팀장
                                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamInfoFragment_Master.newInstance());
                                break;
                        }

                        break;
                }
                break;
            case R.id.team_create:
                switch (in_team) {
                    case 0:
                        ((MainActivity) getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamRegisterFragment.newInstance());
                        break;
                    case 1:
                        Toast.makeText(context.getApplicationContext(), "이미 소속된 팀이 있습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case R.id.team_search:
                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamSearchFragment.newInstance());

                break;
        }

    }
    public class Get extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {

            InputStream is = null;
            try {
                is = new URL(urls[0]).openStream();
                // System.out.println(urls[0]);
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
                        String team_main = jsonObject.getString("team_main");
                        JSONArray jsarr = new JSONArray(team_main);
                        JSONObject js = jsarr.getJSONObject(0);

                        get_id=js.getString("id");
                        team_name=js.getString("team_name");
                        get_master_id=js.getString("master_id");
                    }
                     else {
                        Toast.makeText(context.getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
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
            //TODO 겟 처리 후 결과

            if(team_name.equals("")||team_name.equals("null"))
                in_team=0;
            else
                in_team=1;

            System.out.println("ttt"+team_name);

            if(get_id.equals(get_master_id)) {
                Team_Master = 1;
            }
            else
                Team_Master=0;


        }

    }
}
