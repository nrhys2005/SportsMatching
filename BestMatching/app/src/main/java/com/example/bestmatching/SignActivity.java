package com.example.bestmatching;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.ArrayList;

public class SignActivity extends AppCompatActivity implements View.OnClickListener {

    Button finshsign_btn;
    Button back_btn;

    TextView testget;
    TextView testpost;

    EditText id;
    EditText pw;
    EditText pw_check;
    EditText name;
    EditText email;

    ArrayList<String> idList = new ArrayList<>();
    ArrayList<String> pwList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> emailList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        finshsign_btn = findViewById(R.id.finshsign_btn);
        back_btn = findViewById(R.id.back_btn);

        testget = findViewById(R.id.testget);
        testpost = findViewById(R.id.testpost);

        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);
        pw_check = findViewById(R.id.pw_check);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        finshsign_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
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
                    //JSONObject jsonObject = new JSONObject("http://localhost:3000/users");
                    JSONArray jsonArray = new JSONArray(receiveMsg);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idList.add(jsonObject.getString("id"));
                        pwList.add(jsonObject.getString("pwd"));
                        nameList.add(jsonObject.getString("name"));
                        emailList.add(jsonObject.getString("email"));
                    }

                    //testpost.setText(idList+" "+nameList);


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

            testget.setText("성공");
            testpost.setText(idList.get(0)+"\n"+nameList.get(0));

            /*String url = "http://localhost:3000/users";
            InputStream is = null;
            try {
                is = new URL(url).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String str;
                StringBuffer buffer = new StringBuffer();
                while ((str = rd.readLine()) != null) {
                    buffer.append(str);
                }
                String receiveMsg = buffer.toString();

                try {
                    //JSONObject jsonObject = new JSONObject("http://localhost:3000/users");
                    JSONArray jsonArray = new JSONArray(receiveMsg);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idList.add(jsonObject.getString("id"));
                        nameList.add(jsonObject.getString("name"));
                    }
                    testpost.setText(idList+" "+nameList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }*/


            /*try {
                //JSONObject jsonObject = new JSONObject("http://localhost:3000/users");
                JSONArray jsonArray = new JSONArray(testget.getText().toString());
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    idList.add(jsonObject.getString("id"));
                    nameList.add(jsonObject.getString("name"));
                }
                testpost.setText(idList+" "+nameList);


            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }

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

                jsonObject.put("id", id.getText().toString());
                jsonObject.put("pw", pw.getText().toString());
                jsonObject.put("name", name.getText().toString());
                jsonObject.put("email", email.getText().toString());

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
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
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
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
            testpost.setText(result);//서버로 부터 받은 값을 출력해주는 부분

            try {
                JSONObject jsonObject = new JSONObject(result);
                String msg = jsonObject.getString("result");

                if ( msg.equals("Success")){
                    Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
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
            case R.id.finshsign_btn:

                if (pw.getText().toString().equals(pw_check.getText().toString())) {
                    new Post().execute("http://59.151.245.176:3000/signup/signup");
                    //new JSONTask().execute("http://192.168.0.7:3000/users");
                    //new Get().execute("http://59.151.245.176:3000/users");
                }
                else {
                    Toast.makeText(getApplicationContext(),"비밀번호를 확인하세요",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
