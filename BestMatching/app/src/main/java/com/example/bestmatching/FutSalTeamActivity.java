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
    public String team_name="";
    private static int in_team=0;
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

        new Get().execute(ip + "/team/myteam?id="+send_id);

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
                        ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamInfoFragment.newInstance());
                        break;
                }
                break;
            case R.id.team_create:
                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamRegisterFragment.newInstance());
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
                        String team_info = jsonObject.getString("myteam_info");
                        JSONArray jsarr = new JSONArray(team_info);
                        JSONObject js = jsarr.getJSONObject(0);
                        team_name=js.getString("team_name");
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

            if(team_name.equals(""))
                in_team=0;
            else
                in_team=1;




        }

    }
}
