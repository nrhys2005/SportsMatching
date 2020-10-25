package com.example.bestmatching;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.ArrayList;

public class FutSalTeam_Match_RegisterFragment extends Fragment implements View.OnClickListener {

    private Context context;
    LoginActivity lg = new LoginActivity();
    LoginResultActivity lr = new LoginResultActivity();
    EditText match_title;
    TextView select_stadium;
    TextView match_date;
    TextView match_start_time;
    TextView match_end_time;
    EditText match_cost;
    TextView match_user;
    Button select_player;
    Button confirm_player;
    EditText min_player_number;
    EditText max_player_number;
    Button match_register;

    String ip = lg.ip;
    String now_id = lg.Myid;
    String team_name = lr.team_name;

    private int ground_size;
    private int member_size;

    ArrayList<String> my_book_groundName = new ArrayList<>();
    ArrayList<String> my_groundName = new ArrayList<>();
    ArrayList<String> my_book_price = new ArrayList<>();
    ArrayList<String> my_book_start_time = new ArrayList<>();
    ArrayList<String> my_book_end_time = new ArrayList<>();

    ArrayList<String> member_id = new ArrayList<>();
    ArrayList<String> send_member = new ArrayList<>();
    AlertDialog.Builder builder;
    AlertDialog dialog;

    String[] groundnameItems;
    String[] groundItems;
    String[] startTimeItems;
    String[] endTimeItems;

    String[] memberItems;

//    private DatePickerDialog.OnDateSetListener callbackMethod;
//    private TimePickerDialog.OnTimeSetListener start;
//    private TimePickerDialog.OnTimeSetListener end;


    public static FutSalTeam_Match_RegisterFragment newInstance() {
        return new FutSalTeam_Match_RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_match_register, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        match_title = (EditText) view.findViewById(R.id.match_title);
        select_stadium = (TextView) view.findViewById(R.id.select_stadium);
        match_date = (TextView) view.findViewById(R.id.match_date);
        match_start_time = (TextView) view.findViewById(R.id.match_start_time);
        match_end_time = (TextView) view.findViewById(R.id.match_end_time);
        match_cost = (EditText) view.findViewById(R.id.match_cost);
        match_user = (TextView) view.findViewById(R.id.match_user);
        select_player = (Button) view.findViewById(R.id.select_player);
        confirm_player = (Button) view.findViewById(R.id.confirm_player);
        min_player_number = (EditText) view.findViewById(R.id.min_player_number);
        max_player_number = (EditText) view.findViewById(R.id.max_player_number);
        match_register = (Button) view.findViewById(R.id.match_register);


//        match_start_time.setOnClickListener(this);
//        match_end_time.setOnClickListener(this);
//        match_date.setOnClickListener(this);

        new Get1().execute(ip+"/match/create_team_match/booking_list?user_id=" + now_id);

        new Get2().execute(ip+"/match/create_team_match/member_list?team_name=" + team_name);

        select_stadium.setOnClickListener(this);
        match_register.setOnClickListener(this);
        select_player.setOnClickListener(this);
        confirm_player.setOnClickListener(this);

        return view;
    }

