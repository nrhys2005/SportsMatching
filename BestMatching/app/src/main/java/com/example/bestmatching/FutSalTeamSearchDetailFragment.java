package com.example.bestmatching;

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

public class FutSalTeamSearchDetailFragment extends Fragment implements View.OnClickListener {



    private Context context;

    FutSalTeamActivity fa = new FutSalTeamActivity();
    public int wait_state=fa.wait_team;
    LoginActivity lg = new LoginActivity();

    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;
    String ip = lg.ip;

    public int team_member = fa.in_team;
    TextView detail_team_name;
    TextView detail_team_matser;
    TextView detail_team_number;
    TextView detail_team_location;
    TextView detail_team_week;
    TextView detail_team_age;
    TextView detail_team_comment;

    Button back_btn;
    Button team_join;
    public static FutSalTeamSearchDetailFragment newInstance() {
        return new FutSalTeamSearchDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_search_item_detail, null);

        context = container.getContext();

        detail_team_name = (TextView)view.findViewById(R.id.detail_team_name);
        detail_team_matser = (TextView)view.findViewById(R.id.detail_master_id);
        detail_team_number = (TextView)view.findViewById(R.id.detail_team_number);
        detail_team_location = (TextView)view.findViewById(R.id.detail_team_locaion);
        detail_team_week = (TextView)view.findViewById(R.id.detail_team_week);
        detail_team_age = (TextView)view.findViewById(R.id.detail_team_age);
        detail_team_comment = (TextView)view.findViewById(R.id.detail_team_comment);

        String team_name = getArguments().getString("team_name");
        String team_master = getArguments().getString("team_master");
        String team_number = getArguments().getString("phonenumber");
        String team_location = getArguments().getString("location");
        String team_week = getArguments().getString("week");
        String team_age= getArguments().getString("avg_age");
        String team_comment = getArguments().getString("comment");
        //Toast.makeText(getActivity(),Integer.toString(a),Toast.LENGTH_SHORT).show();

        detail_team_name.setText(team_name);
        detail_team_matser.setText(team_master);
        detail_team_number.setText(team_number);
        detail_team_location.setText(team_location);
        detail_team_week.setText(team_week);
        detail_team_age.setText(team_age);
        detail_team_comment.setText(team_comment);

        /*int id = getArguments().getInt("id");
        switch (id) {
            case 1:
                Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.sangju);
                detail_ground.setImageBitmap(bm1);
                break;
            case 2:
                Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.daegu);
                detail_ground.setImageBitmap(bm2);
                break;
        }*/

        back_btn = (Button)view.findViewById(R.id.back_btn);
        team_join = (Button)view.findViewById(R.id.team_join);
        back_btn.setOnClickListener(this);
        team_join.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a) {
            case R.id.back_btn:
                ((MainActivity) getActivity()).backFragment(FutSalTeamActivity.newInstance(), FutSalTeamSearchFragment.newInstance());
                break;
            case R.id.team_join:
                switch (team_member){
                    case 0:
                        switch (wait_state) {
                            case 0://팀 가입신청 수락 대기중 상태가 아닐 때
                                new Post().execute(ip + "/team/join");
                                ((MainActivity) getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamSearchFragment.newInstance());
                                break;
                            case 1://팀 가입신청 수락 대기중 상태일 때
                                Toast.makeText(context.getApplicationContext(),"가입신청 수락 대기중에 있어 신청할 수 없습니다.",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case 1:
                        Toast.makeText(context.getApplicationContext(),"소속된 팀이 있어 가입 신청을 할 수 없습니다.",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                //jsonObject.put("user_id", "androidTest");
                //jsonObject.put("name", "yun");
                //jsonObject.put("waiting", detail_team_name.getText().toString()+"_waiting");
                jsonObject.put("team_name", detail_team_name.getText().toString());
                jsonObject.put("user_id", lg.Myid);

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
                    Toast.makeText(context.getApplicationContext(),"가입 신청이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
