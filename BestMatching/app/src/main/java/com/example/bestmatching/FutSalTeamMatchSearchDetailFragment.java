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

public class FutSalTeamMatchSearchDetailFragment extends Fragment implements View.OnClickListener {

    private Context context;

    LoginActivity lg = new LoginActivity();
    LoginResultActivity lr = new LoginResultActivity();
    String team_name = lr.team_name;
    String ip = lg.ip;
    String now_id = lg.Myid;

    public String team_match_id;
    private int member_size;
    TextView detail_team_match_title;
    TextView detail_team_match_ground_name;
    TextView detail_team_match_start_time;
    TextView detail_team_match_end_time;
    TextView detail_team_match_cost;
    TextView detail_team_match_participants;
    TextView detail_team_match_max_user;

    Button back_btn;
    Button team_match_participants;
    Button team_match_join;

    //ArrayList<String> member_id = new ArrayList<>();
    ArrayList<String> send_member = new ArrayList<>();
    String[] memberItems;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    ArrayList<String> member_id = new ArrayList<>();
    public static FutSalTeamMatchSearchDetailFragment newInstance() {
        return new FutSalTeamMatchSearchDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_match_search_item_detail, null);

        context = container.getContext();

        detail_team_match_title = (TextView)view.findViewById(R.id.detail_team_match_title);
        detail_team_match_ground_name = (TextView)view.findViewById(R.id.detail_team_match_ground_name);
        detail_team_match_start_time = (TextView)view.findViewById(R.id.detail_team_match_start_time);
        detail_team_match_end_time = (TextView)view.findViewById(R.id.detail_team_match_end_time);
        detail_team_match_cost = (TextView)view.findViewById(R.id.detail_team_match_cost);
        detail_team_match_participants = (TextView)view.findViewById(R.id.detail_team_match_participants);
        detail_team_match_max_user = (TextView)view.findViewById(R.id.detail_team_match_max_user);

        String title = getArguments().getString("title");
        String ground_name = getArguments().getString("ground_name");
        String start_time = getArguments().getString("start_time");
        String end_time = getArguments().getString("end_time");
        String cost = getArguments().getString("cost");
        String participants = getArguments().getString("participants");
        String max_user = getArguments().getString("max_user");
        team_match_id = getArguments().getString("id");

        String start1 = start_time.substring(0,10);
        String start2 = start_time.substring(11,16);
        String end1 = end_time.substring(0,10);
        String end2 = end_time.substring(11,16);

        detail_team_match_title.setText(title);
        detail_team_match_ground_name.setText(ground_name);
        detail_team_match_start_time.setText(start1 + "   " + start2);
        detail_team_match_end_time.setText(end1 + "   " + end2);
        detail_team_match_cost.setText(cost + "원");
        detail_team_match_participants.setText(participants + "명");
        detail_team_match_max_user.setText(max_user + "명");

        back_btn = (Button)view.findViewById(R.id.back_btn);
        team_match_participants = (Button)view.findViewById(R.id.team_match_participants);
        team_match_join = (Button)view.findViewById(R.id.team_match_join);

        back_btn.setOnClickListener(this);
        team_match_participants.setOnClickListener(this);
        team_match_join.setOnClickListener(this);
        new Get().execute(ip+"/match/create_team_match/member_list?team_name=" + team_name);

        return view;
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
    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                JSONArray ja = new JSONArray(send_member);
                jsonObject.put("user_id", now_id);
                jsonObject.put("team_match_id", team_match_id);
                jsonObject.put("member_info", ja);
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
                    Toast.makeText(context.getApplicationContext(), "팀 매치참여 성공", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMyMatchFragment.newInstance());
                } else {
                    Toast.makeText(context.getApplicationContext(), "팀 매치참여 실패", Toast.LENGTH_SHORT).show();
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
                ((MainActivity)getActivity()).backFragment(FutSalMatchActivity.newInstance(), FutSalTeamMatchSearchFragment.newInstance());
                break;
            case R.id.team_match_participants:
                Bundle bundle = new Bundle();
                bundle.putString("id", team_match_id);
                FutSalTeamMatchSearchPartFragment f = new FutSalTeamMatchSearchPartFragment();
                f.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), f);
                break;
            case R.id.team_match_join:
                builder = new AlertDialog.Builder(context);
                final ArrayList<String> selectedItems = new ArrayList<String>();
                selectedItems.clear();
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
                                send_member.add(now_id);
                                for(int i =0; i<selectedItems.size();i++)
                                {
                                    send_member.add(selectedItems.get(i));
                                }

                                new Post().execute(ip + "/match/join/team");
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

        }

    }
}
