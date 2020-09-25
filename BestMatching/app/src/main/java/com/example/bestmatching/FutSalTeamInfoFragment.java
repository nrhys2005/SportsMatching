package com.example.bestmatching;

import android.app.AlertDialog;
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

import org.json.JSONArray;
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

public class FutSalTeamInfoFragment extends Fragment implements View.OnClickListener {

    private Context context;

    LoginActivity lg = new LoginActivity();
    FutSalTeamActivity ta = new FutSalTeamActivity();
    String send_teamname=ta.team_name;
    String ip = lg.ip;
    String send_id=lg.Myid;

    TextView team_name;
    TextView master_id;
    TextView phonenumber;
    TextView age_avg;
    TextView level;
    TextView location;
    TextView week;
    TextView comment;

    public String team_namestr;
    public String master_idstr;
    public String phonenumberstr;
    public String age_avgstr;
    public String levelstr;
    public String locationstr;
    public String weekstr;
    public String commentstr;


    //private Spinner spinner_location;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;
    Button team_member;
    Button team_leave;
    public static FutSalTeamInfoFragment newInstance() {
        return new FutSalTeamInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_info, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();
        new Get().execute(ip + "/team/myteam?team_name="+send_teamname);
        team_name = (TextView) view.findViewById(R.id.team_name);
        master_id = (TextView)view.findViewById(R.id.master_id);
        phonenumber = (TextView) view.findViewById(R.id.phonenumber);
        age_avg = (TextView) view.findViewById(R.id.age_avg);
        level = (TextView) view.findViewById(R.id.level);
        location = (TextView) view.findViewById(R.id.location);
        week = (TextView) view.findViewById(R.id.week);
        comment = (TextView) view.findViewById(R.id.comment);
        team_member = (Button)view.findViewById(R.id.team_member);
        team_leave = (Button)view.findViewById(R.id.team_leave);

        team_member.setOnClickListener(this);
        team_leave.setOnClickListener(this);

        return view;
    }

    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", lg.Myid);
                try {
                    //URL url = new URL("http://192.168.25.16:3000/users");
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

                if ( msg.equals("200")){
                    Toast.makeText(context.getApplicationContext(),"팀 탈퇴 성공",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context.getApplicationContext(),"팀 탈퇴 실패",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                    if (msg.equals("200")) {
                        String team_info = jsonObject.getString("myteam_info");
                        JSONArray jsarr = new JSONArray(team_info);
                        JSONObject js = jsarr.getJSONObject(0);
                        team_namestr=js.getString("team_name");
                        master_idstr=js.getString("master_id");
                        phonenumberstr=js.getString("phonenumber");
                        age_avgstr=js.getString("age_avg");
                        levelstr=js.getString("level");
                        locationstr=js.getString("location");
                        weekstr=js.getString("week");
                        commentstr=js.getString("comment");


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
            team_name.setText(team_namestr);
            master_id.setText(master_idstr);
            phonenumber.setText(phonenumberstr);
            age_avg.setText(age_avgstr);
            level.setText(levelstr);
            location.setText(locationstr);
            week.setText(weekstr);
            comment.setText(commentstr);

        }

    }

    @Override
    public void onClick(View v) {
        int a = v.getId();

        switch (a) {
            case R.id.team_member://팀원일때 팀 정보 눌르기
                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeam_MemberFragment_member.newInstance());
                break;
            case R.id.team_leave:
                new Post().execute(ip + "/team/myteam_drop");
                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamSearchFragment.newInstance());
                break;
        }
    }
}