package com.example.bestmatching;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class FutsalTeam_MemberAdapter_master extends BaseAdapter{

    private TextView mem_id;
    private TextView mem_name;
    private TextView mem_age;
    private TextView mem_loc;
    private TextView mem_phone;
    private TextView mem_position;
    private Button mem_del;

    private String expulsion_id;
    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    FutSalTeam_MemberFragment_master fm=new FutSalTeam_MemberFragment_master();

    private Context context;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    //private TextView content;

    public LinearLayout search_list_click;

    MainActivity ma = new MainActivity();

    public ArrayList<FutsalTeam_MemberItems_master> listItems = new ArrayList<FutsalTeam_MemberItems_master>();

    public FutsalTeam_MemberAdapter_master() {

    }


    // 어댑터에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return listItems.size();
    }


    //지정한 포지션에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }


    //지정한 포지션에 있는 데이터와 관계된 아이템의 id리턴
    @Override
    public long getItemId(int position) {
        return position;
    }


    // 포지션에 위치한 데이터를 화면에 출력하는데 사용될 뷰를 리턴
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;

        context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_futsal_team_member_item_master, parent, false);
        }

        mem_id = (TextView) convertView.findViewById(R.id.m_id);
        mem_name = (TextView) convertView.findViewById(R.id.m_name);
        mem_age = (TextView) convertView.findViewById(R.id.m_age);
        mem_loc = (TextView) convertView.findViewById(R.id.m_location);
        mem_phone = (TextView) convertView.findViewById(R.id.m_phonenumber);
        mem_position = (TextView) convertView.findViewById(R.id.m_position);

        mem_del = (Button) convertView.findViewById(R.id.m_del);

        FutsalTeam_MemberItems_master futsalTeam_memberItems_master = listItems.get(position);

        mem_id.setText(futsalTeam_memberItems_master.getId());
        mem_name.setText(futsalTeam_memberItems_master.getName());
        mem_age.setText(futsalTeam_memberItems_master.getAge());
        mem_loc.setText(futsalTeam_memberItems_master.getLocation());
        mem_phone.setText(futsalTeam_memberItems_master.getPhonenumberStr());
        mem_position.setText(futsalTeam_memberItems_master.getPositionStr());

        if (mem_age.getText().toString().equals("null") || mem_age.getText().toString().equals(""))
            mem_age.setText("정보없음");
        if (mem_loc.getText().toString().equals("null") || mem_loc.getText().toString().equals(""))
            mem_loc.setText("정보없음");
        if (mem_phone.getText().toString().equals("null") || mem_phone.getText().toString().equals(""))
            mem_phone.setText("정보없음");
        if (mem_position.getText().toString().equals("null") || mem_position.getText().toString().equals(""))
            mem_position.setText("정보없음");

        mem_del.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                builder = new AlertDialog.Builder(context);
                builder.setTitle("추방하시겠습니까?")
                        .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                expulsion_id = listItems.get(pos).getId();
                                new Post().execute(ip + "/team/myteam_drop");
                                listItems.remove(listItems.get(pos));
                                notifyDataSetChanged();

                            }
                        })
                        .setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                            }
                        });;
                dialog = builder.create();
                dialog.show();

            }
        });

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String id, String name, String age, String location, String phonenumber, String position) {
        FutsalTeam_MemberItems_master memberItems_master = new FutsalTeam_MemberItems_master();

        memberItems_master.setId(id);
        memberItems_master.setName(name);
        memberItems_master.setAge(age);
        memberItems_master.setLocation(location);
        memberItems_master.setPhonenumber(phonenumber);
        memberItems_master.setPosition(position);

        listItems.add(memberItems_master);
    }

    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", expulsion_id);
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
                    Toast.makeText(context.getApplicationContext(), "회원 추방 완료", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(context.getApplicationContext(), "회원 추방 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
