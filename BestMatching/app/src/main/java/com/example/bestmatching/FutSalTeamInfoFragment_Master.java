package com.example.bestmatching;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class FutSalTeamInfoFragment_Master extends Fragment implements View.OnClickListener {

    private Context context;

    LoginActivity lg = new LoginActivity();
    FutSalTeamActivity  ta = new FutSalTeamActivity();
    String send_teamname=ta.team_name;
    int tm = ta.Team_Master;
    String ip = lg.ip;
    String send_id=lg.Myid;

    TextView team_name;
    TextView master_id;
    EditText phonenumber;
    TextView age_avg;
    TextView level;
    TextView location;
    TextView week;
    EditText comment;

    public String team_namestr;
    public String master_idstr;
    public String phonenumberstr;
    public String age_avgstr;
    public String levelstr;
    public String locationstr;
    public String weekstr;
    public String commentstr;

    public static int Team_Master=0;
    //private Spinner spinner_location;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;
    Button team_member;
    Button team_update;
    public static FutSalTeamInfoFragment_Master newInstance() {
        return new FutSalTeamInfoFragment_Master();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_team_info_master, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();
        new Get().execute(ip + "/team/myteam?team_name="+send_teamname);
        team_name = (TextView) view.findViewById(R.id.team_name);
        master_id = (TextView)view.findViewById(R.id.master_id);
        phonenumber = (EditText) view.findViewById(R.id.phonenumber);
        age_avg = (TextView) view.findViewById(R.id.age_avg);
        level = (TextView) view.findViewById(R.id.level);
        location = (TextView) view.findViewById(R.id.location);
        week = (TextView) view.findViewById(R.id.week);
        comment = (EditText) view.findViewById(R.id.comment);
        team_member = (Button)view.findViewById(R.id.team_member);
        team_update = (Button)view.findViewById(R.id.team_update);

        team_member.setOnClickListener(this);
        team_update.setOnClickListener(this);

        return view;
    }

    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("phonenumber", phonenumber.getText().toString());
                jsonObject.put("age_avg", age_avg.getText().toString());
                jsonObject.put("level", level.getText().toString());
                jsonObject.put("location", location.getText().toString());
                jsonObject.put("week", week.getText().toString());
                jsonObject.put("comment", comment.getText().toString());
                jsonObject.put("team_name", team_name.getText().toString());

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

                if ( msg.equals("Success")){
                    Toast.makeText(context.getApplicationContext(),"팀수정 성공",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context.getApplicationContext(),"팀수정 실패",Toast.LENGTH_SHORT).show();
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
                    if (msg.equals("Success")) {
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
            case R.id.age_avg:
                final String[] ages = {"10대", "20대", "30대", "40대", "50대"};

                builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(ages, -1, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int index){
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                age_avg.setText(ages[index]);
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });;
                dialog = builder.create();
                dialog.show();
                break;

            //팀수준 버튼
            case R.id.level:
                final String[] levels = {"상", "중", "하"};

                builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(levels, -1, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int index){
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                level.setText(levels[index]);
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });;
                dialog = builder.create();
                dialog.show();
                break;

            //활동지역 버튼
            case R.id.location:
                final String[] locations = {"경기도", "경상도", "강원도", "전라도"};

                builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(locations, -1, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int index){
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                location.setText(locations[index]);
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });;
                dialog = builder.create();
                dialog.show();
                break;

            //활동요일 버튼
            case R.id.week:
                final String[] weeks = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};

                builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(weeks, -1, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int index){
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                week.setText(weeks[index]);
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });;
                dialog = builder.create();
                dialog.show();
                break;

            case R.id.team_member://팀장이 인원 조회 눌렀을 때

                break;
            case R.id.team_update: {
                System.out.println("업데이트버튼누름");
                new Post().execute(ip + "/team/team_update");
            }
        }
    }
}