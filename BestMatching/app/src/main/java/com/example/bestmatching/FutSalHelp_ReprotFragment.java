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

public class FutSalHelp_ReprotFragment extends Fragment implements View.OnClickListener {

    private Context context;

    EditText report_title;
    TextView report_id;
    TextView report_category;
    EditText report_target;
    EditText report_content;
    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    String myid = lg.Myid;

    //private Spinner spinner_location;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;
    Button report_regist;

    public static FutSalHelp_ReprotFragment newInstance() {
        return new FutSalHelp_ReprotFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_help_report_register, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.




        context = container.getContext();

        report_title = (EditText) view.findViewById(R.id.report_title);
        report_id = (TextView) view.findViewById(R.id.report_id);
        report_category = (TextView) view.findViewById(R.id.report_category);
        report_target = (EditText) view.findViewById(R.id.report_target);
        report_content = (EditText) view.findViewById(R.id.report_content);

        report_regist = (Button)view.findViewById(R.id.report_regist);

        report_id.setText(myid);

        report_category.setOnClickListener(this);
        report_regist.setOnClickListener(this);
        return view;
    }


    public void reset()
    {
        report_title.setText("");
        report_content.setText("");
        report_target.setText("");
        report_category.setText("");
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
                jsonObject.put("report_title", report_title.getText().toString());
                jsonObject.put("report_id", report_id.getText().toString());
                jsonObject.put("report_category", report_category.getText().toString());
                jsonObject.put("report_target", report_target.getText().toString());
                jsonObject.put("report_content", report_content.getText().toString());


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
                    Toast.makeText(context.getApplicationContext(),"신고 완료",Toast.LENGTH_SHORT).show();
                    reset();

                }
                else {
                    Toast.makeText(context.getApplicationContext(),"신고 실패",Toast.LENGTH_SHORT).show();
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
            //평균연령 버튼
            case R.id.report_category:
                final String[] ages = {"비매너","사칭","허위사실유포","기타"};

                builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(ages, -1, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int index){
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                report_category.setText(ages[index]);
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


            case R.id.report_regist:
                new Post().execute(ip + "/Help/Report");
                //((MainActivity)getActivity()).replaceFragment(this,FutSalTeamSearchFragment.newInstance());
                break;
        }
    }
}