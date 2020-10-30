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

public class FutSalTeam_BoardRegisterFragment extends Fragment implements View.OnClickListener {

    private Context context;

    LoginActivity lg = new LoginActivity();
    FutSalTeamActivity ta = new FutSalTeamActivity();
    String send_teamname=ta.team_name;
    String ip = lg.ip;
    String send_id=lg.Myid;

    TextView board_team_name;
    TextView title;

    Button board_regist;
    Button back_btn;

    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;


    public static FutSalTeam_BoardRegisterFragment newInstance() {
        return new FutSalTeam_BoardRegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_board_register, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();
        board_team_name = (TextView) view.findViewById(R.id.board_team_name);
        title = (TextView)view.findViewById(R.id.board_title);

        board_regist = (Button)view.findViewById(R.id.board_regist);
        back_btn = (Button)view.findViewById(R.id.back_btn);

        board_regist.setOnClickListener(this);
        back_btn.setOnClickListener(this);

        board_team_name.setText(send_teamname);

        return view;
    }

    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("team_name",board_team_name.getText().toString());
                jsonObject.put("title", title.getText().toString());


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
                    Toast.makeText(context.getApplicationContext(),"게시물 등록 완료",Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context.getApplicationContext(),"게시물 등록 실패",Toast.LENGTH_SHORT).show();
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
            case R.id.board_regist:
                new Post().execute(ip + "/team/team_board/create");
                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeam_BoardFragment.newInstance());
                break;
            case R.id.back_btn:
                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeam_BoardFragment.newInstance());
                break;

        }
    }
}