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

public class FutSalMatchRegisterActivity extends Fragment implements View.OnClickListener {

    private Context context;
    LoginActivity lg = new LoginActivity();

    EditText match_title;
    TextView select_stadium;
    TextView match_date;
    TextView match_start_time;
    TextView match_end_time;
    EditText match_cost;

    String ip = lg.ip;

    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TimePickerDialog.OnTimeSetListener start;
    private TimePickerDialog.OnTimeSetListener end;

    Button match_register;

    public static FutSalMatchRegisterActivity newInstance() {
        return new FutSalMatchRegisterActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_match_register, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        match_title = (EditText) view.findViewById(R.id.match_title);
        select_stadium = (TextView) view.findViewById(R.id.select_stadium);
        match_date = (TextView) view.findViewById(R.id.match_date);
        match_start_time = (TextView) view.findViewById(R.id.match_start_time);
        match_end_time = (TextView) view.findViewById(R.id.match_end_time);
        match_cost = (EditText) view.findViewById(R.id.match_cost);
        match_register = (Button) view.findViewById(R.id.match_register);


        match_start_time.setOnClickListener(this);
        match_end_time.setOnClickListener(this);
        match_date.setOnClickListener(this);
        select_stadium.setOnClickListener(this);
        match_register.setOnClickListener(this);

        return view;
    }

    public void reset() {
        match_title.setText("");
        select_stadium.setText("");
        match_date.setText("");
        match_start_time.setText("");
        match_end_time.setText("");
        match_cost.setText("");
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
                jsonObject.put("cost", match_cost.getText().toString());


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

    @Override
    public void onClick(View v) {
        int a = v.getId();

        switch (a) {
            case R.id.select_stadium:

                final String[] items = {"경북대 상주캠 풋살장", "경북대 대구캠 풋살장"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int index) {
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                select_stadium.setText(items[index]);
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();*/
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

            //날짜버튼 눌렀을때
            case R.id.match_date:
                callbackMethod = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        match_date.setText(year + "년 " + month + "월 " + dayOfMonth + "일");
                    }
                };

                DatePickerDialog d = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, callbackMethod, 2020, 06, 13);
                d.show();
                break;

            //시작시간 버튼
            case R.id.match_start_time:
                start = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        match_start_time.setText(String.format("%02d",hourOfDay) + " : " + String.format("%02d",minute));
                    }
                };

                TimePickerDialog t1 = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, start, 12,00, true);
                t1.setTitle("시작시간");
                t1.show();
                break;

            //종료시간 버튼
            case R.id.match_end_time:
                end = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        match_end_time.setText(String.format("%02d",hourOfDay) + " : " + String.format("%02d",minute));
                    }
                };

                TimePickerDialog t2 = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, end, 12,00, true);
                t2.setTitle("종료시간");
                t2.show();
                break;

            case R.id.match_register:
                new Post().execute(ip + "/match/create");
                break;
        }
    }
}
