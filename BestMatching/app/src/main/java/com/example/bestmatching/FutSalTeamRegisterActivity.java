package com.example.bestmatching;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.List;

public class FutSalTeamRegisterActivity extends Fragment implements View.OnClickListener {

    private Context context;

    EditText team_name;
    EditText phonenumber;
    TextView age_avg;
    TextView level;
    TextView location;
    TextView week;
    EditText comment;
    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;

    //private Spinner spinner_location;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;
    Button team_regist;

    public static FutSalTeamRegisterActivity newInstance() {
        return new FutSalTeamRegisterActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_team_register, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        team_name = (EditText) view.findViewById(R.id.team_name);
        phonenumber = (EditText) view.findViewById(R.id.phonenumber);
        age_avg = (TextView) view.findViewById(R.id.age_avg);
        level = (TextView) view.findViewById(R.id.level);
        location = (TextView) view.findViewById(R.id.location);
        week = (TextView) view.findViewById(R.id.week);
        comment = (EditText) view.findViewById(R.id.comment);

        team_regist = (Button)view.findViewById(R.id.team_regist);

        /*final String[] location = {"10대", "20대", "30대", "40대", "50대"};
        spinner_location = (Spinner)view.findViewById(R.id.location);
        ArrayAdapter spinnerAdaptor;
        spinnerAdaptor = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item,location);
        spinner_location.setAdapter(spinnerAdaptor);*/

        age_avg.setOnClickListener(this);
        level.setOnClickListener(this);
        location.setOnClickListener(this);
        week.setOnClickListener(this);
        team_regist.setOnClickListener(this);
        return view;
    }

    public void reset()
    {
        team_name.setText("");
        phonenumber.setText("");
        age_avg.setText("");
        level.setText("");
        location.setText("");
        week.setText("");
        comment.setText("");
    }

    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                //jsonObject.put("user_id", "androidTest");
                //jsonObject.put("name", "yun");

                jsonObject.put("team_name", team_name.getText().toString());
                jsonObject.put("phonenumber", phonenumber.getText().toString());
                jsonObject.put("age_avg", age_avg.getText().toString());
                jsonObject.put("level", level.getText().toString());
                jsonObject.put("location", location.getText().toString());
                jsonObject.put("week", week.getText().toString());
                jsonObject.put("comment", comment.getText().toString());




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
                    setCookieHeader();
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
                    getCookieHeader();
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
                    Toast.makeText(context.getApplicationContext(),"팀등록 성공",Toast.LENGTH_SHORT).show();
                    reset();
                }
                else {
                    Toast.makeText(context.getApplicationContext(),"팀등록 실패",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private void getCookieHeader(){//Set-Cookie에 배열로 돼있는 쿠키들을 스트링 한줄로 변환
        List<String> cookies = con.getHeaderFields().get("Set-Cookie");
        //cookies -> [JSESSIONID=D3F829CE262BC65853F851F6549C7F3E; Path=/smartudy; HttpOnly] -> []가 쿠키1개임.
        //Path -> 쿠키가 유효한 경로 ,/smartudy의 하위 경로에 위의 쿠키를 사용 가능.
        if (cookies != null) {
            for (String cookie : cookies) {
                String sessionid = cookie.split(";\\s*")[0];
                //JSESSIONID=FB42C80FC3428ABBEF185C24DBBF6C40를 얻음.
                //세션아이디가 포함된 쿠키를 얻었음.
                setSessionIdInSharedPref(sessionid);

            }
        }

    }


    private void setSessionIdInSharedPref(String sessionid){
        SharedPreferences pref = context.getSharedPreferences("sessionCookie",context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if(pref.getString("sessionid",null) == null){ //처음 로그인하여 세션아이디를 받은 경우
            Log.d("LOG","처음 로그인하여 세션 아이디를 pref에 넣었습니다."+sessionid);
        }else if(!pref.getString("sessionid",null).equals(sessionid)){ //서버의 세션 아이디 만료 후 갱신된 아이디가 수신된경우
            Log.d("LOG","기존의 세션 아이디"+pref.getString("sessionid",null)+"가 만료 되어서 "
                    +"서버의 세션 아이디 "+sessionid+" 로 교체 되었습니다.");
        }
        edit.putString("sessionid",sessionid);
        edit.apply(); //비동기 처리
    }


    private void setCookieHeader(){
        SharedPreferences pref = context.getSharedPreferences("sessionCookie",context.MODE_PRIVATE);
        String sessionid = pref.getString("sessionid",null);
        if(sessionid!=null) {
            Log.d("LOG","세션 아이디"+sessionid+"가 요청 헤더에 포함 되었습니다.");
            con.setRequestProperty("Cookie", sessionid);
        }
    }


    @Override
    public void onClick(View v) {
        int a = v.getId();

        switch (a) {
            //평균연령 버튼
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

            case R.id.team_regist:
                new Post().execute(ip + "/team/create");
                break;
        }
    }
}