    public void reset() {
        match_title.setText("");
        select_stadium.setText("");
        match_date.setText("");
        match_start_time.setText("");
        match_end_time.setText("");
        match_cost.setText("");
        match_user.setText("");
        min_player_number.setText("");
        max_player_number.setText("");
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

                jsonObject.put("title", match_title.getText().toString());
                jsonObject.put("ground_name", select_stadium.getText().toString());
                jsonObject.put("date", match_date.getText().toString());
                jsonObject.put("start_time", match_start_time.getText().toString());
                jsonObject.put("end_time", match_end_time.getText().toString());
                jsonObject.put("cost",  Integer.parseInt(match_cost.getText().toString()));
                jsonObject.put("user", match_user.getText().toString());
                //인원수만 보내고 인원 정보들을 team_matching_user테이블에 추가해야함.. 방법고려,,
                //->
                jsonObject.put("member_info",send_member);
                jsonObject.put("min_user", min_player_number.getText().toString());
                jsonObject.put("max_user", max_player_number.getText().toString());
                jsonObject.put("user_id", now_id);



                HttpURLConnection con = null;
                BufferedReader reader = null;

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

                if (msg.equals("Success")) {
                    Toast.makeText(context.getApplicationContext(), "매치등록 성공", Toast.LENGTH_SHORT).show();
                    reset();
                } else {
                    Toast.makeText(context.getApplicationContext(), "매치등록 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class Get1 extends AsyncTask<String, String, String> {

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

                    if (msg.equals("Success_booking")) {
                        String item = jsonObject.getString("rows");
                        JSONArray jsonArray = new JSONArray(item);

                        ground_size = jsonArray.length();
                        for (int i = 0; i < ground_size; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            my_book_groundName.add(js.getString("name"));
                            my_groundName.add(js.getString("name")
                                    +"\n"
                                    +(js.getString("start_time").substring(5, 7))
                                    +"/"
                                    +(js.getString("start_time").substring(8, 10))
                                    +"  "
                                    +(js.getString("start_time").substring(11,16))
                                    +" ~ "
                                    +(js.getString("end_time").substring(11,16)));
                            my_book_price.add(js.getString("price"));
                            my_book_start_time.add(js.getString("start_time"));
                            my_book_end_time.add(js.getString("end_time"));
                        }
                    }  else {
                        ground_size=0;
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

            if (ground_size != 0) {
                for (int i = 0; i < ground_size; i++) {
                    groundnameItems = my_book_groundName.toArray(new String[my_book_groundName.size()]);
                    groundItems = my_groundName.toArray(new String[my_groundName.size()]);
                    startTimeItems = my_book_start_time.toArray(new String[my_book_start_time.size()]);
                    endTimeItems = my_book_end_time.toArray(new String[my_book_end_time.size()]);

                }
            }
            else {
                Toast.makeText(getActivity(), "구장예약을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                //((MainActivity) getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalSearchMapFragment.newInstance());
            }

        }
    }
    public class Get2 extends AsyncTask<String, String, String> {

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

             if (msg.equals("Success_member")) {
                        String item = jsonObject.getString("member_info");
                        JSONArray jsonArray = new JSONArray(item);

                        member_size = jsonArray.length();
                        for(int i=0;i<member_size;i++)
                        {
                            JSONObject js = jsonArray.getJSONObject(i);
                            if(!now_id.equals(js.getString("id")))
                                member_id.add(js.getString("id"));
                        }

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

           if(member_size!=0)
            {
                for( int i=0;i<member_size;i++)
                {
                    memberItems = member_id.toArray(new String[member_id.size()]);
                }
            }
            else {
                Toast.makeText(getActivity(), "팀원이 없습니다..", Toast.LENGTH_SHORT).show();
                //((MainActivity) getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalSearchMapFragment.newInstance());
            }

        }
    }
    @Override
    public void onClick(View v) {
        int a = v.getId();

        switch (a) {
            case R.id.select_stadium:
                final ArrayList<String> selectedGroundNameItem = new ArrayList<String>();
                final ArrayList<String> selectedGroundItem = new ArrayList<String>();
                final ArrayList<String> selectedStart_time = new ArrayList<String>();
                final ArrayList<String> selectedEnd_time = new ArrayList<String>();

                //겟을 여기다 해야할지 creatview에다 해야할지 테스트해보기


                builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(groundItems, -1, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int index) {
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                System.out.println("##");
                                selectedGroundNameItem.clear();
                                selectedGroundItem.clear();
                                selectedStart_time.clear();
                                selectedEnd_time.clear();

                                selectedGroundNameItem.add(groundnameItems[index]);
                                selectedGroundItem.add(groundItems[index]);
                                selectedStart_time.add(startTimeItems[index]);
                                selectedEnd_time.add(endTimeItems[index]);
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();*/
                                String date = selectedStart_time.get(0).substring(0, 10);
                                String start = selectedStart_time.get(0).substring(11, 16);
                                String end = selectedEnd_time.get(0).substring(11, 16);

                                select_stadium.setText(selectedGroundNameItem.get(0));

                                match_date.setText(date);
                                match_start_time.setText(start);
                                match_end_time.setText(end);
                            }
                        })

                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show();*/
                            }
                        });
                ;

                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.select_player:
                builder = new AlertDialog.Builder(context);
                final ArrayList<String> selectedItems = new ArrayList<String>();

                builder.setTitle("함께 뛸 팀원을 선택해 주세요.")

                .setMultiChoiceItems(memberItems, null, new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos, boolean isChecked)
                    {
                        if(isChecked == true) // Checked 상태일 때 추가
                        {
                            selectedItems.add(memberItems[pos]);
                        }
                        else				  // Check 해제 되었을 때 제거
                        {
                            selectedItems.remove(pos);
                        }
                    }
                })

                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos)
                    {

                        for(int i =0; i<selectedItems.size();i++)
                        {
                            send_member.add(selectedItems.get(i));
                        }
                        System.out.println(send_member);
                        match_user.setText( Integer.toString(selectedItems.size()));
                    }
                })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show();*/
                            }
                        });
                dialog = builder.create();
                dialog.show();
                break;

            case R.id.match_register:
                new Post().execute(ip + "/match/create_team_match");
                break;
        }
    }
}

