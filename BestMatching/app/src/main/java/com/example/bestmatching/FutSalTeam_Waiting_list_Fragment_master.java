package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FutSalTeam_Waiting_list_Fragment_master extends Fragment implements View.OnClickListener{

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    FutSalTeamActivity ta = new FutSalTeamActivity();
    String send_teamname=ta.team_name;
    private ListView member;
    private FutsalTeam_Waiting_list_Adapter_master waiting_list_adapter_master;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;

    private Context context;
    private int waiting_list_Size;
    ArrayList<String> member_id = new ArrayList<>();
    ArrayList<String> member_age = new ArrayList<>();
    ArrayList<String> member_name = new ArrayList<>();
    ArrayList<String> member_location = new ArrayList<>();
    ArrayList<String> member_phonenumber = new ArrayList<>();
    ArrayList<String> member_position = new ArrayList<>();


    public static FutSalTeam_Waiting_list_Fragment_master newInstance() {
        return new FutSalTeam_Waiting_list_Fragment_master();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_team_waiting_list_master, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();
        waiting_list_adapter_master = new FutsalTeam_Waiting_list_Adapter_master();
        member = (ListView) view.findViewById(R.id.futsal_team_waiting_list_master);
        member.setAdapter(waiting_list_adapter_master);
        new Get().execute(ip + "/team/team_waiting_list?team_name="+send_teamname);

        return view;
    }


    @Override
    public void onClick(View v) {
        int a = v.getId();


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

                    if (msg.equals("200")) {
                        String notice_info = jsonObject.getString("agree_info");
                        JSONArray jsonArray = new JSONArray(notice_info);

                        waiting_list_Size = jsonArray.length();

                        for (int i = 0; i < waiting_list_Size; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            member_id.add(js.getString("user_id"));
                            member_name.add(js.getString("name"));
                            member_age.add(js.getString("age"));
                            member_location.add(js.getString("location"));
                            member_phonenumber.add(js.getString("phone"));
                            member_position.add(js.getString("position"));


                        }
                    } else if (msg.equals("no find")) {
                        waiting_list_Size = 0;//들어올릴 없음 왜냐하면 팀이 존재하면 팀원은 무조건 1명이상 존재
                    } else {
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
            Toast.makeText(getActivity(), "승인 대기자들을 성공적으로 불러왔습니다.", Toast.LENGTH_SHORT).show();
            if (waiting_list_Size != 0) {
                for (int i = 0; i < waiting_list_Size; i++) {
                    waiting_list_adapter_master.addItem(member_id.get(i), member_name.get(i),member_age.get(i),member_location.get(i),member_phonenumber.get(i),member_position.get(i));
                }

                waiting_list_adapter_master.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "에러", Toast.LENGTH_SHORT).show();
            }




        }

    }


}
