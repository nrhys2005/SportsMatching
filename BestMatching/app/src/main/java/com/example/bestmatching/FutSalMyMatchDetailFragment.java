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

public class FutSalMyMatchDetailFragment extends Fragment implements View.OnClickListener {

    private Context context;

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    String now_id = lg.Myid;

    TextView detail_my_match_title;
    TextView detail_my_match_ground_name;
    TextView detail_my_match_date;
    TextView detail_my_match_start_time;
    TextView detail_my_match_end_time;
    TextView detail_my_match_cost;

    Button back_btn;
    Button match_cancel;

    public static FutSalMyMatchDetailFragment newInstance() {
        return new FutSalMyMatchDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_my_match_item_detail, null);

        context = container.getContext();

        detail_my_match_title = (TextView)view.findViewById(R.id.detail_my_match_title);
        detail_my_match_ground_name = (TextView)view.findViewById(R.id.detail_my_match_ground_name);
        detail_my_match_date = (TextView)view.findViewById(R.id.detail_my_match_date);
        detail_my_match_start_time = (TextView)view.findViewById(R.id.detail_my_match_start_time);
        detail_my_match_end_time = (TextView)view.findViewById(R.id.detail_my_match_end_time);
        detail_my_match_cost = (TextView)view.findViewById(R.id.detail_my_match_cost);

        String title = getArguments().getString("title");
        String ground_name = getArguments().getString("ground_name");
        String date = getArguments().getString("date");
        String start_time = getArguments().getString("start_time");
        String end_time = getArguments().getString("end_time");
        String cost = getArguments().getString("cost");

        detail_my_match_title.setText(title);
        detail_my_match_ground_name.setText(ground_name);
        detail_my_match_date.setText(date);
        detail_my_match_start_time.setText(start_time);
        detail_my_match_end_time.setText(end_time);
        detail_my_match_cost.setText(cost + "원");

        back_btn = (Button)view.findViewById(R.id.back_btn);
        match_cancel = (Button)view.findViewById(R.id.match_cancel);

        back_btn.setOnClickListener(this);
        match_cancel.setOnClickListener(this);

        return view;
    }

    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("user_id", now_id);
                jsonObject.put("match_id", getArguments().getString("id") );

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
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

                if (msg.equals("Success")) {
                    Toast.makeText(context.getApplicationContext(), "매치취소 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), "매치취소 실패", Toast.LENGTH_SHORT).show();
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
            case R.id.back_btn:
                ((MainActivity) getActivity()).backFragment(FutSalMatchActivity.newInstance(), FutSalMatchSearchFragment.newInstance());
                break;
            case R.id.match_cancel:
                //new Post().execute(ip + "/match/mymatching_cancel");
                break;
        }

    }
}
