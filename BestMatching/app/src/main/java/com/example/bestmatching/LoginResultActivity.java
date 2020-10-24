package com.example.bestmatching;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class LoginResultActivity extends AppCompatActivity {
    LoginActivity lg =new LoginActivity();
    String ip=lg.ip;
    String send_id=lg.Myid;
    TextView TextView_get;
    Button futsal_btn;
    public static String team_name="";
    public static String get_master_id="";
    public static String get_id="";
    public static String get_wait="";
    //팀에 소속되었는지 판단
    public static int in_team=0;
    //팀 가입신청 수락 대기중인지 판단
    public static int wait_team=0;
    //팀장인지 판단
    public static int Team_Master=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        new Get().execute(ip + "/team/team?id="+send_id);
/*    값확인용    TextView_get = findViewById(R.id.TextView_get);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String id = bundle.getString("id");
        String password = bundle.getString("password");

        TextView_get.setText(id + " / " + password);*/

        futsal_btn = findViewById(R.id.futsal_btn);
        futsal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


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
                        String team_main = jsonObject.getString("team_main");
                        JSONArray jsarr = new JSONArray(team_main);
                        JSONObject js = jsarr.getJSONObject(0);

                        get_id=js.getString("id");
                        get_master_id=js.getString("master_id");
                        team_name=js.getString("team_name");
                        get_wait=js.getString("wait_state");

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
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


            //팀 가입신청 수락 대기중이면 1 아니면 0
            if(get_wait.equals("")||get_wait.equals("null"))
                wait_team=0;
            else
                wait_team=1;

            //소속된 팀이 있으면 1 없으면 0
            if(team_name.equals("")||team_name.equals("null"))
                in_team=0;
            else
                in_team=1;
            //소속된 팀의 팀장이면 1 팀원이면 0
            // System.out.println(get_id+","+get_master_id);
            if(get_id.equals(get_master_id)) {

                Team_Master = 1;
            }
            else
                Team_Master=0;




        }

    }
}
