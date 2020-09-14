package com.example.bestmatching;

import android.content.Context;
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

public class FutSalHelp_MyinfoActivity extends Fragment implements View.OnClickListener{

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    String send_id=lg.Myid;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;

    private Context context;

    TextView id;
    TextView name;
    TextView team;
    EditText mail;
    EditText phone;
    EditText location;
    EditText position;

    Button change;


    public static FutSalHelp_MyinfoActivity newInstance() {
        return new FutSalHelp_MyinfoActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_help_myinfo, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        id = (TextView)view.findViewById(R.id.Myinfo_id);
        name = (TextView)view.findViewById(R.id.Myinfo_name);
        team = (TextView)view.findViewById(R.id.Myinfo_team);

        mail = (EditText)view.findViewById(R.id.Myinfo_mail);
        phone = (EditText)view.findViewById(R.id.Myinfo_phone);
        location = (EditText)view.findViewById(R.id.Myinfo_location);
        position = (EditText)view.findViewById(R.id.Myinfo_position);

        change = (Button)view.findViewById(R.id.Myinfo_change);

        change.setOnClickListener(this);
        new Get().execute(ip + "/Help/Myinfo"+send_id);

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a){
            case R.id.Myinfo_change:
                new Post().execute(ip + "/Help/Myinfo");
                break;
        }

    }
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", id.getText().toString());
                jsonObject.put("mail", mail.getText().toString());
                jsonObject.put("phone", phone.getText().toString());
                jsonObject.put("location", location.getText().toString());
                jsonObject.put("position", position.getText().toString());

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
                    //setCookieHeader();
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
                    //getCookieHeader();
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
                    Toast.makeText(context.getApplicationContext(),"정보수정 완료",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context.getApplicationContext(),"정보수정 실패",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
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
                        String userinfo = jsonObject.getString("Myinfo");
                        JSONArray jsonArray = new JSONArray(userinfo);
                        id, name, team, email, phone, location, position
                                jsonArray.getString("id");
                        for (int i = 0; i < teamSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            team_search_name.add(js.getString("team_name"));
                            team_search_phone.add(js.getString("phonenumber"));
                            team_search_loaction.add(js.getString("location"));
                            team_search_week.add(js.getString("week"));
                        }
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
}
